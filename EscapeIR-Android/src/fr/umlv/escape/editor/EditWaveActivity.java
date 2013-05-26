package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import fr.umlv.escape.R.layout;
import fr.umlv.escape.R.menu;
import fr.umlv.escape.game.Wave;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class EditWaveActivity extends Activity {
	Spinner spinner_wave;
	EditText edit_text_launch;
	int pos_wave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_wave);
		
		if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		    	throw new IllegalArgumentException();
		    }
			pos_wave = extras.getInt("pos");
		} else {
			pos_wave = Integer.parseInt((String) savedInstanceState.getSerializable("pos"));
		}

		
		spinner_wave = (Spinner) findViewById(R.id.wave_list_select);
		edit_text_launch = (EditText) findViewById(R.id.launching_wave);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.waves_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_wave.setAdapter(adapter);

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
			Wave w = new Wave(spinner_wave.getSelectedItem().toString());
			
			EditedLevel.level.getWaveList().set(pos_wave,w);
			Long l = 0;
			if(edit_text_launch.getText() != null)
				l = Long.valueOf(edit_text_launch.getText().toString()
			EditedLevel.level.getDelayWaveList().set(pos_wave, l));
			Intent intent = new Intent(getApplicationContext(), EditorLevelActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
