package edu.udel.jtown.EscapeGame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.udel.jatlas.gameframework.Action;
import edu.udel.jatlas.gameframework.Game;
import edu.udel.jatlas.gameframework.GameListener;
import edu.udel.jtown.gameframework.android.AndroidTicker;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import	android.widget.TextView;
import android.widget.Toast;

public class EscapeGameActivity extends Activity implements GameListener<EscapeGame> {

	private Map<String, View> appViews;
	private EscapeGame game;
	private TextView status;
	private EscapeGameView2D gameView;
	private StudentDatabase database;
	private EndOfGameDialog dialog;
	private int gameType;

	private SoundManager soundManager;
	private List<StudentLevel> levels;


	public static final int GAMETYPE_AI = 0;
	public static final int GAMETYPE_HUMAN = 1;

	public int getGameType() {
		return gameType;
	}

	public StudentDatabase getDatabase() {
		return database;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appViews = new HashMap<String, View>();

		soundManager = new SoundManager(this);
		soundManager.init();

		status = new TextView(this);
		gameView = new EscapeGameView2D(this);
		dialog = new EndOfGameDialog(this);

		database = new StudentDatabase(this);

		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(status);
		ll.addView(gameView);
		

		appViews.put("Game", ll);
		appViews.put("Splash", new SplashScreen(this));

		loadLevels();
		setAppView("Splash");
	}

	private void loadLevels() {
		try {
			String[] files = getAssets().list("levels");
			StudentLevel[] levelArray = new StudentLevel[files.length];

			for (String levelName : files) {
				// Construct a List of levels from an asset
				InputStream in = getAssets().open("levels/" + levelName);

				String withoutExtension = levelName.replaceFirst("\\..*", "");
				String justTheNumber = withoutExtension.replaceAll("\\D", "");
				int level = Integer.parseInt(justTheNumber);
				levelArray[level] = StudentLevel.loadFromStream(in, level);
			}

			levels = Arrays.asList(levelArray);
		}
		catch (IOException e) {
		}
	}



	public void setAppView(String nameOfView) {
		View view = appViews.get(nameOfView);
		setContentView(view);
		view.invalidate();
	}


	public void startGame() {
		// make game visible

		game = EscapeGame.makeDefaultStartGame();
		game.setLevels(levels);
		game.addGameListener(this);

		if (gameType == GAMETYPE_AI) {
			game.addGameListener(new EscapeAI());
		}

		game.start(new AndroidTicker());
		EscapeHuman human = new EscapeHuman(this);
		gameView.setOnTouchListener(human);
		gameView.setOnKeyListener(human);

		setAppView("Game");
	}




	public void updateViews() {
		status.setText(game.getStatus());
		gameView.invalidate();
	}

	public EscapeGame getCurrentGame() {
		return game;
	}

	@Override
	public void onPerformActionEvent(Action<EscapeGame> action, EscapeGame game) {
		updateViews();

	}

	@Override
	public void onTickEvent(EscapeGame game) {
		updateViews();

	}

	@Override
	public void onStartEvent(EscapeGame game) {
		updateViews();

	}

	@Override
	public void onEndEvent(EscapeGame game) {
		updateViews();
		if (gameType == GAMETYPE_HUMAN && !isFinishing()) {
			dialog.show();
		}
		else {
			setAppView("Splash");
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
		if (game != null) {
			game.end();
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		if (database != null) {
			database.close();
		}
	}

	@Override
	public void onEvent(String event, EscapeGame game) {
		updateViews();

		if (event.equals("gained_knowledge")) {
			soundManager.playSound("Action_Gain_Bulb");
		}
		else if (event.equals("next_level")) {
			if (game.getLevel() > 0) {
				
				Toast.makeText(this, "You are now in year " + game.getLevel() + "!", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public void restartGame() {
		if (game != null && game.getLifecycle() != Game.ENDED) {
			game.end();
		}
		startGame();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		selectMenuOption(item.getTitle());
		return true;
	}

	public void selectMenuOption(CharSequence title) {
		if (title.equals("See demonstration")) {
			gameType = GAMETYPE_AI;
			restartGame();
		}
		else if (title.equals("Start a new game")) {
			gameType = GAMETYPE_HUMAN;
			restartGame();
		}
		else if (title.equals("Restart the game")) {
			// start a new game with the same players as previous game
			restartGame();
		}
		else if (title.equals("Quit")) {
			finish();
		}
	}


}
