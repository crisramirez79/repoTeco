package com.aws.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aws.model.MailRequest;
import com.aws.model.msg.EntityResponse;
import com.aws.service.MailService;

@RestController
@RequestMapping("/")
public class Controller {
	Logger logger = LoggerFactory.getLogger(Controller.class);
	private final String MODEL_LINK = "link";
	
	@Autowired
	private MailService mailService;
	
	@Value("${springboot.api.error.message}")
	private String apiErrorMsg;
	
	@Value("${spring.mail.username}")
	private String emailFrom;
	
	@Value("${spring.mail.subject}")
	private String emailSubject;
	
	/**
	  * Method that ** SEND AN EMAIL ** to validate the user email address.
	  * @param request
	  * @return
	  */
	@RequestMapping(value="utils/email", method=RequestMethod.POST)	
	public ResponseEntity<Object> sendEmail(@RequestBody MailRequest request) {
		//Send 		
		try {
			String emailValidationResponse = "";
			request.setFrom(emailFrom);
			request.setSubject(emailSubject);
			Map<String, Object> model = new HashMap<>();		
			model.put(MODEL_LINK, emailValidationResponse+"&email="+request.getTo());
			mailService.sendEmail(request, model);		
			EntityResponse eResp = new EntityResponse();
			eResp.setResponse("success.");
			return new ResponseEntity<>(eResp,  HttpStatus.OK);					
		}
		catch(MailSendException e) {
			return new ResponseEntity<>("Error:" + e,  HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

}
