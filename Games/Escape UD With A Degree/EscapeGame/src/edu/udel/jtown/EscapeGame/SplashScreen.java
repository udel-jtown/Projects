package edu.udel.jtown.EscapeGame;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreen extends LinearLayout implements View.OnClickListener, OnItemClickListener {

	private EscapeGameActivity activity;

	private ImageView imageView;
	private Bitmap splashImage;
	private TextView scores;
	private Button button;

	private PopupMenu selectOptions;


	public SplashScreen(EscapeGameActivity activity) {
		super(activity);
		this.activity = activity;
		this.imageView = new ImageView(activity);
		this.scores = new TextView(activity);
		this.button = new Button(activity);

		splashImage = BitmapFactory.decodeResource(activity.getResources(), 
				activity.getResources().getIdentifier("splash", "drawable", getClass().getPackage().getName()));

		init();
	}

	private void init() {
		setOrientation(LinearLayout.VERTICAL);

		imageView.setImageBitmap(splashImage);
		imageView.setAdjustViewBounds(true);

		selectOptions = new PopupMenu(activity, "Escape UD With A Degree",
				new String[]{"Start a new game", "See demonstration", "Restart the game", "Quit"},
				this, 32);

		scores.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
		scores.setGravity(Gravity.TOP | Gravity.LEFT); // set it to the top left using bitwise OR

		button.setText("Are you ready to get schooled?");
		button.setTypeface(Typeface.SANS_SERIF);
		button.setGravity(Gravity.CENTER);
		button.setBackgroundColor(Color.BLUE);
		button.setTextColor(Color.WHITE);
		button.setTextSize(30);
		button.setOnClickListener(this);

		// these parameters allow the scores view to fill all remaining space after
		// all components are laid out
		imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		scores.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		button.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		addView(imageView);
		addView(scores);
		addView(button);
	}

	public void invalidate() {
		if (activity != null) {
			// fill table with current highscores
			StringBuilder text = new StringBuilder();
			for (StudentGameRecord record : activity.getDatabase().selectTopGameRecords(10)) {
				text.append(record.toString());
				text.append('\n'); // end of line between each
			}
			scores.setText(text);
		}
		super.invalidate();
	}


	@Override
	public void onClick(View v) {
		if (v == button) {
			selectOptions.show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		activity.selectMenuOption(((TextView)view).getText());
	}


}
