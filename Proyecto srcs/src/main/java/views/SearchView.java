package views;

import model.entities.SearchResult;
import presenter.SaveSeriesPresenter;
import presenter.SearchPresenter;

import javax.swing.*;
import java.awt.*;

public class SearchView extends JPanel implements View {

    private JTextField searchTextField;
    private JButton searchButton;
    private JTextPane selectedSeriesPane;
    private JButton saveLocallyButton;
    private JPanel searchPanel;
    private JCheckBox scoreBox;
    private JComboBox scoreComboBox;
    private JPopupMenu searchOptionsMenu;
    private SearchPresenter searchPresenter;
    private SaveSeriesPresenter saveSeriesPresenter;

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
    }
    private void initScoreSettings(){
        Integer scores[] = {1,2,3,4,5,6,7,8,9,10};
        scoreComboBox.setModel(new DefaultComboBoxModel(scores));
        scoreComboBox.setEnabled(false);
        scoreBox.addActionListener(actionEvent -> {
            scoreComboBox.setEnabled(scoreBox.isSelected());
        });
    }
    public JPanel getContent() {
        return this.getContent();
    }
    public void setSearchPresenter(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }
    public void setSaveSeriesPresenter(SaveSeriesPresenter saveSeriesPresenter) {
        this.saveSeriesPresenter = saveSeriesPresenter;
    }
    public String getSearchFieldText() {
        return searchTextField.getText();
    }
    public void setSearchOptionsMenu(JPopupMenu searchOptionsMenu) {
        this.searchOptionsMenu = searchOptionsMenu;
    }
    public void initSearchOptionListener(SearchResult searchResult) {
        searchResult.addActionListener(actionEvent -> {
            searchPresenter.onSeriesMenuSelect(searchResult);
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
        selectedSeriesPane.setEnabled(true);
    }
    public void clearSelectedSeriesPane() {
        selectedSeriesPane.setText("");
    }
    public void clearSearchField() {
        searchTextField.setText("");
    }
}
