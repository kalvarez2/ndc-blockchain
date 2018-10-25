package net.atpco.rnd.customerprofile.domain;

import java.util.Map;

import lombok.Data;

@Data
public class CreateProfileRequest {
	
	private String carrierPublicKey;
	private String carrier; 
	private Map<String, String> newValues;
}
