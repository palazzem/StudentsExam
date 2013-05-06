package it.tinkergarage.studentexams.rest.resource;

import org.json.JSONException;
import org.json.JSONObject;

public class Exam implements Resource {
	private static final String KEY_COURSE = "course";
	private static final String KEY_REGISTRATION_DATE = "registration_date";
	private static final String KEY_VOTE = "vote";
	private static final String KEY_HONORS = "laude";
	private static final String KEY_NOTE = "note";
	
	private JSONObject jsonObject;
	
	public Exam(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public String getCourseName() {
		String result = null;
		try {
			result = jsonObject.getString(Exam.KEY_COURSE);
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getRegistrationDate() {
		String result = null;
		try {
			result = jsonObject.getString(Exam.KEY_REGISTRATION_DATE);
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getVote() {
		String result = null;
		try {
			result = jsonObject.getString(Exam.KEY_VOTE);
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getHonors() {
		String result = null;
		try {
			result = jsonObject.getString(Exam.KEY_HONORS);
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getNote() {
		String result = null;
		try {
			result = jsonObject.getString(Exam.KEY_NOTE);
		} catch (JSONException e) {
		}
		return result;
	}
	
	@Override
	public String toString() {
		return getCourseName();
	}
} 
