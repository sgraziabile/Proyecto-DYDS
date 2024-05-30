package views;

import javax.swing.*;

public class BaseView implements View {

    private JTabbedPane optionsPanel;
    private JPanel searchPanel;
    private JPanel storagePanel;
    private JPanel mainPanel;
    private StorageView storageView1;
    private SearchView searchView;
    private StorageView storageView;

    public BaseView() {
    }
    public void showView() {
        JFrame mainFrame = new JFrame("TV Series Info Repo");
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public JPanel getContent() {
        return this.getContent();
    }

    public void setData(SearchView data) {
    }

    public void getData(SearchView data) {
    }

    public boolean isModified(SearchView data) {
        return false;
    }

    public void setData(StorageView data) {
    }

    public void getData(StorageView data) {
    }
    public void setStorageView(StorageView storageView) {
        this.storageView = storageView;
    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }


}
