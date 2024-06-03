package model.entities;

import javax.swing.*;

public class Series extends JMenuItem {
    private String title;
    private String pageID;
    private String snippet;
    private int score;

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
    public String getTitle() {
        return title;
    }
    public String getPageID() {
        return pageID;
    }
    public String getSnippet() {
        return snippet;
    }
    public boolean isRated() {
        return score != 0;
    }
    public void setScore(int score) {
        this.score = score;
    }

}
