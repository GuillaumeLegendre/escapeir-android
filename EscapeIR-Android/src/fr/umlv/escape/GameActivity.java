package fr.umlv.escape;

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
		
		frontApplication = new FrontApplication(this);
		this.game = Game.getTheGame();
		this.game.setFrontApplication(frontApplication);
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

}
