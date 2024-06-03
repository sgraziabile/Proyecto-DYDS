package views;

import model.entities.RatedSeries;
import presenter.RankingPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScoreView extends JPanel {
    private JPanel scorePanel;
    private JList<RatedSeries> rankingList;
    private JScrollPane rankingScrollPane;
    private RankingPresenter rankingPresenter;
    private BaseView baseView;

    public ScoreView() {
        initRankingList();
       initListeners();
    }
    private void initListeners() {
    rankingList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    changeTab(0);
                    rankingPresenter.onRankingSeriesSelected();
                }
            }
        });
    }
    public void setRankingPresenter(RankingPresenter rankingPresenter) {
        this.rankingPresenter = rankingPresenter;
    }
    public void setBaseView(BaseView baseView) {
        this.baseView = baseView;
    }
    public String getSelectedSeriesTitle() {
        return rankingList.getSelectedValue().getTitle();
    }
    private void initRankingList() {
        DefaultListModel<RatedSeries> listModel = new DefaultListModel();
        rankingList.setModel(listModel);
        rankingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    private void changeTab(int tabIndex) {baseView.getOptionsPanel().setSelectedIndex(tabIndex);}
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
    public boolean isSelectionEmpty() {
        return rankingList.getSelectedValue() == null;
    }

}
