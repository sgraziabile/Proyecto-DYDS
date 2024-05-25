package views;

import dyds.tvseriesinfo.fulllogic.DataBase;
import presenter.StoragePresenter;

import javax.swing.*;
import java.lang.reflect.Array;

public class StorageView implements View {

    private JPanel storagePanel;
    private JTextPane savedSeriesPane;
    private JComboBox savedShowsComboBox;
    private StoragePresenter storagePresenter;

    public StorageView(StoragePresenter storagePresenter) {
        this.storagePresenter = storagePresenter;
        showSavedSeries();
        setupSavedSeriesPaneContentType();
        initListeners();
    }
    public void showView() {
        setupSavedSeriesPaneContentType();
    }

    public JPanel getContent() {
        return storagePanel;
    }
    private void initListeners() {

    }
    private void showSavedSeries() {
        Object[] savedSeries = storagePresenter.getSavedSeries();
        savedShowsComboBox.setModel(new DefaultComboBoxModel(savedSeries));
    }
    private Object[] getSavedSeries() {
        return storagePresenter.getSavedSeries();
    }
    private void setupSavedSeriesPaneContentType() {
        savedSeriesPane.setContentType("text/html");
    }
}
