package net.atpco.rnd.customerprofile.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.RequiredArgsConstructor;
import net.atpco.rnd.customerprofile.util.StringUtils;

@RequiredArgsConstructor
public class CommandService {

	private final String swarmPath;
	private final static String UP = "swarm up ";
	private static String RESTRICT = "swarm --bzzaccount myKey --password passwordFile access new act --grant-keys keysFile dataKey";
	private static String GET = "curl localhost:8500/bzz-raw:/secureHash/filePath";
	private static String CREATE_FEED = "swarm --bzzaccount myHash --password <password file path> feed create --name carrier";
	private static String GETHASH = "curl localhost:8500/bzz:/secureHash";
	private static String FEED = "curl localhost:8500/bzz-feed:/?user=myKey&name=carrier&hex=1";
	private static String UPDATE_FEED = "swarm --bzzaccount myKey --password <password file path> feed update 0x1bsecureHash --name carrier";
	public static Runtime runtime = Runtime.getRuntime();

	public String createFeed(String myHash, String passwordFile, String carrier) {
//		swarm --bzzaccount 3ebd22d6e5214bc56d20c5816c4ebf60aca88b41 feed create --password <password file path> --name carrier
		String command = CREATE_FEED.replace("myHash", myHash).replace("swarm", swarmPath)
				.replace("<password file path>", passwordFile).replace("carrier", carrier);
		return runCommand(command);
	}

	public String updateFeed(String myKey, String secureHash, String passwordFile, String carrier) {
		String command = UPDATE_FEED.replace("myKey", myKey).replace("secureHash", secureHash)
				.replace("swarm", swarmPath).replace("carrier", carrier).replace("<password file path>", passwordFile);
		return runCommand(command);
	}

	public String issueUp(String filePath) {
		String command = UP + filePath;
		return runCommand(command.replace("swarm", swarmPath));
	}

	public String restrict(String myKey, String passwordFile, String keysFile, String dataKey) {
//		swarm --bzzaccount myKey --password passwordFile access new act --grant-keys keysFile dataKey
		String command = RESTRICT.replace("myKey", myKey).replace("passwordFile", passwordFile)
				.replace("keysFile", keysFile).replace("dataKey", dataKey).replace("swarm", swarmPath);
		return runCommand(command);
	}

	public String read(String secureHash, String filePath) {
		// "curl localhost:8500/bzz-raw:/secureHash/ path_to_file";
		String command = GET.replace("secureHash", secureHash).replace("filePath", filePath);
		return runCommand(command);
	}

	public String readLatestFromFeed(String myKey, String carrierPublicKey, String carrier) {
		// curl localhost:8500/bzz-feed:/?user=3ebd22d6e5214bc56d20c5816c4ebf60aca88b41
		// > readhash.txt
		String command = FEED.replace("myKey", myKey).replaceAll("carrier", carrier);
		String latestHash = runCommand(command);
		// TODO: Change to use util to get the string from the hex
		//String latestHash = StringUtils.decodeBinaryString(latestHashHexString);
		System.out.println("Latest Feed data: "+ latestHash);
		if (latestHash!=null && latestHash.length()>4) {
			latestHash = latestHash.substring(4) + "/";
		}
		System.out.println("Latest Feed data: "+ latestHash);
		command = GETHASH.replace("secureHash", latestHash);
		return runCommand(command);
	}

	private String runCommand(String command) {
		System.out.println("Executing Command: " + command);
		Process process;
		StringBuffer sb = new StringBuffer();
		String s = null;
		try {
			process = runtime.exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
				if (sb.length() > 0) {
					sb.append(System.lineSeparator());
				}
				sb.append(s);
			}

			// read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
//				sb.append(s).append(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Finished Executing Command:: " + command);
		return sb.toString();
	}

	private char[] runCommandBinaryOutput(String command) {
		System.out.println("Executing Command, binary output: " + command);
		Process process;
		char[] charArray = null;
		String s = null;
		try {
			process = runtime.exec(command);
			InputStream is = process.getInputStream();
			InputStreamReader stdIR = new InputStreamReader(is);
			
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			charArray = new char[1024];
			int i = 0;
			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			while (!stdIR.ready()) {
				Thread.sleep(200);
			}
			while ( stdIR.ready()) {
				int oneChar = stdIR.read();
				charArray[i] = (char) oneChar;
				i++;
				System.out.print(oneChar + " ");
			}
			System.out.println();
			char[] retCharArray = new char[i];
			System.arraycopy(charArray, 0, retCharArray, 0, i);
			charArray = retCharArray;
			// read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finished Executing Command: " + command);
		System.out.println("Returning: " + charArray);
		return charArray;
	}

}
