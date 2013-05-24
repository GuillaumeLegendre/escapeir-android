package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import fr.umlv.escape.game.Level;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

public class LevelAdapter extends BaseAdapter{
	Level level;
	LayoutInflater inflater;
	
	public LevelAdapter(Context context, Level level) {
		inflater = LayoutInflater.from(context);
		this.level = level;
	}
	
	@Override
	public int getCount() {
		return level.getWaveList().size();
	}

	@Override
	public Object getItem(int position) {
		return level.getWaveList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView waveName;
		TextView waveDelay;
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
		holder.waveDelay.setText("Delay befor next wave's launching: "+String.valueOf(level.getDelayWaveList().get(position)));
		
		return convertView;
	}
}
