package it.aranciaict.jobmatch.config;

import java.time.Duration;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    /**
     * Instantiates a new cache configuration.
     *
     * @param jHipsterProperties the j hipster properties
     */
    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    /**
     * Cache manager customizer.
     *
     * @return the j cache manager customizer
     */
    @Bean
    @SuppressWarnings("checkstyle:executablestatementcount")
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(it.aranciaict.jobmatch.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Candidate.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Candidate.class.getName() + ".documents", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Candidate.class.getName() + ".jobExperiences", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Candidate.class.getName() + ".educations", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Candidate.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.JobExperience.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Education.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Skill.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Skill.class.getName() + ".skillTags", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.SkillTag.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.SponsoringInstitution.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Document.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Company.class.getName() + ".sectors", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.CompanySector.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Project.class.getName() + ".jobOffers", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.JobOffer.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.JobOffer.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.JobOfferSkill.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.AppliedJob.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.AppliedJob.class.getName() + ".appliedJobIterations", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.AppliedJobIteration.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.Company.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.CompanySkill.class.getName(), jcacheConfiguration);
            cm.createCache(it.aranciaict.jobmatch.domain.DirectApplication.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
