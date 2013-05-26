package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class ShipPlacerActivity extends Activity implements OnTouchListener{
	int HEIGHT;
	int WIDTH;
	int pos_wave;
	ArrayAdapter<String> moveAdapter;
	private final String[] listMove = {"DownMove","DownUpMove","KamikazeMove",
										"LeftDampedMove","LeftMove","LeftRightMove",
										"RightDampedMove","RightMove","SquareLeft",
										"SquareRight","StraightLine","UpMove"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.builder_wave);
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
				throw new IllegalArgumentException();
			}
			pos_wave = extras.getInt("pos");
		} else {
			pos_wave = Integer.parseInt((String) savedInstanceState.getSerializable("pos"));
		}
		
		ImageView img;
		
		moveAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listMove);
		img = (ImageView)findViewById(R.id.mini_map_builder);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ShipEditor s = WaveObject.ships.get(pos_wave);
		s.x = (int)event.getX();
		s.y = (int)event.getY();
		Toast.makeText(getApplicationContext(), "Pos:"+s.x+":"+s.y, Toast.LENGTH_SHORT).show();
		return true;
	}
}
