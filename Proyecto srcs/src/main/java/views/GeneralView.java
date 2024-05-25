package views;

import dyds.tvseriesinfo.fulllogic.MainWindow;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GeneralView implements View{
    private ArrayList<View> viewsList;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel searchPanel;
    private JPanel storagePanel;

    public GeneralView() {

    }
    public void showView() {
        JFrame mainFrame = new JFrame("TV Series Info Repo");
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public JPanel getContent() {
        return mainPanel;
    }

}
