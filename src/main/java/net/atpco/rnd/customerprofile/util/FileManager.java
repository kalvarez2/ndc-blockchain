package net.atpco.rnd.customerprofile.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import lombok.SneakyThrows;

public class FileManager {

	@SneakyThrows
	public String preparePassword(String privatePhase) {
		String fileName = "password.txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(privatePhase);
        } 	
		return fileName;
	}

	@SneakyThrows
	public String prepareKeys(String... keys) {
		String fileName = "keys.txt";
		boolean first = true;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (int i = 0; i < keys.length; i++) {
				if (first) {
					first = false;
				} else {
					writer.newLine();
				}
	            writer.write(keys[i]);
			}
        } 	
		return fileName;
	}
	
	@SneakyThrows
	public String prepareProfile(String carrier, Map<String, String> data) {
		
		String fileLocation = carrier + ".txt";
		try (OutputStream output = new FileOutputStream(fileLocation)) {
			Properties prop = new Properties();
			for (Entry<String, String> entry : data.entrySet()) {
				prop.setProperty(entry.getKey(), entry.getValue());
			}
			
			prop.store(output, null);
		}
		return fileLocation;
	}
	
	

}
