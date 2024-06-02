package model.entities;

import javax.swing.*;

public class Series extends JMenuItem {
    public String title;
    public String pageID;
    public String snippet;
    public int score;

    public Series(String title, String pageID, String snippet) {
        String itemText = "<html><font face=\"arial\">" + title + ": " + snippet;
        itemText =itemText.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");
        this.setText(itemText);
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
        this.score = 0;
    }
    public Series(String title, int score) {
        this.title = title;
        this.score = score;
    }
    public boolean isRated() {
        return score != 0;
    }
    public void setScore(int score) {
        this.score = score;
    }

}
