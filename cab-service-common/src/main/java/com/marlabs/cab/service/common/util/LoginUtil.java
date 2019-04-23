package com.marlabs.cab.service.common.util;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginUtil {

	private static Logger logger = LogManager.getLogger(LoginUtil.class);
	
	public static String getEncryptedPassword(byte[] passwordBytes, String algorithm ){
		String encryptedPassword = null;
		try{
			
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(passwordBytes);
			byte[] digestedBytes = messageDigest.digest();
			encryptedPassword = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
			
		}catch(Exception exception){
			logger.error("ERROR: Encrypting Password --> "+ exception.getCause());
			logger.error(exception.getStackTrace());
		}
		
		return encryptedPassword;
	}
	
}
