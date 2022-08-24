package com.newsletter.service;

import java.util.List;

public interface MailService {
	
	public String sendAttachmentEmail (String fileName, byte[] file, List<String> destinatarios);

}
