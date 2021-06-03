package com.iuh.ABCStore.services;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmailSenderService implements Serializable{
	
	
	private static final long serialVersionUID = -5855434065902650852L;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailSenderService( JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}

	public boolean sendEmail(SimpleMailMessage email) {
		
		javaMailSender.send(email);
		return false;
	}
}
