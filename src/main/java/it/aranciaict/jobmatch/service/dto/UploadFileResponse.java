package it.aranciaict.jobmatch.service.dto;

/**
 * The Class UploadFileResponse.
 */
public class UploadFileResponse {
    
    /** The file name. */
    private String fileName;
    
    /** The file download uri. */
    private String fileDownloadUri;
    
    /** The file type. */
    private String fileType;
    
    /** The size. */
    private long size;

    /**
     * Instantiates a new upload file response.
     *
     * @param fileName the file name
     * @param fileDownloadUri the file download uri
     * @param fileType the file type
     * @param size the size
     */
    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    /**
     * Gets the file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName the new file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the file download uri.
     *
     * @return the file download uri
     */
    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    /**
     * Sets the file download uri.
     *
     * @param fileDownloadUri the new file download uri
     */
    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    /**
     * Gets the file type.
     *
     * @return the file type
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the file type.
     *
     * @param fileType the new file type
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size.
     *
     * @param size the new size
     */
    public void setSize(long size) {
        this.size = size;
    }
}
