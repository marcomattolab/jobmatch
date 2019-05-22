package it.aranciaict.jobmatch.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Interface FileStorageService.
 */
public interface FileStorageService {

	/**
	 * Store file.
	 *
	 * @param file the file
	 * @param fileName 
	 * @return the string
	 */
	String storeFile(MultipartFile file, String fileName);
	
//	/**
//	 * Store file.
//	 *
//	 * @param file the file
//	 * @param fileName 
//	 * @return the string
//	 */
//	String storeFile(File file, String fileName);

	/**
	 * Load file as resource.
	 *
	 * @param fileName the file name
	 * @return the resource
	 */
	Resource loadFileAsResource(String fileName);

}