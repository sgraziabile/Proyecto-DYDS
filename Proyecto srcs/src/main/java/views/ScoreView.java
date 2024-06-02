package views;

import presenter.RankingPresenter;

import javax.swing.*;
import java.awt.*;

public class ScoreView extends JPanel {
    private JPanel scorePanel;
    private JList<String> rankingList;
    private JScrollPane rankingScrollPane;
    private RankingPresenter rankingPresenter;

    public ScoreView() {
        initRankingList();
       initListeners();
    }
    private void initListeners() {

    }
    public void setRankingPresenter(RankingPresenter rankingPresenter) {
        this.rankingPresenter = rankingPresenter;
    }

    public void setRankingComboBox(String ranking) {

    }
    private void initRankingList() {
        DefaultListModel<String> listModel = new DefaultListModel();
        rankingList.setModel(listModel);
        rankingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    public void showSeriesRanking() {
        rankingPresenter.requestSeriesRanking();
    }
    public void addSeriesToRanking(String series) {
        DefaultListModel<String> listModel = (DefaultListModel<String>) rankingList.getModel();
        listModel.addElement(series);
    }

}
