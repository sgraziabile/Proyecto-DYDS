package views;

import presenter.SaveSeriesPresenter;
import presenter.SearchPresenter;

import javax.swing.*;
import java.awt.*;

public class SearchView implements View {

    private JPanel searchPanel;
    private JButton searchButton;
    private JButton saveLocallyButton;
    private JTextPane selectedSeriesPane;
    private JTextField searchTextField;
    private SearchPresenter searchPresenter;
    private SaveSeriesPresenter saveSeriesPresenter;

    public SearchView(SearchPresenter searchPresenter, SaveSeriesPresenter saveSeriesPresenter) {
        this.searchPresenter = searchPresenter;
        this.saveSeriesPresenter = saveSeriesPresenter;
        initListeners();
    }
    public void showView() {
        setupSelectedSeriesPaneContentType();
    }

    public JPanel getContent() {
        return searchPanel;
    }
    private void initListeners() {

    }
    private void setupSelectedSeriesPaneContentType(){
        selectedSeriesPane.setContentType("text/html");
    }
    private void setWorkingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
        selectedSeriesPane.setEnabled(false);
    }
    private void setWatingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
        selectedSeriesPane.setEnabled(true);
    }



}
