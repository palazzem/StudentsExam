package it.tinkergarage.studentexams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ExamsListActivity extends Activity {
	private ProgressBar progressBar = null;
	private Button fetchButton = null;
	private ListView resultList = null;
	private ArrayAdapter<String> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exams_list);
		
		progressBar = (ProgressBar) findViewById(R.id.fetchProgress);
		fetchButton = (Button) findViewById(R.id.fetchExamButton);
		resultList = (ListView) findViewById(R.id.examsListView);
		
		// Exam ListView
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
		resultList.setAdapter(adapter);
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
				URL url = new URL("http://172.16.21.27:8000/api/exam/?format=json");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				response = readStream(connection.getInputStream());
			} catch (Exception e) {
				response = e.getMessage();
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String response) {
			progressBar.setVisibility(View.GONE);
			fetchButton.setEnabled(true);
			
			// Parse JSONObject as simple text and put values inside adapter
			try {
				JSONArray jsonResult = new JSONArray(response);
				adapter.clear();
				for (int i = 0; i < jsonResult.length(); i++) {
					adapter.add(jsonResult.getJSONObject(i).getString("course"));
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			
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
