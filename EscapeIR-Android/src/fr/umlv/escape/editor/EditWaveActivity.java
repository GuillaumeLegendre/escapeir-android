package fr.umlv.escape.editor;

import fr.umlv.escape.R;
import fr.umlv.escape.R.layout;
import fr.umlv.escape.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditWaveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_wave);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_wave, menu);
		return true;
	}

}
