package net.atpco.rnd.customerprofile.service;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import net.atpco.rnd.customerprofile.util.FileManager;
import net.atpco.rnd.customerprofile.util.HashManager;
import net.atpco.rnd.customerprofile.util.StringUtils;

@RequiredArgsConstructor
public class ProfileService {
	private final String myKey;
	private final String privatePhrase;
	private final CommandService cmd;
	private final FileManager fileManager;
	private final HashManager hashManager;
	
	public Map<String, String> getCarrierProfile(String carrierPublicKey, String carrier) {
		String filePath = hashManager.getCarrierCurrentFileLocation(carrierPublicKey);
		String secureHash = hashManager.getCarrierSecureHash(carrierPublicKey);
		String data =  cmd.read(secureHash, filePath);
		return null;
	}
	 
	public Map<String, String> getFeedCarrierProfile(String carrierPublicKey, String carrier) {
		String data = cmd.readLatestFromFeed(myKey, carrierPublicKey, carrier);
		return StringUtils.parseLinesToMap(data);
	}
	
	public void updateCarrierProfile(String carrierPublicKey, String carrier, Map<String, String> values) {
		String secureHash = createCarrierProfile(carrierPublicKey, carrier, values);
		String passwordFile = fileManager.preparePassword(privatePhrase);
		String result = cmd.updateFeed(myKey, secureHash, passwordFile, carrier);
	}

	public String createCarrierProfile(String carrierPublicKey, String carrier, Map<String, String> newValues) {
		String fileLocation = fileManager.prepareProfile(carrier, newValues);
		String contentHash = cmd.issueUp(fileLocation);
		hashManager.setCarrierCurrentFilePath(carrierPublicKey, fileLocation);
		
		String keysFile = fileManager.prepareKeys(carrierPublicKey); 
		String passwordFile = fileManager.preparePassword(privatePhrase);
		String secureHash = cmd.restrict(myKey, passwordFile, keysFile, contentHash);
		hashManager.setCarrierSecureHash(carrierPublicKey, secureHash);
		//TODO store carrierPublicKey : secureHash
		return secureHash;
	}

	public String createFeed(String carrier) {
		String passwordFile = fileManager.preparePassword(privatePhrase);
		String hash = cmd.createFeed(myKey, passwordFile, carrier);
		
		return hash;
	}
}
