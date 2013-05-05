package it.tinkergarage.studentexams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ExamsListActivity extends Activity {
	private TextView resultText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exams_list);
		
		resultText = (TextView) findViewById(R.id.responseText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exams_list, menu);
		return true;
	}

	public void fetchExams(View v) {
		try {
			URL url = new URL("http://172.16.21.27:8000/api/student/2/?format=json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			resultText.setText(readStream(connection.getInputStream()));
		} catch (Exception e) {
			resultText.setText(e.getMessage());
		}
	}

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
