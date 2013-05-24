package fr.umlv.escape;

import java.io.IOException;

import fr.umlv.escape.file.IllegalFormatContentFile;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.game.Game;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

public class GameActivity extends Activity{
	FrontApplication frontApplication;
	Game game;
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();  // deprecated
		int height = display.getHeight();
		
		frontApplication = new FrontApplication(this,width,height);
		this.game = Game.getTheGame();
		setContentView(R.layout.levels_menu);			
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		game.stop();
	}
	
	/**
	 * Asynchronous task that initialize the application at the launching
	 * and set the content view with the main menu
	 */
	private class LaunchApplication extends AsyncTask<Void, Integer, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			//Initialize here the game
			for(int i=0; i<20; ++i){
				try {
					Thread.sleep(5); // SImule traitement
					publishProgress(i*5);
				} catch (InterruptedException e) {
					break;
				}
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
	}
	
	public void launchLevel(View view){
		String[] levelsNames=null;
		
		switch(view.getId())
		{
		case R.id.level1Button:{
			levelsNames = new String[3];
			levelsNames[0]="level1";
			levelsNames[1]="level2";
			levelsNames[2]="level3"; 
			break;
		}
		case R.id.level2Button: {
			levelsNames = new String[2];
			levelsNames[0]="level2";
			levelsNames[1]="level3"; 
			break;
		}
		case R.id.level3Button: {
			levelsNames = new String[1];
			levelsNames[0]="level3"; 
			break;
		}
		default:
			return;
		}
		
		try {
			this.game.initializeGame(this,frontApplication,levelsNames);	
			this.game.startGame(getApplicationContext());
			setContentView(frontApplication);
		} catch (IOException e) {
			onDestroy();
		} catch (IllegalFormatContentFile e) {
			onDestroy();
		}	
	}

}
