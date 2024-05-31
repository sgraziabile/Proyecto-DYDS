package views;

import presenter.StoragePresenter;

import javax.swing.*;

public class StorageView extends JPanel implements View {

    private JComboBox savedShowsComboBox;
    private JPanel storagePanel;
    private JTextPane savedSeriesPane;
    private StoragePresenter storagePresenter;

    public StorageView() {
        showView();
        initListeners();
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
    public void requestSavedSeries() {
        storagePresenter.getSavedSeries();
    }
    public void showSavedSeries(Object[] savedSeries) {
        savedShowsComboBox.setModel(new DefaultComboBoxModel(savedSeries));
    }
    public void showSavedSeriesContent(String seriesContent) {
        savedSeriesPane.setText(seriesContent);
    }
    public Object getSelectedSavedSeries() {
        return savedShowsComboBox.getSelectedItem();
    }
    private void setupSavedSeriesPaneContentType() {
        savedSeriesPane.setContentType("text/html");
    }

}
