package views;

import presenter.RankingPresenter;

import javax.swing.*;

public class ScoreView extends JPanel {
    private JPanel scorePanel;
    private JComboBox seriesRankingComboBox;
    private RankingPresenter rankingPresenter;

    public ScoreView() {
       initListeners();
    }
    private void initListeners() {
        seriesRankingComboBox.addActionListener(actionEvent -> {
            System.out.println("RankingComboBox clicked");
            rankingPresenter.onSeriesRankingComboBoxClicked();
        });
    }
    public void setRankingPresenter(RankingPresenter rankingPresenter) {
        this.rankingPresenter = rankingPresenter;
    }

    public void setRankingComboBox(String ranking) {
        seriesRankingComboBox.removeAllItems();
        seriesRankingComboBox.addItem(ranking);
    }
}
