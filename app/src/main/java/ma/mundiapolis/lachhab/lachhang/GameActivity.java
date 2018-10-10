package ma.mundiapolis.lachhab.lachhang;

import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class GameActivity extends Activity {
	private AlertDialog helpAlert;
	private String[] words;
	private Random rand;
	private String currWord;
	private LinearLayout wordLayout;
	private TextView[] charViews;
	private GridView letters;
	private LetterAdapter ltrAdapt;
	private ImageView[] bodyParts;
	private int numParts=6;
	private int currPart;
	private int numChars;
	private int numCorr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_game);
	  getActionBar().setDisplayHomeAsUpEnabled(true);
	  Resources res = getResources();
	  words = res.getStringArray(R.array.words);
	  rand = new Random();
	  currWord = "";
	  wordLayout = (LinearLayout)findViewById(R.id.word);
	  letters = (GridView)findViewById(R.id.letters);
	  
	  bodyParts = new ImageView[numParts];
	  bodyParts[0] = (ImageView)findViewById(R.id.head);
	  bodyParts[1] = (ImageView)findViewById(R.id.body);
	  bodyParts[2] = (ImageView)findViewById(R.id.arm1);
	  bodyParts[3] = (ImageView)findViewById(R.id.arm2);
	  bodyParts[4] = (ImageView)findViewById(R.id.leg1);
	  bodyParts[5] = (ImageView)findViewById(R.id.leg2);
	  
	  playGame();
	}
	
	private void playGame() {
		String newWord = words[rand.nextInt(words.length)];
		while (newWord.equals(currWord))  {
			newWord = words[rand.nextInt(words.length)];
		}
		currWord = newWord;
		
		charViews = new TextView[currWord.length()];
		wordLayout.removeAllViews();
		
		for (int c = 0; c < currWord.length(); c++) {
			  charViews[c] = new TextView(this);
			  charViews[c].setText(""+currWord.charAt(c));
			  
			  charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			  charViews[c].setGravity(Gravity.CENTER);
			  charViews[c].setTextColor(Color.BLACK);
			  charViews[c].setBackgroundResource(R.drawable.letter_bg);
			  wordLayout.addView(charViews[c]);
			  
			}	
		ltrAdapt=new LetterAdapter(this);
		letters.setAdapter(ltrAdapt);
		
		currPart=0;
		numChars=currWord.length();
		numCorr=0;
		
		for(int p = 0; p < numParts; p++) {
			  bodyParts[p].setVisibility(View.INVISIBLE);
			}
	}
	
	public void letterPressed(View view) {
		String ltr=((TextView)view).getText().toString();
		char letterChar = ltr.charAt(0);
		view.setEnabled(false);
		view.setBackgroundResource(R.drawable.letter_down);
		
		boolean correct = false;
		for(int k = 0; k < currWord.length(); k++) {
			if (currWord.charAt(k)==letterChar){
				correct = true;
				numCorr++;
				charViews[k].setTextColor(Color.WHITE);
			}
		}
		
		if (correct) {
			if (numCorr == numChars) {
				disableBtns();
				 
				AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
				winBuild.setTitle("Yay, well done!");
				winBuild.setMessage("You won!\n\nThe answer was:\n\n"+currWord);
				winBuild.setPositiveButton("Play Again",
						new DialogInterface.OnClickListener() {
						  public void onClick(DialogInterface dialog, int id) {
							GameActivity.this.playGame();
				    }});
				 
				  winBuild.setNegativeButton("Back To home Page",
				    new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int id) {
				        GameActivity.this.finish();
				    }});
				 
				  winBuild.show();
				}
		} else if (currPart < numParts) {
			bodyParts[currPart].setVisibility(View.VISIBLE);
			currPart++;
		} else {
			disableBtns();
			 
			AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
			loseBuild.setTitle("Ops");
			loseBuild.setMessage("You lose!\n\nThe answer was:\n\n"+currWord);
			loseBuild.setPositiveButton("Play Again",
			  new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int id) {
			        GameActivity.this.playGame();
			    }});
			 
			  loseBuild.setNegativeButton("Exit",
			    new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int id) {
			        GameActivity.this.finish();
			    }});
			 
			  loseBuild.show();
		
		}
		}

	public void disableBtns() {
		  int numLetters = letters.getChildCount();
		  for (int l = 0; l < numLetters; l++) {
		    letters.getChildAt(l).setEnabled(false);
		  }
		}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	void hide_navigation_bar() {
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
}
