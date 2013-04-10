package fr.umlv.escape;

import fr.umlv.escape.front.FrontApplication;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	ProgressBar loadingBar;
	FrontApplication frontApplication;
	
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
					Thread.sleep(500); // SImule traitement
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
			loadingBar.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setContentView(frontApplication);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		loadingBar = (ProgressBar) findViewById(R.id.LoadingProgress);
	}
	
	@Override
	public void onStart(){
		super.onStart();

		loadingBar.setProgress(0);
		frontApplication = new FrontApplication(this);
		new LaunchApplication().execute(); // Asynchrony initialization of the game
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
