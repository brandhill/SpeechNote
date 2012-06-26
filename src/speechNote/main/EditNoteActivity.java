package speechNote.main;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNoteActivity extends Activity {

	Button okEdit = null;
	Button cancelEdit = null;
	EditText editContent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.edit_note);
		okEdit = (Button) this.findViewById(R.id.ok_edit);
		cancelEdit = (Button) this.findViewById(R.id.cancel_edit);
		editContent = (EditText) this.findViewById(R.id.edit_content);
		String toEdit = this.getIntent().getStringExtra("toEdit");
		editContent.setText(toEdit);

		okEdit.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DataHelper dbHelper = new DataHelper(EditNoteActivity.this,
						"speech_note", null, 1);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				if (getIntent().getBooleanExtra("isNew", false)) {
					String sql = String
							.format("insert into notes values (null, \"%s\", datetime('now', 'localtime'))",
									editContent.getText().toString());
					db.execSQL(sql);
					finish();
					db.close();
					Toast.makeText(EditNoteActivity.this,
							"Create Successfully", Toast.LENGTH_SHORT).show();
				} else {
					String sql = String
							.format("update notes set content=\"%s\", modify_time=datetime('now', 'localtime') where id=%s",
									editContent.getText().toString(),
									getIntent().getStringExtra("toEditId"));
					db.execSQL(sql);
					Toast.makeText(EditNoteActivity.this, "Change Successfully", Toast.LENGTH_SHORT).show();
					finish();
					db.close();
				}
			}

		});

		cancelEdit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});

	}
}
