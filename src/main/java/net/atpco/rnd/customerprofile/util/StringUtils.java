package net.atpco.rnd.customerprofile.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class StringUtils {
	public static String decodeBinaryString(char[] hexEncondedString) {
		byte[] bytes;
		try {
			bytes = Hex.decodeHex(hexEncondedString);
			return new String(bytes, "UTF-8");
		} catch (DecoderException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> parseLinesToMap(String lines) {
		Map<String, String> ret = new HashMap<String, String>();
		if (lines != null) {
			String[] aLines = lines.split(System.lineSeparator());
			for (int i = 0; i < aLines.length; i++) {
				if (!aLines[i].startsWith("#")) {
					String[] keyValue = aLines[i].split("=");
					ret.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return ret;
	}

}
