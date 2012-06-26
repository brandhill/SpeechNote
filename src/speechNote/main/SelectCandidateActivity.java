package speechNote.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SelectCandidateActivity extends ListActivity {

	private ArrayList<HashMap<String, String>> candidate_list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.select_candidate);
		String[] candidates = this.getIntent()
				.getStringArrayExtra("candidates");
		candidate_list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < candidates.length; i++) {
			// System.out.println("In new Activity " + candidates[i]);
			HashMap<String, String> newMap = new HashMap<String, String>();
			newMap.put("candidate_item", candidates[i]);
			candidate_list.add(newMap);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this, candidate_list,
				R.layout.select_candidate_list,
				new String[] { "candidate_item" },
				new int[] { R.id.candidate_item });
	
		this.setListAdapter(adapter);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		System.out.println("touch " + id);
		System.out.println(candidate_list.size());
		
		HashMap<String, String> tmp = candidate_list.get((int) id);
		String toEdit = candidate_list.get((int) id).get("candidate_item");
		Intent intent = new Intent();
		intent.setClass(SelectCandidateActivity.this, EditNoteActivity.class);
		intent.putExtra("toEdit", toEdit);
		intent.putExtra("isNew", true);
		this.startActivity(intent);
		this.finish();
	}
}
