package speechNote.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class SpeechNoteActivity extends ListActivity {
	/** Called when the activity is first created. */

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private ArrayList<HashMap<String, String>> list;
	private DataHelper dbHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.init();
		this.refreshList();

		this.getListView().setOnItemLongClickListener(
				new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(SpeechNoteActivity.this,
								EditOrDeleteActivity.class);
						intent.putExtra("toEditId", list.get((int) id)
								.get("id"));
						intent.putExtra("toEdit",
								list.get((int) id).get("content"));
						intent.putExtra("toEditTime",
								list.get((int) id).get("modify_time"));
						startActivity(intent);
						return false;
					}

				});
	}

	private void refreshList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("notes", new String[] { "id", "content",
				"modify_time" }, null, null, null, null, "modify_time desc");
		list = new ArrayList<HashMap<String, String>>();
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String modify_time = cursor.getString(cursor
					.getColumnIndex("modify_time"));
			HashMap<String, String> newMap = new HashMap<String, String>();
			newMap.put("id", id);
			newMap.put("content", content);
			newMap.put("modify_time", modify_time);
			list.add(newMap);
		}
		this.setNewAdapter();
		cursor.close();
		db.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.refreshList();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it
			// could have heard

			super.onActivityResult(requestCode, resultCode, data);

			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			String[] res = new String[5];

			for (int i = 0; i < 5; i++) {
				res[i] = matches.get(i);
			}

			Intent intent = new Intent();
			intent.setClass(SpeechNoteActivity.this,
					SelectCandidateActivity.class);
			intent.putExtra("candidates", res);
			startActivity(intent);
		}
	}

	private void init() {
		list = new ArrayList<HashMap<String, String>>();
		dbHelper = new DataHelper(SpeechNoteActivity.this, "speech_note", null,
				1);
	}

	private void setNewAdapter() {

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.notelist, new String[] { "content", "modify_time" },
				new int[] { R.id.modify_time, R.id.content_short });

		this.setListAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, this.getString(R.string.add_new_menu_item));
		menu.add(0, 2, 2, this.getString(R.string.about));
		menu.add(0, 3, 3, this.getString(R.string.exit));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech Note");
			startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		} else if (item.getItemId() == 2) {
			Intent intent = new Intent();
			intent.setClass(SpeechNoteActivity.this, AboutActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 3) {
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

}