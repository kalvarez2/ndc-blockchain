package net.atpco.rnd.customerprofile.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class HashManager {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private class CarrierInfo {
		private String latestHash;
		private String filelocation;
	}

	private final Map<String, CarrierInfo> hashStore = new HashMap<>();

	public String getCarrierSecureHash(String carrierPublicKey) {
		CarrierInfo carrierInfo = hashStore.get(carrierPublicKey);
		return carrierInfo != null ? carrierInfo.getLatestHash() : null;
	}

	public String getCarrierCurrentFileLocation(String carrierPublicKey) {
		CarrierInfo carrierInfo = hashStore.get(carrierPublicKey);
		return carrierInfo != null ? carrierInfo.getFilelocation() : null;

	}

	public void setCarrierSecureHash(String carrierPublicKey, String secureHash) {
		CarrierInfo carrierInfo = hashStore.computeIfAbsent(carrierPublicKey, cpk -> new CarrierInfo());
		carrierInfo.setLatestHash(secureHash);
	}

	public void setCarrierCurrentFilePath(String carrierPublicKey, String filelocation) {
		CarrierInfo carrierInfo = hashStore.computeIfAbsent(carrierPublicKey, cpk -> new CarrierInfo());
		carrierInfo.setFilelocation(filelocation);
	}

	@PreDestroy
	public void persist() throws IOException {
		String fileName = "hashes.txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (Entry<String, CarrierInfo> entry : hashStore.entrySet()) {
				writer.write(entry.getKey());
				writer.write(",");
				CarrierInfo carrierInfo = entry.getValue();
				writer.write(carrierInfo.getFilelocation());
				writer.write(",");
				writer.write(carrierInfo.getLatestHash());
				writer.newLine();
			}
		}
	}

	public void dump() throws IOException {
		for (Entry<String, CarrierInfo> entry : hashStore.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(",");
			CarrierInfo carrierInfo = entry.getValue();
			System.out.print(carrierInfo.getFilelocation());
			System.out.print(",");
			System.out.println(carrierInfo.getLatestHash());
		}
	}

	@PostConstruct
	public void load() throws IOException {
		String fileName = "hashes.txt";
		hashStore.clear();
		File hashFile = new File(fileName);
		if (hashFile.exists()) {
			Files.lines(hashFile//
					.toPath())//
					.forEach(line -> {
						String[] aLine = line.split(",");
						if (aLine.length == 3) {
							CarrierInfo carrierInfo = new CarrierInfo(aLine[1], aLine[2]);
							String carrierPublicKey = aLine[0];
							hashStore.put(carrierPublicKey, carrierInfo);
						}
					});
		}
	}

	
}
