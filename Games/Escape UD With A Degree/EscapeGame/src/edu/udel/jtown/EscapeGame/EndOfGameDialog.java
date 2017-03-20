package edu.udel.jtown.EscapeGame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

public class EndOfGameDialog implements DialogInterface.OnClickListener {
	private EscapeGameActivity activity;
	private EditText input;
	private StudentGameRecord lastRecord;
	private boolean topTen;

	public EndOfGameDialog(EscapeGameActivity activity) {
		this.activity = activity;
	}

	public void onClick(DialogInterface dialog, int whichButton) {
		if (topTen) {
			// format value to only valid letters, and only the first 7, uppercase
			String value = String.format("%7s", 
					this.input.getText().toString().replaceAll("[^a-zA-Z]", "")).substring(0,7).toUpperCase();

			lastRecord.setPlayer(value);

			// insert record to database
			activity.getDatabase().insertGameRecord(lastRecord);
		}

		activity.setAppView("Splash");
	}

	/**
	 * Gets the last initials entered.  Will return "" if none entered yet.
	 * 
	 * @return
	 */
	public String getLastInitials() {
		String lastInitials = "";
		if (this.lastRecord != null) {
			lastInitials = lastRecord.getPlayer();
		}
		return lastInitials;
	}


	public void show() {
		// pop up a dialog box telling them to enter name
		AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		int score = activity.getCurrentGame().getBulbScore();

		lastRecord = new StudentGameRecord(getLastInitials(), score, System.currentTimeMillis());

		topTen = activity.getDatabase().isTopTenScore(lastRecord); 
		if (topTen) {
			alert.setTitle("Congratulations! You have escaped with your degree");
			alert.setMessage("New top ten score of " + score + "!\nPlease enter your initials:");

			// get these before we replace the old edit text
			String lastInitials = getLastInitials();
			this.input = new EditText(activity);
			this.input.setText(lastInitials);

			// Set an the view to the EditText 
			alert.setView(this.input);
		}
		else {
			alert.setTitle("Game Over");
			alert.setMessage("Score of " + score + ".");
		}


		alert.setPositiveButton("Ok", this);
		alert.show();

	}

}
