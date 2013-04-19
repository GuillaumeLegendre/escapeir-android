package fr.umlv.escape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.game.Game;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class GameActivity extends Activity{
	FrontApplication frontApplication;
	Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.game = new Game(
		frontApplication = new FrontApplication(this);
		setContentView(frontApplication);
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
			Vec2  gravity = new Vec2(0,0f); // No gravity in space
			World world = new World(gravity);
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			loadingBar.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			//setContentView(frontApplication);
			setContentView(R.layout.main_menu);
			//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
	}

}
