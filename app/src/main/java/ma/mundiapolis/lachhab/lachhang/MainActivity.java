package ma.mundiapolis.lachhab.lachhang;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
	private AlertDialog helpAlert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hangman);
		Button playButton = (Button) findViewById(R.id.playBtn);
		playButton.setOnClickListener(this);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;

		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.playBtn:
				Intent playIntent = new Intent(this, GameActivity.class);
				this.startActivity(playIntent);
				break;
			

		}

	}

	public void help (View v) {
		AlertDialog.Builder helpBuild = new AlertDialog.Builder(this);

		helpBuild.setTitle("Help");
		helpBuild.setMessage("Guess the word by selecting the letters.\n\n"
				+ "You only have 6 wrong selections then it's game over!");
		helpBuild.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						helpAlert.dismiss();
					}
				});
		helpAlert = helpBuild.create();

		helpBuild.show();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	void hide_navigation_bar() {
		// fullscreen mode
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			hide_navigation_bar();


		}


	}
    public void clickexit(View v)
    {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);}


}