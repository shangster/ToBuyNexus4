import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class StoreChecker {

	public static boolean isNexus4SoldOut() {
		if (isStoreCheckerWorking()) {
			String result = getHTML("https://play.google.com/store/devices/details?id=nexus_4_8gb");
			if (result.contains("Sold out")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isStoreCheckerWorking() {
		String result = getHTML("https://play.google.com/store/devices/details?id=nexus_4_8gb");
		if (result.contains("hardware-price-description")) {
			return true;
		} else {
			return false;
		}
	}

	private static String getHTML(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while (!(line = rd.readLine())
					.contains("hardware-price-description hardware-large-print hardware-sold-out")) {
				result += line;
			}
			result += line; // read one more line after you find the description
							// containing the product status
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String randomText() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	public static void main(String args[]) {
		int count = 0;
		int failures = 0;
		FileWriter fstream;
		BufferedWriter out = null;
		// Close the output stream
		try {
			fstream = new FileWriter("out.txt");
			out = new BufferedWriter(fstream);
			while(count<3 && failures <3){
				if (isStoreCheckerWorking()) {
					if (!StoreChecker.isNexus4SoldOut()) {
						TwitterServiceImpl.sendDirectMessage(randomText()
								+ "=====Nexus 4! GOGOGO!=====" + randomText());
						out.write("In Stock "
								+ new Timestamp(System.currentTimeMillis()));
						out.newLine();
						System.out.println("In Stock");
						count++;
					} else {
						System.out.println("Sold Out");
						out.write("Sold Out "
								+ new Timestamp(System.currentTimeMillis()));
						out.newLine();
					}
				} else {
					failures++;
					System.out.println("Store Checker not working");
					out.write("Store Checker is not working "
							+ new Timestamp(System.currentTimeMillis()));
					out.newLine();
					TwitterServiceImpl.sendDirectMessage(randomText()
							+ "=======Store Checker is failing!======"
							+ randomText());
				}
				out.flush();
			}
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
