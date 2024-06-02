package views;

import model.entities.Series;
import presenter.SaveSeriesPresenter;
import presenter.ScorePresenter;
import presenter.SearchPresenter;

import javax.swing.*;
import java.awt.*;

public class SearchView extends JPanel implements View {

    private JTextField searchTextField;
    private JButton searchButton;
    private JTextPane selectedSeriesPane;
    private JButton saveLocallyButton;
    private JPanel searchPanel;
    private JComboBox scoreComboBox;
    private JButton updateScoreButton;
    private JPopupMenu searchOptionsMenu;
    private SearchPresenter searchPresenter;
    private SaveSeriesPresenter saveSeriesPresenter;
    private ScorePresenter scorePresenter;

    public SearchView() {
        showView();
        initScoreSettings();
        initListeners();
    }
    public void showView() {
        setupSelectedSeriesPaneContentType();
    }
    private void initListeners() {
        searchButton.addActionListener(actionEvent -> {
            searchPresenter.onSearchButtonClicked();
        });
        saveLocallyButton.addActionListener(actionEvent -> {
            saveSeriesPresenter.onSavedLocallyButtonClicked();
        });
        updateScoreButton.addActionListener(actionEvent -> {
            scorePresenter.onUpdateScoreButtonClicked();
        });
    }
    private void initScoreSettings(){
        Integer scores[] = {1,2,3,4,5,6,7,8,9,10};
        scoreComboBox.setModel(new DefaultComboBoxModel(scores));
        scoreComboBox.setEnabled(false);
        updateScoreButton.setEnabled(false);
    }
    public JPanel getContent() {
        return this.getContent();
    }
    public void setSearchPresenter(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }
    public void setSaveSeriesPresenter(SaveSeriesPresenter saveSeriesPresenter) {this.saveSeriesPresenter = saveSeriesPresenter;}
    public void setScorePresenter(ScorePresenter scorePresenter) {this.scorePresenter = scorePresenter;}
    public String getSearchFieldText() {
        return searchTextField.getText();
    }
    public void setSearchOptionsMenu(JPopupMenu searchOptionsMenu) {
        this.searchOptionsMenu = searchOptionsMenu;
    }
    public void initSearchOptionListener(Series series) {
        series.addActionListener(actionEvent -> {
            searchPresenter.onSeriesMenuSelect(series);
        });
    }
    public void showSelectedSeries(String seriesExtract) {
        selectedSeriesPane.setText(seriesExtract);
        selectedSeriesPane.setCaretPosition(0);
    }
    public void showSearchOptionsMenu() {
        searchOptionsMenu.show(searchButton, 0, searchButton.getHeight());
    }
    public String getSelectedSeriesPaneText() {
        return selectedSeriesPane.getText();
    }
    public String getScore() { return scoreComboBox.getSelectedItem().toString();}
    private void setupSelectedSeriesPaneContentType() {
        selectedSeriesPane.setContentType("text/html");
        selectedSeriesPane.setEditable(false);
    }

    public void setWorkingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
        selectedSeriesPane.setEnabled(false);
    }
    public void setWaitingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
        scoreComboBox.setEnabled(false);
        updateScoreButton.setEnabled(false);
        selectedSeriesPane.setEnabled(true);
    }
    public void allowScoreUpdate() {
        scoreComboBox.setEnabled(true);
        updateScoreButton.setEnabled(true);
    }
    public void clearSelectedSeriesPane() {
        selectedSeriesPane.setText("");
    }
    public void clearSearchField() {
        searchTextField.setText("");
    }
}
