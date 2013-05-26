package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import fr.umlv.escape.game.Level;
import fr.umlv.escape.game.Wave;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class EditorLevelActivity extends Activity {
	Level level;
	ArrayAdapter<Wave> arrayAdapter;
	//LevelAdapter levelAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor_level);
		this.level = new Level("edited_level");
		level.addWaveList(new Wave("LeftRight"));

		final EditText level_name = (EditText) findViewById(R.id.level_name);	
		final Button button_level_builder = (Button) findViewById(R.id.plus);
		final ListView lv = (ListView) findViewById(R.id.waves_list_editor);

		//levelAdapter=new LevelAdapter(getApplicationContext(),level);
		arrayAdapter=new ArrayAdapter<Wave>(getApplicationContext(), android.R.layout.simple_list_item_1, level.getWaveList());
		lv.setAdapter(arrayAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), EditWaveActivity.class);
				intent.putExtra("pos", arg2);
				startActivity(intent);
			}
		});

		button_level_builder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				level.addWaveList(new Wave("LeftRight"));
				arrayAdapter.notifyDataSetChanged();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
