package it.tinkergarage.studentexams;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ExamDetailActivity extends Activity {
	private TextView textName = null;
	private TextView textDate = null;
	private TextView textVote = null;
	private TextView textHonor = null;
	private TextView textNote = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_detail);
		
		textName = (TextView) findViewById(R.id.textName);
		textDate = (TextView) findViewById(R.id.textDate);
		textVote = (TextView) findViewById(R.id.textVote);
		textHonor = (TextView) findViewById(R.id.textHonor);
		textNote = (TextView) findViewById(R.id.textNote);
		
		textName.setText(getIntent().getStringExtra("name"));
		textDate.setText(getIntent().getStringExtra("date"));
		textVote.setText(getIntent().getStringExtra("vote"));
		textHonor.setText(getIntent().getStringExtra("honor"));
		textNote.setText(getIntent().getStringExtra("note"));
	}
}
