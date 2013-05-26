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
	ArrayAdapter<String> shootAdapter;
	private final String[] listMove = {"DownMove","DownUpMove","KamikazeMove",
										"LeftDampedMove","LeftMove","LeftRightMove",
										"RightDampedMove","RightMove","SquareLeft",
										"SquareRight","StraightLine","UpMove"};
	private final String[] listShoot = {"DoNotShoot","ShootDown","BatShipShoot",
										"FirstBossShoot","SecondBossShoot","ThirdBossShoot"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExpandableListView elv = (ExpandableListView)findViewById(R.id.shoot_list_editor);
		ImageView img;
		
		moveAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listMove);
		elv.setAdapter(moveAdapter);
		shootAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listShoot);
		elv.setAdapter(shootAdapter);
		img = (ImageView)findViewById(R.id.mini_map_builder);
		//img.setMaxHeight(HEIGHT/2);
		//img.setMaxWidth(WIDTH/2);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.default_ship_button:break;
		case R.id.bat_ship_button:break;
		case R.id.kamikaze_ship_button:break;
		case R.id.first_boss_button:break;
		case R.id.second_boss_button:break;
		case R.id.third_boss_button:break;

		default:
			event.getX();
			event.getY();
		}
		return true;
	}
}
