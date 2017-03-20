package edu.udel.jtown.EscapeGame;

public class StudentGameRecord {
	private String player;
    private int score;
    private Long date;
    
    public StudentGameRecord(String player, int score, Long date) {
        this.player = player;
        this.score = score;
        this.date = date;
    }
    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Long getDate() {
        return date;
    }
    public void setDate(Long date) {
        this.date = date;
    }
    
    public String toString() {
        return String.format("%6s %6d %3$tm/%3$td/%3$ty", player, score, date);
    }
}
