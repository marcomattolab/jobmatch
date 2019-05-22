package it.aranciaict.jobmatch.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.aranciaict.jobmatch.config.FileStorageProperties;
import it.aranciaict.jobmatch.exceptions.FileStorageException;
import it.aranciaict.jobmatch.exceptions.MyFileNotFoundException;
import it.aranciaict.jobmatch.security.FileUploadType;
import it.aranciaict.jobmatch.service.FileStorageService;

/**
 * The Class FileStorageServiceImpl.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

	private static final Logger LOG = LoggerFactory.getLogger(FileStorageServiceImpl.class);

	/** The file storage location. */
	private final Path fileStorageLocation;

	/**
	 * File storage service.
	 *
	 * @param fileStorageProperties the file storage properties
	 */
	@Autowired
	public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
		if(org.apache.commons.lang3.StringUtils.isNotBlank(fileStorageProperties.getUploadDir())) {
			this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
						ex);
			}
		}else {
			this.fileStorageLocation = null;
		}
	}

	/**
	 * Store file.
	 *
	 * @param file the file
	 * @return the string
	 */
	@Override
	public String storeFile(MultipartFile file, String originalfileName) {

		String fileName = org.apache.commons.lang3.StringUtils.isNotBlank(originalfileName) ? originalfileName
				: file.getOriginalFilename();

		// Normalize file name
		fileName = StringUtils.cleanPath(fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Path parentDir = targetLocation.getParent();
			if (!Files.exists(parentDir)) {
				Files.createDirectories(parentDir);
			}
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * Load file as resource.
	 *
	 * @param fileName the file name
	 * @return the resource
	 */
	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

	/**
	 * Clear old files.
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void clearOldFiles() {
		LOG.info("start clear old profile pictures");
		try {
			Files.newDirectoryStream(fileStorageLocation).forEach(userDir -> {

				try {
					List<Path> orderedProfilePictures = Files.list(userDir)
							.filter(f -> !Files.isDirectory(f)
									&& f.toFile().getName().startsWith(FileUploadType.PROFILE_PICTURE.name()))
							.sorted(Comparator.comparingLong(f -> f.toFile().lastModified()))
							.collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(orderedProfilePictures) && orderedProfilePictures.size() > 1) {
						LOG.info("FOR " + userDir);
						for (int i = 0; i < orderedProfilePictures.size() - 1; i++) {
							Path fileToDelete = orderedProfilePictures.get(i);
							LOG.info("DELETE OLD PICTURE: " + fileToDelete);
							Files.delete(fileToDelete);
						}
					}
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			});
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			LOG.info("end clear old profile pictures");
		}
	}

}
