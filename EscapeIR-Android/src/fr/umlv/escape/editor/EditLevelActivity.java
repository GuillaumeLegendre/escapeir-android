package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import fr.umlv.escape.R.layout;
import fr.umlv.escape.R.menu;
import fr.umlv.escape.game.Wave;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class EditLevelActivity extends Activity {
	Spinner spinner_ship;
	EditText edit_text_life;
	Button button_delete;
	Button set_pos;
	int pos_wave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wave_layout);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
				throw new IllegalArgumentException();
			}
			pos_wave = extras.getInt("pos");
		} else {
			pos_wave = Integer.parseInt((String) savedInstanceState.getSerializable("pos"));
		}


		spinner_ship = (Spinner) findViewById(R.id.select_ship);
		edit_text_life = (EditText) findViewById(R.id.life_ship);
		button_delete = (Button) findViewById(R.id.moins);
		set_pos = (Button) findViewById(R.id.button_put_ship);


		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.ships_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_ship.setAdapter(adapter);

		button_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WaveObject.ships.remove(pos_wave);
				Intent intent = new Intent(getApplicationContext(), EditorWaveActivity.class);
				startActivity(intent);
			}
		});

		set_pos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ShipPlacerActivity.class);
				startActivity(intent);
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
			ShipEditor s = WaveObject.ships.get(pos_wave);
			Editable t = edit_text_life.getText();
			if(t != null && !t.toString().equals(""))
				s.health = Integer.valueOf(edit_text_life.getText().toString());
			s.name = spinner_ship.getSelectedItem().toString();
			WaveObject.ships.set(pos_wave,s);
			Intent intent = new Intent(getApplicationContext(), EditorWaveActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
