package fr.umlv.escape.editor;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import fr.umlv.escape.R;
import fr.umlv.escape.ship.Ship;

public class EditorWaveActivity extends Activity {
	List<Ship> list_ship;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor_waves);

		final EditText wave_name = (EditText) findViewById(R.id.wave_name);
		
		final Button button_wave_builder = (Button) findViewById(R.id.plus);
		
		list_ship = new ArrayList<Ship>();
		list_ship.add(new Ship("default_ship", 0, null, null, null, null));
		
		final ListView lv = (ListView) findViewById(R.id.waves_list_editor);
		ShipAdapter shipAdapter=new ShipAdapter(this,ship);
		list_ship.setAdapter(shipAdapter);
//		list.setOnItemClickListener(this);
		
		button_wave_builder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
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
//		LayoutInflater li = getLayoutInflater(); 
//		switch (item.getItemId()) {
//		case R.id.grid:
//			fl.addView(li.inflate(R.layout.grid_layout, null));
//			return true;
//		default:
			return super.onOptionsItemSelected(item);
//		}	
	}
	
}