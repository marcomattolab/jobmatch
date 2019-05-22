package it.aranciaict.jobmatch.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.config.Constants;
import it.aranciaict.jobmatch.domain.Authority;
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.SponsoringInstitution;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.repository.AuthorityRepository;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.repository.CompanySectorRepository;
import it.aranciaict.jobmatch.repository.SponsoringInstitutionRepository;
import it.aranciaict.jobmatch.repository.UserRepository;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.security.SecurityUtils;
import it.aranciaict.jobmatch.service.dto.UserDTO;
import it.aranciaict.jobmatch.service.mapper.UserMapper;
import it.aranciaict.jobmatch.service.util.RandomUtil;
import it.aranciaict.jobmatch.web.rest.errors.EmailAlreadyUsedException;
import it.aranciaict.jobmatch.web.rest.errors.InternalServerErrorException;
import it.aranciaict.jobmatch.web.rest.errors.InvalidPasswordException;
import it.aranciaict.jobmatch.web.rest.errors.LoginAlreadyUsedException;
import it.aranciaict.jobmatch.web.rest.vm.CandidateManagedUserVM;
import it.aranciaict.jobmatch.web.rest.vm.CompanyManagedUserVM;
import it.aranciaict.jobmatch.web.rest.vm.SponsoringInstitutionManagedUserVM;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	/** The user repository. */
	private final UserRepository userRepository;

	/** The password encoder. */
	private final PasswordEncoder passwordEncoder;

	/** The authority repository. */
	private final AuthorityRepository authorityRepository;

	/** The cache manager. */
	private final CacheManager cacheManager;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private SponsoringInstitutionRepository sponsoringInstitutionRepository;

	@Autowired
	private CompanySectorRepository companySectorRepository;

	@Autowired
	private UserMapper userMapper;

	/**
	 * Instantiates a new user service.
	 *
	 * @param userRepository      the user repository
	 * @param passwordEncoder     the password encoder
	 * @param authorityRepository the authority repository
	 * @param cacheManager        the cache manager
	 */
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthorityRepository authorityRepository, CacheManager cacheManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorityRepository = authorityRepository;
		this.cacheManager = cacheManager;
	}

	/**
	 * Activate registration.
	 *
	 * @param key the key
	 * @return the optional
	 */
	public Optional<User> activateRegistration(String key) {
		log.debug("Activating user for activation key {}", key);
		return userRepository.findOneByActivationKey(key).map(user -> {
			// activate given user for the registration key.
			user.setActivated(true);
			user.setActivationKey(null);
			this.clearUserCaches(user);
			log.debug("Activated user: {}", user);
			return user;
		});
	}

	/**
	 * Complete password reset.
	 *
	 * @param newPassword the new password
	 * @param key         the key
	 * @return the optional
	 */
	@SuppressWarnings("checkstyle:magicNumber")
	public Optional<User> completePasswordReset(String newPassword, String key) {
		log.debug("Reset user password for reset key {}", key);
		return userRepository.findOneByResetKey(key)
				.filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400))).map(user -> {
					user.setPassword(passwordEncoder.encode(newPassword));
					user.setResetKey(null);
					user.setResetDate(null);
					this.clearUserCaches(user);
					return user;
				});
	}

	/**
	 * Request password reset.
	 *
	 * @param mail the mail
	 * @return the optional
	 */
	public Optional<User> requestPasswordReset(String mail) {
		return userRepository.findOneByEmailIgnoreCase(mail).filter(User::getActivated).map(user -> {
			user.setResetKey(RandomUtil.generateResetKey());
			user.setResetDate(Instant.now());
			this.clearUserCaches(user);
			return user;
		});
	}

	/**
	 * Register user.
	 *
	 * @param userDTO  the user DTO
	 * @param password the password
	 * @param role     the role
	 * @return the user
	 */
	public User registerUser(UserDTO userDTO, String password, String role) {
		userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new LoginAlreadyUsedException();
			}
		});
		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new EmailAlreadyUsedException();
			}
		});
		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin().toLowerCase());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setEmail(userDTO.getEmail().toLowerCase());
		newUser.setImageUrl(userDTO.getImageUrl());
		newUser.setLangKey(userDTO.getLangKey());
		// new user is not active
		newUser.setActivated(false);
		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		Set<Authority> authorities = new HashSet<>();
		if (StringUtils.isNotBlank(role)) {
			authorityRepository.findById(role).ifPresent(authorities::add);
		} else {
			authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		}
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		createSpecificUser(userDTO, newUser, role);
		this.clearUserCaches(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	/**
	 * Creates the specific user (candidate, company or sponsor).
	 *
	 * @param userDTO the user DTO
	 * @param user    the user
	 * @param role    the role
	 */
	@Transactional
	public void createSpecificUser(UserDTO userDTO, User user, String role) {
		if (AuthoritiesConstants.CANDIDATE.equals(role)) {
			Candidate candidate = composeCandidateFromUserVM((CandidateManagedUserVM) userDTO, user);
			candidateRepository.save(candidate);
		} else if (AuthoritiesConstants.COMPANY.equals(role)) {
			Company company = composeCompanyFromUserVM((CompanyManagedUserVM) userDTO, user);
			companyRepository.save(company);
		} else if (AuthoritiesConstants.SPONSORING_INSTITUTION.equals(role)) {
			SponsoringInstitution sponsoringInstitution = composeSponsoringInstitutionFromUserVM(
					(SponsoringInstitutionManagedUserVM) userDTO, user);
			sponsoringInstitutionRepository.save(sponsoringInstitution);
			Company company = composeCompanyFromUserVM((SponsoringInstitutionManagedUserVM) userDTO, user);
			company.setSponsoringInstitution(sponsoringInstitution);
			companyRepository.save(company);
		}
	}

	/**
	 * Compose candidate from user VM.
	 *
	 * @param candidateUserDTO the candidate user DTO
	 * @param user             the user
	 * @return the candidate
	 */
	private Candidate composeCandidateFromUserVM(CandidateManagedUserVM candidateUserDTO, User user) {
		Candidate candidate = new Candidate();
		candidate.associaUser(user);
		return candidate;
	}

	/**
	 * Compose company from user VM.
	 *
	 * @param companyUserDTO the company user DTO
	 * @param user           the user
	 * @return the company
	 */
	private Company composeCompanyFromUserVM(CompanyManagedUserVM companyUserDTO, User user) {
		final Company company = new Company();
		company.setUser(user);
		company.setCompanyName(companyUserDTO.getCompanyName());
		company.setFoundationDate(companyUserDTO.getFoundationDate());
		company.setCompanyDescription(companyUserDTO.getCompanyDescription());
		company.setCountry(companyUserDTO.getCountry());
		company.setVatNumber(companyUserDTO.getVatNumber());
		company.setPhone(companyUserDTO.getPhone());
		company.setEmail(user.getEmail());
		company.setCompanyType(companyUserDTO.getCompanyType());
		company.setCompanySize(companyUserDTO.getCompanySize());
		company.setNumberOfEmployee(companyUserDTO.getNumberOfEmployee());
		if (companyUserDTO.getSectorId() != null) {
			companySectorRepository.findById(companyUserDTO.getSectorId()).ifPresent(company::setSector);
		}
		return company;
	}

	/**
	 * Compose sponsoring institution from user VM.
	 *
	 * @param instituitionUserDTO the instituition user DTO
	 * @param user                the user
	 * @return the sponsoring institution
	 */
	private SponsoringInstitution composeSponsoringInstitutionFromUserVM(
			SponsoringInstitutionManagedUserVM instituitionUserDTO, User user) {
		final SponsoringInstitution sponsoringInstitution = new SponsoringInstitution();
		sponsoringInstitution.setUser(user);
		sponsoringInstitution.setIstituitionName(instituitionUserDTO.getIstituitionName());
		sponsoringInstitution.setIstituitionDescription(instituitionUserDTO.getIstituitionDescription());
		sponsoringInstitution.setFoundationDate(instituitionUserDTO.getFoundationDate());
		sponsoringInstitution.setCountry(instituitionUserDTO.getCountry());
		sponsoringInstitution.setVatNumber(instituitionUserDTO.getVatNumber());
		sponsoringInstitution.setPhone(instituitionUserDTO.getPhone());
		sponsoringInstitution.setEmail(user.getEmail());
		return sponsoringInstitution;
	}

	/**
	 * Compose company from user VM.
	 *
	 * @param instituitionUserDTO the instituition user DTO
	 * @param user                the user
	 * @return the company
	 */
	private Company composeCompanyFromUserVM(SponsoringInstitutionManagedUserVM instituitionUserDTO, User user) {
		final Company company = new Company();
		company.setUser(user);
		company.setCompanyName(instituitionUserDTO.getIstituitionName());
		company.setCompanyDescription(instituitionUserDTO.getIstituitionDescription());
		company.setFoundationDate(instituitionUserDTO.getFoundationDate());
		company.setCountry(instituitionUserDTO.getCountry());
		company.setVatNumber(instituitionUserDTO.getVatNumber());
		company.setPhone(instituitionUserDTO.getPhone());
		company.setEmail(user.getEmail());
		company.setCompanyType(CompanyType.INSTITUTION);
		company.setCompanySize(CompanySizeType.INSTITUTION);
		return company;
	}

	/**
	 * Removes the non activated user.
	 *
	 * @param existingUser the existing user
	 * @return true, if successful
	 */
	private boolean removeNonActivatedUser(User existingUser) {
		if (existingUser.getActivated()) {
			return false;
		}
		userRepository.delete(existingUser);
		userRepository.flush();
		this.clearUserCaches(existingUser);
		return true;
	}

	/**
	 * Creates the user.
	 *
	 * @param userDTO the user DTO
	 * @return the user
	 */
	public User createUser(UserDTO userDTO) {
		User user = new User();
		user.setLogin(userDTO.getLogin().toLowerCase());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail().toLowerCase());
		user.setImageUrl(userDTO.getImageUrl());
		if (userDTO.getLangKey() == null) {
			user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
		} else {
			user.setLangKey(userDTO.getLangKey());
		}
		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);
		user.setResetKey(RandomUtil.generateResetKey());
		user.setResetDate(Instant.now());
		user.setActivated(true);
		if (userDTO.getAuthorities() != null) {
			Set<Authority> authorities = userDTO.getAuthorities().stream().map(authorityRepository::findById)
					.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
			user.setAuthorities(authorities);
		}
		userRepository.save(user);
		this.clearUserCaches(user);
		log.debug("Created Information for User: {}", user);
		return user;
	}

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName first name of user
	 * @param lastName  last name of user
	 * @param email     email id of user
	 * @param langKey   language key
	 * @param imageUrl  image URL of user
	 */
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email.toLowerCase());
			user.setLangKey(langKey);
			user.setImageUrl(imageUrl);
			updateSpecificUser(user);
			this.clearUserCaches(user);
			log.debug("Changed Information for User: {}", user);
		});
	}

	/**
	 * Creates the specific user (candidate, company or sponsor).
	 *
	 * @param user    the user
	 */
	@Transactional
	public void updateSpecificUser(User user) {
		if (SecurityUtils.isCurrentUserCandidate()) {
			Candidate candidate = candidateRepository.findByUserIsCurrentUser().get();
			candidate.setFirstName(user.getFirstName());
			candidate.setLastName(user.getLastName());
			candidate.setEmail(user.getEmail());
			candidateRepository.save(candidate);
		} else if (SecurityUtils.isCurrentUserCompany() || SecurityUtils.isCurrentUserSponsoringInstitution()) {
			Company company = companyRepository.findByUserIsCurrentUser().get();
			company.setEmail(user.getEmail());
			companyRepository.save(company);
			if (SecurityUtils.isCurrentUserSponsoringInstitution()) {
				SponsoringInstitution sponsoringInstitution = sponsoringInstitutionRepository.findByUserIsCurrentUser()
						.get();
				sponsoringInstitution.setEmail(user.getEmail());
				sponsoringInstitutionRepository.save(sponsoringInstitution);
			}

		}
	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO user to update
	 * @return updated user
	 */
	public Optional<UserDTO> updateUser(UserDTO userDTO) {
		return Optional.of(userRepository.findById(userDTO.getId())).filter(Optional::isPresent).map(Optional::get)
				.map(user -> {
					this.clearUserCaches(user);
					user.setLogin(userDTO.getLogin().toLowerCase());
					user.setFirstName(userDTO.getFirstName());
					user.setLastName(userDTO.getLastName());
					user.setEmail(userDTO.getEmail().toLowerCase());
					user.setImageUrl(userDTO.getImageUrl());
					user.setActivated(userDTO.isActivated());
					user.setLangKey(userDTO.getLangKey());
					Set<Authority> managedAuthorities = user.getAuthorities();
					managedAuthorities.clear();
					userDTO.getAuthorities().stream().map(authorityRepository::findById).filter(Optional::isPresent)
							.map(Optional::get).forEach(managedAuthorities::add);
					this.clearUserCaches(user);
					log.debug("Changed Information for User: {}", user);
					return user;
				}).map(UserDTO::new);
	}

	/**
	 * Delete user.
	 *
	 * @param login the login
	 */
	public void deleteUser(String login) {
		userRepository.findOneByLogin(login).ifPresent(user -> {
			if (user.getActivated()) {
				throw new InternalServerErrorException(
						"Activated user cannot be deleted, only Unactivated users can be deleted");
			}

			Long idProfile;
			if (user.isCandidate()) {
				idProfile = candidateRepository.findIdByUser(user.getLogin()).get();
				candidateRepository.deleteById(idProfile);
			} else if (user.isCompany()) {
				idProfile = companyRepository.findIdByUser(user.getLogin()).get();
				companyRepository.deleteById(idProfile);
			} else if (user.isSponsoringInstitution()) {
				idProfile = sponsoringInstitutionRepository.findIdByUser(user.getLogin()).get();
				sponsoringInstitutionRepository.deleteById(idProfile);
			} else {
				throw new InternalServerErrorException(
						"Only candidates, companies and sponsoring inst. can be deleted");
			}

			userRepository.delete(user);
			this.clearUserCaches(user);
			log.debug("Deleted User: {}", user);
		});
	}

	/**
	 * Change password.
	 *
	 * @param currentClearTextPassword the current clear text password
	 * @param newPassword              the new password
	 */
	public void changePassword(String currentClearTextPassword, String newPassword) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			String currentEncryptedPassword = user.getPassword();
			if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
				throw new InvalidPasswordException();
			}
			String encryptedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encryptedPassword);
			this.clearUserCaches(user);
			log.debug("Changed password for User: {}", user);
		});
	}

	/**
	 * Gets the all managed users.
	 *
	 * @param pageable the pageable
	 * @return the all managed users
	 */
	@Transactional(readOnly = true)
	public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
		return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
	}

	/**
	 * Gets the user by login.
	 *
	 * @param login the login
	 * @return the user by login
	 */
	@Transactional(readOnly = true)
	public Optional<UserDTO> getUserByLogin(String login) {
		return userRepository.findOneByLogin(login).map(userMapper::userToUserDTO);
	}

	/**
	 * Gets the user by login.
	 *
	 * @return the user by login
	 */
	@Transactional(readOnly = true)
	public Optional<UserDTO> getCurrentUser() {
		return getUserByLogin(SecurityUtils.getCurrentUserLogin().get());
	}

	/**
	 * Gets the current user locale.
	 *
	 * @return the current user locale
	 */
	@Transactional(readOnly = true)
	public Locale getCurrentUserLocale() {
		String keyLang = "EN";
		if (getCurrentUser().isPresent()) {
			keyLang = getCurrentUser().get().getLangKey();
		}
		return new Locale(keyLang);
	}

	/**
	 * Gets the user with authorities by login.
	 *
	 * @param login the login
	 * @return the user with authorities by login
	 */
	@Transactional(readOnly = true)
	public UserDTO getUserWithAuthoritiesByLoginAsDTO(String login) {
		User user = null;
		UserDTO userDTO = null;
		Optional<User> queryResult = userRepository.findOneWithAuthoritiesByLogin(login);

		if (!queryResult.isPresent()) {
			return null;
		}

		user = queryResult.get();
		userDTO = userMapper.userToUserDTO(user);
		if (user.isCandidate()) {
			userDTO.setCurrentRoleId(candidateRepository.findIdByUser(user.getLogin()).get());
		} else if (user.isCompany()) {
			userDTO.setCurrentRoleId(companyRepository.findIdByUser(user.getLogin()).get());
		} else if (user.isSponsoringInstitution()) {
			userDTO.setCurrentRoleId(sponsoringInstitutionRepository.findIdByUser(user.getLogin()).get());
		}

		return userDTO;
	}

	/**
	 * Gets the user with authorities by login.
	 *
	 * @param login the login
	 * @return the user with authorities by login
	 */
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneWithAuthoritiesByLogin(login);
	}

	/**
	 * Gets the user with authorities.
	 *
	 * @param id the id
	 * @return the user with authorities
	 */
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(Long id) {
		return userRepository.findOneWithAuthoritiesById(id);
	}

	/**
	 * Gets the user with authorities.
	 *
	 * @return the user with authorities
	 */
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities() {
		return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
	}

	/**
	 * Gets the user with authorities.
	 *
	 * @return the user with authorities
	 */
	@Transactional(readOnly = true)
	public UserDTO getUserWithAuthoritiesAsDTO() {
		User user = null;
		UserDTO userDTO = null;
		Optional<User> queryResult = SecurityUtils.getCurrentUserLogin()
				.flatMap(userRepository::findOneWithAuthoritiesByLogin);

		if (!queryResult.isPresent()) {
			return null;
		}

		user = queryResult.get();
		userDTO = userMapper.userToUserDTO(user);
		if (user.isCandidate()) {
			userDTO.setCurrentRoleId(candidateRepository.findIdByUser(user.getLogin()).get());
		} else if (user.isCompany()) {
			userDTO.setCurrentRoleId(companyRepository.findIdByUser(user.getLogin()).get());
		} else if (user.isSponsoringInstitution()) {
			userDTO.setCurrentRoleId(sponsoringInstitutionRepository.findIdByUser(user.getLogin()).get());
		}

		return userDTO;
	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 */
	@SuppressWarnings("checkstyle:magicNumber")
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
				.forEach(user -> {
					log.debug("Deleting not activated user {}", user.getLogin());
					deleteSpecificUser(user);
					userRepository.delete(user);
					this.clearUserCaches(user);
				});
	}

	/**
	 * Delete specific user.
	 *
	 * @param user the user
	 */
	private void deleteSpecificUser(User user) {
		if (user.isCandidate()) {
			log.debug("Deleting not activated candiadte for user {}", user.getLogin());
			Candidate candidate = candidateRepository.findByUser(user.getLogin()).orElse(null);
			if (candidate != null) {
				candidateRepository.delete(candidate);
			}
		} else if (user.isCompany()) {
			log.debug("Deleting not activated company for user {}", user.getLogin());
			Company company = companyRepository.findByUser(user.getLogin()).orElse(null);
			if (company != null) {
				companyRepository.delete(company);
			}
		} else if (user.isSponsoringInstitution()) {
			log.debug("Deleting not activated sponsoring institute for user {}", user.getLogin());
			SponsoringInstitution institution = sponsoringInstitutionRepository.findByUser(user.getLogin())
					.orElse(null);
			if (institution != null) {
				sponsoringInstitutionRepository.delete(institution);
			}
		}
	}

	/**
	 * Gets the authorities.
	 *
	 * @return a list of all the authorities
	 */
	public List<String> getAuthorities() {
		return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
	}

	/**
	 * Clear user caches.
	 *
	 * @param user the user
	 */
	private void clearUserCaches(User user) {
		Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
		Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
	}
}
