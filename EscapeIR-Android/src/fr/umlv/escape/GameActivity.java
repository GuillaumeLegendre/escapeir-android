package fr.umlv.escape;

import fr.umlv.escape.front.FrontApplication;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity{
	FrontApplication frontApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		frontApplication = new FrontApplication(this);
		setContentView(frontApplication);
	}
}
