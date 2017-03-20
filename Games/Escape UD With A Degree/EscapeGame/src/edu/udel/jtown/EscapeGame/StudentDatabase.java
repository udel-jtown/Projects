package edu.udel.jtown.EscapeGame;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentDatabase {
	public static final String DATABASE_NAME = "Student";
	public static final String TABLE_NAME = "Game_Records";

	private SQLiteDatabase database;

	/**
	 * Creates a database for the Snake application. Creates/opens a connection
	 * to the underlying Android database.
	 * 
	 * @param context
	 */
	public StudentDatabase(Context context) {
		this.database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		createGameRecordsTable();
		try {
			selectTopGameRecords(1);
		}
		catch (Exception e) {
			// this will only happen if the table was created incorrectly in some previous run
			// or if one of the columns was changed.  To prevent this from crashing our program,
			// we will delete all records now
			System.err.println("SnakeDatabase was in an invalid state so the data has been cleared");
			deleteGameRecords();
			createGameRecordsTable();
		}

	}

	public void close() {
		if (this.database != null) {
			this.database.close();
		}
	}

	/**
	 * Creates a table in the database if it does not exist already.
	 */
	private void createGameRecordsTable() {
		database.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME
				+ " (Name TEXT, "
				+ " Score INTEGER, Date INTEGER);");
	}

	/**
	 * Resets the database table to empty by deleting all rows.
	 */
	public void deleteGameRecords() {
		database.execSQL("DROP TABLE " + TABLE_NAME);
		createGameRecordsTable();
	}

	/**
	 * Inserts a single record (row) into the database table.
	 * 
	 * @param record
	 */
	public void insertGameRecord(StudentGameRecord record) {
		database.execSQL("INSERT INTO "
				+ TABLE_NAME
				+ " (Name, Score, Date)"
				+ " VALUES ('" + record.getPlayer() 
				+ "', " + record.getScore()
				+ ", " + record.getDate() + ");");       
	}

	/**
	 * Gets the current top scores by querying the table for high scores ordered
	 * by the score (descending) and date (ascending) so that the first person to
	 * score a specific high score appears first.  Returns only the first
	 * number of these.
	 * 
	 * @return
	 */
	public List<StudentGameRecord> selectTopGameRecords(int number) {
		Cursor c = database.rawQuery("SELECT Name, Score, Date" +
				" FROM " + TABLE_NAME 
				+ " ORDER BY Score DESC, Date ASC"
				+ " LIMIT 0, " + number,
				null);
		/* Get the indices of the Columns we will need */
		int nameColumn = c.getColumnIndex("Name");
		int scoreColumn = c.getColumnIndex("Score");
		int dateColumn = c.getColumnIndex("Date");

		List<StudentGameRecord> highScores = new ArrayList<StudentGameRecord>(number);
		if (c.moveToFirst()) {
			//            while (true) {

			//            }
			do {
				StudentGameRecord record = new StudentGameRecord(
						c.getString(nameColumn),
						c.getInt(scoreColumn),
						c.getLong(dateColumn));
				highScores.add(record);
				//                if (!c.moveToNext()) {
				//                    break;
				//                }
			}
			while (c.moveToNext());
		}

		c.close();

		return highScores;
	}

	public boolean isTopTenScore(StudentGameRecord record) {
		List<StudentGameRecord> top = selectTopGameRecords(10);
		return top.size() < 10 || top.get(top.size()-1).getScore() < record.getScore();
	}
}
