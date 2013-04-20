package com.androidbegin.videostreamtutorial;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {
	// Put in your Video URL here
	private String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
	// Declare some variables
	private ProgressDialog pDialog;
	VideoView videoview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set the layout from video_main.xml
		setContentView(R.layout.video_main);
		// Find your VideoView in your video_main.xml layout
		videoview = (VideoView) findViewById(R.id.VideoView);
		// Execute StreamVideo AsyncTask
		new StreamVideo().execute();

	}

	// StreamVideo AsyncTask
	private class StreamVideo extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressbar
			pDialog = new ProgressDialog(MainActivity.this);
			// Set progressbar title
			pDialog.setTitle("Android Video Streaming Tutorial");
			// Set progressbar message
			pDialog.setMessage("Buffering...");
			pDialog.setIndeterminate(false);
			// Show progressbar
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {

			try {
				// Start the MediaController
				MediaController mediacontroller = new MediaController(
						MainActivity.this);
				mediacontroller.setAnchorView(videoview);
				// Get the URL from String VideoURL
				Uri video = Uri.parse(VideoURL);
				videoview.setMediaController(mediacontroller);
				videoview.setVideoURI(video);

				videoview.requestFocus();
				videoview.setOnPreparedListener(new OnPreparedListener() {
					// Close the progress bar and play the video
					public void onPrepared(MediaPlayer mp) {
						pDialog.dismiss();
						videoview.start();
					}
				});
			} catch (Exception e) {
				pDialog.dismiss();
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

		}

	}

	// Not using options menu for this tutorial
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}