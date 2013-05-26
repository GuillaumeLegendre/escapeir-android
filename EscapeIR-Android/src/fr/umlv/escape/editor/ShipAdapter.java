package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import fr.umlv.escape.game.Level;
import fr.umlv.escape.ship.Ship;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShipAdapter extends BaseAdapter{
	Ship ship;
	LayoutInflater inflater;

	public ShipAdapter(Context context, Ship ship) {
		inflater = LayoutInflater.from(context);
		this.ship = ship;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.wave_layout, null);
			holder.waveName = (TextView)convertView.findViewById(R.id.waveName);
			holder.waveDelay = (TextView)convertView.findViewById(R.id.waveDelay);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.waveName.setText("Name of the wave: " + level.getWaveList().get(position).getName());
		holder.waveDelay.setText("Delay before next wave's launching: "+String.valueOf(level.getDelayWaveList().get(position)));
		
		return convertView;
	}

}
