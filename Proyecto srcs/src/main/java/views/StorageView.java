package views;

import presenter.StoragePresenter;

import javax.swing.*;

public class StorageView extends JPanel implements View {

    private JComboBox savedShowsComboBox;
    private JPanel storagePanel;
    private JTextPane savedSeriesPane;
    private StoragePresenter storagePresenter;

    public StorageView(StoragePresenter storagePresenter) {
        this.storagePresenter = storagePresenter;
        //showSavedSeries();
        showView();
        initListeners();
        System.out.println("StorageView created");
    }
    public StorageView() {
        showView();
    }

    public void showView() {
        setupSavedSeriesPaneContentType();
    }
    public void setStoragePresenter(StoragePresenter storagePresenter) {
        this.storagePresenter = storagePresenter;
    }
    public JPanel getContent() {
        return this.getContent();
    }
    private void initListeners() {
        savedShowsComboBox.addActionListener(actionEvent -> {
            storagePresenter.onSavedSeriesSelected();
        });
    }
    public void showSavedSeries() {
        Object[] savedSeries = storagePresenter.getSavedSeries();
        savedShowsComboBox.setModel(new DefaultComboBoxModel(savedSeries));
    }
    private void setupSavedSeriesPaneContentType() {
        savedSeriesPane.setContentType("text/html");
    }

}
