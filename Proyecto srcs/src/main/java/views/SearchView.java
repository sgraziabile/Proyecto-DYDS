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
    private JPopupMenu searchOptionsMenu;
    SearchPresenter searchPresenter;
    SaveSeriesPresenter saveSeriesPresenter;

    public SearchView() {
        setupSelectedSeriesPaneContentType();
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
            searchPresenter.onSeriesMenuSelect();
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
            searchPresenter.onSeriesMenuSelect();
        });
    }
    public void showSearchOptionsMenu() {
        searchOptionsMenu.show(searchButton, 0, searchButton.getHeight());
    }
    private void setupSelectedSeriesPaneContentType() {
        selectedSeriesPane.setContentType("text/html");
    }
    public void setWorkingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
        selectedSeriesPane.setEnabled(false);
    }
    public void setWaitingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
        selectedSeriesPane.setEnabled(true);
    }
}
