package it.aranciaict.jobmatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import it.aranciaict.jobmatch.repository.support.impl.JpaSpecificationExecutorWithProjectionImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseConfiguration.
 */
@Configuration
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class, basePackages="it.aranciaict.jobmatch.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    /** The env. */
    private final Environment env;

    /**
     * Instantiates a new database configuration.
     *
     * @param env the env
     */
    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    /**
     * Open the TCP port for the H2 database, so it is available remotely.
     *
     * @return the H2 database TCP server
     * @throws SQLException if the server failed to start
     */
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
//    public Object h2TCPServer() throws SQLException {
//        String port = getValidPortForH2();
//        log.debug("H2 database is available on port {}", port);
//        return H2ConfigurationHelper.createServer(port);
//    }
	
    /**
     * Gets the valid port for H 2.
     *
     * @return the valid port for H 2
     */
//    @SuppressWarnings("checkstyle:magicNumber")
//    private String getValidPortForH2() {
//        int port = Integer.parseInt(env.getProperty("server.port"));
//        if (port < 10000) {
//            port = 10000 + port;
//        } else {
//            if (port < 63536) {
//                port = port + 2000;
//            } else {
//                port = port - 2000;
//            }
//        }
//        return String.valueOf(port);
//    }
}
