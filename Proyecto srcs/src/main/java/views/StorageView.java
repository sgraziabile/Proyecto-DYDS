package views;

import javax.swing.*;

public class StorageView extends JPanel implements View {

    private JComboBox savedShowsComboBox;
    private JPanel storagePanel;
    private JTextPane savedSeriesPane;

    public StorageView() {
        showView();
    }
    public void showView() {
        setupSavedSeriesPaneContentType();
        initListeners();
    }
    public JPanel getContent() {
        return this.getContent();
    }
    private void initListeners() {

    }
    private void setupSavedSeriesPaneContentType() {
        savedSeriesPane.setContentType("text/html");
    }
}
