package views;

import model.entities.RatedSeries;
import presenter.RankingPresenter;

import javax.swing.*;
import java.awt.*;

public class ScoreView extends JPanel {
    private JPanel scorePanel;
    private JList<RatedSeries> rankingList;
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
        DefaultListModel<RatedSeries> listModel = new DefaultListModel();
        rankingList.setModel(listModel);
        rankingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    public void showSeriesRanking() {
        rankingPresenter.requestSeriesRanking();
    }
    public void addSeriesToRanking(RatedSeries series) {
        DefaultListModel<RatedSeries> listModel = (DefaultListModel<RatedSeries>) rankingList.getModel();
        listModel.addElement(series);
    }
    public void clearRankingList() {
        DefaultListModel<RatedSeries> listModel = (DefaultListModel<RatedSeries>) rankingList.getModel();
        listModel.clear();
    }

}
