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

public class ShipPlacerActivity extends Activity implements OnTouchListener{
	int HEIGHT;
	int WIDTH;
	ArrayAdapter<String> moveAdapter;
	private final String[] listMove = {"DownMove","DownUpMove","KamikazeMove",
										"LeftDampedMove","LeftMove","LeftRightMove",
										"RightDampedMove","RightMove","SquareLeft",
										"SquareRight","StraightLine","UpMove"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExpandableListView elv = (ExpandableListView)findViewById(R.id.shoot_list_editor);
		ImageView img;
		
		moveAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listMove);
		elv.setAdapter(moveAdapter);
		img = (ImageView)findViewById(R.id.mini_map_builder);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//ShipEditor s = WaveObject.ships.get(index);
		//s.x = (int)event.getX();
		//s.y = (int)event.getY();
		return true;
	}
}
