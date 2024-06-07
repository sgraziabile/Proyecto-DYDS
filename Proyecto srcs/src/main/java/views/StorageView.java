package views;

import presenter.StoragePresenter;

import javax.swing.*;

public class StorageView extends JPanel implements View {

    private JComboBox savedShowsComboBox;
    private JPanel storagePanel;
    private JTextPane savedSeriesPane;
    private StoragePresenter storagePresenter;
    private JPopupMenu storageOptions;
    private JOptionPane eventNotifier;
    private JMenuItem deleteSeries;
    private JMenuItem saveChanges;

    public StorageView() {
        showView();
        setOptionsMenu();
        initListeners();
    }
    private void setOptionsMenu() {
        storageOptions = new JPopupMenu();
        deleteSeries = new JMenuItem("Delete series");
        saveChanges = new JMenuItem("Save changes!");
        storageOptions.add(deleteSeries);
        storageOptions.add(saveChanges);
        savedSeriesPane.setComponentPopupMenu(storageOptions);
    }
    private void showView() {
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
        deleteSeries.addActionListener(actionEvent -> {
            storagePresenter.onDeleteClick();
        });
        saveChanges.addActionListener(actionEvent -> {
            storagePresenter.onSaveChangesClick();
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
    public String getSelectedSavedSeriesTitle() {
        return savedShowsComboBox.getSelectedItem().toString();
    }
    private void setupSavedSeriesPaneContentType() {
        savedSeriesPane.setContentType("text/html");
    }
    public Boolean isSelectedOption() {
        return savedShowsComboBox.getSelectedIndex() > -1;
    }
    public String getSelectedSeriesContent() {
        return savedSeriesPane.getText();
    }
    public void setSelectedSeriesContent(String seriesContent) {
        savedSeriesPane.setText(seriesContent);
    }
    public void setSelectedOption(int index) {
        savedShowsComboBox.setSelectedIndex(index);
    }
    public void clearSavedSeriesContent() {
        savedSeriesPane.setText("");
    }
    public void showEventNotifier(String message) {
        eventNotifier.showMessageDialog(this, message);
    }

}
