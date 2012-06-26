package speechNote.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class EditOrDeleteActivity extends ListActivity {

	private ArrayList<HashMap<String, String>> selections = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.edit_or_delete);
		selections = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> editSelection = new HashMap<String, String>();
		HashMap<String, String> deleteSelection = new HashMap<String, String>();
		editSelection.put("selection", this.getString(R.string.edit_selection));
		deleteSelection.put("selection",
				this.getString(R.string.delete_selection));
		selections.add(editSelection);
		selections.add(deleteSelection);
		SimpleAdapter adapter = new SimpleAdapter(this, selections,
				R.layout.edit_or_delete_list, new String[] { "selection" },
				new int[] { R.id.edit_or_delete_selection });
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if (id == 0) {
			Intent intent = new Intent();
			intent.setClass(EditOrDeleteActivity.this, EditNoteActivity.class);
			intent.putExtra("toEdit", getIntent().getStringExtra("toEdit"));
			intent.putExtra("toEditId", getIntent().getStringExtra("toEditId"));
			intent.putExtra("toEditTime",
					getIntent().getStringExtra("toEditTime"));
			intent.putExtra("isNew", false);
			startActivity(intent);
			finish();
		} else {
			DataHelper dbHelper = new DataHelper(EditOrDeleteActivity.this,
					"speech_note", null, 1);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String sql = String.format("delete from notes where id=%s",
					getIntent().getStringExtra("toEditId"));
			db.execSQL(sql);
			db.close();
			finish();
			Toast.makeText(EditOrDeleteActivity.this, "Delete successfully",
					Toast.LENGTH_SHORT).show();
		}
	}

}
