package fr.umlv.escape;

import java.util.concurrent.atomic.AtomicBoolean;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	ProgressBar loadingBar;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg){
			loadingBar.incrementProgressBy(5);
		}
	};
	AtomicBoolean isRunning = new AtomicBoolean(false);
	
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
		Thread background = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{

					//Initialize here the game
					for(int i=0; i<20 && isRunning.get(); ++i){
						Thread.sleep(1000);
						handler.sendMessage(handler.obtainMessage());
					}
				} catch (Throwable t) {
					// end of the background thread
				}
			}
		});
		
		isRunning.set(true);
		background.start();
	}

	@Override
	public void onStop() {
		super.onStop();
		isRunning.set(false);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
