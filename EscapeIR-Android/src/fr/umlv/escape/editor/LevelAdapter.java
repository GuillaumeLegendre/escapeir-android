package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import fr.umlv.escape.game.Level;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
		Spinner waveList;
		EditText delay;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.select_wave_layout, null);
			holder.waveList = (Spinner)convertView.findViewById(R.id.wave_list_select);
			holder.delay = (EditText)convertView.findViewById(R.id.hint_launching_wave);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
}
