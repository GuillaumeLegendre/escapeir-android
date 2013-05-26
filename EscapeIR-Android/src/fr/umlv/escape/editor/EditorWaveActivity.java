package fr.umlv.escape.editor;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import fr.umlv.escape.R;
import fr.umlv.escape.game.Wave;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.ship.ShipFactory;

public class EditorWaveActivity extends Activity {
//	ShipAdapter shipAdapter;
	ArrayAdapter<ShipEditor> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor_waves);

		final EditText wave_name = (EditText) findViewById(R.id.wave_name);
		final Button button_wave_builder = (Button) findViewById(R.id.plus);
		
		if(WaveObject.ships.size() == 0){
			WaveObject.ships.add(new ShipEditor());
		}
		
		final ListView lv = (ListView) findViewById(R.id.waves_list_editor);
		lv.setItemsCanFocus(true);
		//shipAdapter=new ShipAdapter(getApplicationContext(),list_ship);
		arrayAdapter=new ArrayAdapter<ShipEditor>(getApplicationContext(), android.R.layout.simple_list_item_1, WaveObject.ships);
		lv.setAdapter(arrayAdapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), EditLevelActivity.class);
				intent.putExtra("pos", arg2);
				startActivity(intent);
			}
		});
		
		button_wave_builder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WaveObject.ships.add(new ShipEditor());
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