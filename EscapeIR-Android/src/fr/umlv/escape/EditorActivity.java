package fr.umlv.escape;


import fr.umlv.escape.editor.EditorMapActivity;
import fr.umlv.escape.editor.EditorWaveActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class EditorActivity extends Activity implements OnClickListener{
	ImageView map;
	int HEIGHT;
	int WIDTH;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_builder);
		
		final Button button_wave_builder = (Button) findViewById(R.id.button_wave_builder);
		final Button button_level_builder = (Button) findViewById(R.id.button_level_builder);
		button_wave_builder.setOnClickListener(this);
		button_level_builder.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.button_wave_builder:
			intent = new Intent(this, EditorWaveActivity.class);
			startActivity(intent);
			break;
		case R.id.button_level_builder:
			intent = new Intent(this, EditorMapActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
