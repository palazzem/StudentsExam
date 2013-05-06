package it.tinkergarage.studentexams.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RestClient {

	protected String getMethod(URL url) throws IOException {
		String response;
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		response = readStream(connection.getInputStream());
			
		return response;
	}

	protected String postMethod(URL url) {
		return null;
	}

	protected String putMethod(URL url) {
		return null;
	}

	protected String deleteMethod(URL url) {
		return null;
	}
	
	/* Utility methods */
	private String readStream(InputStream in) {
		String response = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null) {
				response += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
}
