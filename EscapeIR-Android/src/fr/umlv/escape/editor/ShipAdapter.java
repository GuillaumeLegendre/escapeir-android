package fr.umlv.escape.editor;

import java.util.ArrayList;

import fr.umlv.escape.R;
import fr.umlv.escape.game.Level;
import fr.umlv.escape.ship.Ship;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ShipAdapter extends BaseAdapter{
	ArrayList<Ship> shipList;
	LayoutInflater inflater;
	
	public ShipAdapter(Context context, ArrayList<Ship> shipList) {
		inflater = LayoutInflater.from(context);
		this.shipList = shipList;
	}
	
	@Override
	public int getCount() {
		return shipList.size();
	}

	@Override
	public Object getItem(int position) {
		return shipList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private class ViewHolder {
		Spinner selectShip;
		EditText life;
		//TextView shipName;
		Button putShip;
		Button removeShip;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.wave_layout, null);
			holder.selectShip = (Spinner)convertView.findViewById(R.id.select_ship);
			holder.life = (EditText)convertView.findViewById(R.id.life_ship);
			holder.putShip = (Button)convertView.findViewById(R.id.button_put_ship);
			holder.removeShip = (Button)convertView.findViewById(R.id.moins);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}

}
