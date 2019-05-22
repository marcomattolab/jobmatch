package it.aranciaict.jobmatch.service.dto.email;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.aranciaict.jobmatch.service.dto.DocumentDTO;

/** email dto base class */
public abstract class AbstractEmailDTO {

    /** to */
    protected List<CandidateContactInformation> contacts = new ArrayList<CandidateContactInformation>();

    /** default language */
    protected String defaultLanguageKey;

    /** document */
    protected DocumentDTO attachment;

    /**
     * Get All Contact's email
     * @return the emails
     */
    public List<String> getEmails() {
        if(contacts == null || contacts.size() == 0) {
            return new ArrayList<String>();
        }

        return contacts.stream().map(contact -> contact.getEmail()).collect(Collectors.toList());
    }

    /**
     * Get The First contact inserted
     * @return the contact
     */
    public CandidateContactInformation getFirstContact() {
        return contacts == null || contacts.size() == 0  ? null : contacts.get(0);
    }

    /**
     * Add a receiver contact to the email
     * @param email the receiver email
     * @param langKey the language's receiver
     */
    public void addReceiverContact(String email, String langKey) {
        contacts.add(new CandidateContactInformation(email, langKey));
    }

    /**
     * @return the attachment
     */
    public DocumentDTO getAttachment() {
        return attachment;
    }

    /**
     * @param attachment the attachment to set
     */
    public void setAttachment(DocumentDTO attachment) {
        this.attachment = attachment;
    }

    /**
     * @return the contacts
     */
    public List<CandidateContactInformation> getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(List<CandidateContactInformation> contacts) {
        this.contacts = contacts;
    }

    /**
     * @return the defaultLanguageKey
     */
    public String getDefaultLanguageKey() {
        return defaultLanguageKey;
    }

    /**
     * @param defaultLanguageKey the defaultLanguageKey to set
     */
    public void setDefaultLanguageKey(String defaultLanguageKey) {
        this.defaultLanguageKey = defaultLanguageKey;
    }
    
}