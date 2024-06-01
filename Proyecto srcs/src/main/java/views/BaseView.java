package views;

import javax.swing.*;

public class BaseView implements View {

    private JTabbedPane optionsPanel;
    private JPanel searchPanel;
    private JPanel storagePanel;
    private JPanel mainPanel;
    private StorageView storageView;
    private SearchView searchView;
    private ScoreView scoreView;
    private JPanel scorePanel;

    public BaseView() {

    }
    public void showView() {
        JFrame mainFrame = new JFrame("TV Series Info Repo");
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setSize(400, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public JPanel getContent() {
        return this.getContent();
    }

    public void setStorageView(StorageView storageView) {
        this.storageView = storageView;
    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
    public void setScoreView(ScoreView scoreView) { this.scoreView = scoreView; }
    public SearchView getSearchView() {
        return searchView;
    }
    public StorageView getStorageView() {
        return storageView;
    }
    public ScoreView getScoreView() { return scoreView; }
}
