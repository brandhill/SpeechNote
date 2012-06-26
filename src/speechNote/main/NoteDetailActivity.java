package speechNote.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NoteDetailActivity extends Activity {

	private TextView detail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.note_detail);
		detail = (TextView) this.findViewById(R.id.note_detail);
		detail.setText(getIntent().getStringExtra("detail"));
	}

}
