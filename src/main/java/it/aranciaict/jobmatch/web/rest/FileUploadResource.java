package it.aranciaict.jobmatch.web.rest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.aranciaict.jobmatch.security.FileUploadType;
import it.aranciaict.jobmatch.service.FileStorageService;
import it.aranciaict.jobmatch.service.UserService;
import it.aranciaict.jobmatch.service.dto.UploadFileResponse;
import it.aranciaict.jobmatch.service.dto.UserDTO;

/**
 * The Class FileResource.
 */
@RestController
@RequestMapping("/api")
public class FileUploadResource {

	/** The Constant logger. */
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadResource.class);

	/** The file storage service. */
	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private UserService userService;

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @return the upload file response
	 */
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file, file.getOriginalFilename());
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();
		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @return the upload file response
	 */
	@PostMapping("/uploadUserProfileImg")
	public UploadFileResponse uploadUserProfileImg(@RequestParam("file") MultipartFile file) {
		String fileName = composeFileName(FileUploadType.PROFILE_PICTURE, file.getOriginalFilename());
		String filePathName = fileName;
		UserDTO user = userService.getCurrentUser().orElse(null);
		if (user != null) {
			LOG.debug("Upload User Profile picture for User " + user.getLogin());
			filePathName = composeFilePathNameForUser(user, fileName);
		}
		fileStorageService.storeFile(file, filePathName);
		LOG.debug("File Stored in " + filePathName);
		if (user != null) {
			user.setImageUrl(filePathName);
			userService.updateUser(user);
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/downloadFile/")
				.path(filePathName).toUriString();
		return new UploadFileResponse(filePathName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	/**
	 * Compose file name.
	 *
	 * @param fileUploadType  the file upload type
	 * @param orginalFileName the orginal file name
	 * @return the string
	 */
	private String composeFileName(FileUploadType fileUploadType, String orginalFileName) {
		StringBuilder builder = new StringBuilder();
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String extension = FilenameUtils.getExtension(orginalFileName);
		builder.append(fileUploadType).append("_").append(date).append(".").append(extension);
		return builder.toString();
	}

	/**
	 * Compose file path name.
	 *
	 * @param user     the user
	 * @param fileName the file name
	 * @return the string
	 */
	private String composeFilePathNameForUser(UserDTO user, String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append("user_").append(user.getId()).append(File.separator).append(fileName);
		return builder.toString();
	}

	/**
	 * Upload multiple files.
	 *
	 * @param files the files
	 * @return the list
	 */
	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	/**
	 * Download file.
	 *
	 * @param request  the request
	 * @return the response entity
	 */
	@GetMapping("/downloadFile/**")
	public ResponseEntity<Resource> downloadFile(HttpServletRequest request) {

		ResourceUrlProvider urlProvider = (ResourceUrlProvider) request
	            .getAttribute(ResourceUrlProvider.class.getCanonicalName());
	    String fileName = urlProvider.getPathMatcher().extractPathWithinPattern(
	            String.valueOf(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)),
	            String.valueOf(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)));

//		String filePathName = composeFilePathNameForUser(fileName);
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOG.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
