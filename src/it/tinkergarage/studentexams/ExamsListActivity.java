package it.tinkergarage.studentexams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExamsListActivity extends Activity {
	private TextView resultText = null;
	private ProgressBar progressBar = null;
	private Button fetchButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exams_list);
		
		resultText = (TextView) findViewById(R.id.responseText);
		progressBar = (ProgressBar) findViewById(R.id.fetchProgress);
		fetchButton = (Button) findViewById(R.id.fetchExamButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exams_list, menu);
		return true;
	}

	public void fetchExams(View v) {
		new FetchExamsTask().execute();
	}
	
	private class FetchExamsTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
			fetchButton.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String response = "";
			try {
				URL url = new URL("http://192.168.2.148:8000/api/exam/?format=json");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				response = readStream(connection.getInputStream());
			} catch (Exception e) {
				response = e.getMessage();
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String response) {
			String result = "";
			
			progressBar.setVisibility(View.INVISIBLE);
			fetchButton.setEnabled(true);
			
			// Parse JSONObject as simple text
			try {
				JSONArray jsonResult = new JSONArray(response);
				for (int i = 0; i < jsonResult.length(); i++) {
					result += "" + jsonResult.getJSONObject(i).getString("course") + "\n";
				}
			} catch (Exception e) {
				result = e.getMessage();
			}
			
			resultText.setText(result);
			super.onPostExecute(response);
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
