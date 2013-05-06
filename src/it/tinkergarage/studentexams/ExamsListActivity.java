package it.tinkergarage.studentexams;

import it.tinkergarage.studentexams.rest.ExamAPI;
import it.tinkergarage.studentexams.rest.resource.Exam;

import java.util.ArrayList;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ExamsListActivity extends Activity {
	private ProgressBar progressBar = null;
	private Button fetchButton = null;
	private ListView resultList = null;
	private ArrayAdapter<Exam> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exams_list);
		
		progressBar = (ProgressBar) findViewById(R.id.fetchProgress);
		fetchButton = (Button) findViewById(R.id.fetchExamButton);
		resultList = (ListView) findViewById(R.id.examsListView);
		
		// Exam ListView
		adapter = new ArrayAdapter<Exam>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<Exam>());
		resultList.setAdapter(adapter);
		resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				Exam exam = (Exam) adapter.getItemAtPosition(position);
				Intent intent = new Intent(ExamsListActivity.this, ExamDetailActivity.class);
				intent.putExtra("name", exam.getCourseName());
				intent.putExtra("date", exam.getRegistrationDate());
				intent.putExtra("vote", exam.getVote());
				intent.putExtra("honor", exam.getHonors());
				intent.putExtra("note", exam.getNote());
				startActivity(intent);
			}
		});
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
	
	private class FetchExamsTask extends AsyncTask<Void, Void, ArrayList<Exam>> {
		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
			fetchButton.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected ArrayList<Exam> doInBackground(Void... params) {
			ArrayList<Exam> exams = new ArrayList<Exam>();
			
			try {
				// Parse JSONObject as simple text and put values inside adapter
				JSONArray jsonResult = new JSONArray(new ExamAPI().getExams());
				for (int i = 0; i < jsonResult.length(); i++) {
					exams.add(new Exam(jsonResult.getJSONObject(i)));
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			
			return exams;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Exam> exams) {
			progressBar.setVisibility(View.GONE);
			fetchButton.setEnabled(true);
			
			adapter.clear();
			for (Exam exam : exams) {
				adapter.add(exam);
			}
			
			super.onPostExecute(exams);
		}
	}
}
