package model.entities;

import utils.HtmlHandler;

public class RatedSeries {
    private String title;
    private int rating;
    private String lastModifiedDate;
    private int id;
    private HtmlHandler htmlHandler = new HtmlHandler();

    public RatedSeries(String title, int rating, String lastModifiedDate) {
        this.title = title;
        this.rating = rating;
        this.lastModifiedDate = lastModifiedDate;
    }
    public String getTitle() {return title;}
    public int getRating() {return rating;}
    public String getLastModifiedDate() {return lastModifiedDate;}
    public String toString() {
        String seriesString = "<h1 style=\"font-size: 16;\">" + title + " " + rating + "</h1>"
                + "<p style=\"font-size: 16;\">" + lastModifiedDate + "</p>";

        return htmlHandler.formatToHtml(seriesString);
    }
}
