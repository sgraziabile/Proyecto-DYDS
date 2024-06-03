package presenter;

import model.RankingModel;
import model.ScoreModel;
import model.entities.RatedSeries;
import model.listeners.RankingModelListener;
import utils.HtmlHandler;
import views.ScoreView;

import java.util.ArrayList;

public class RankingPresenter {
    private RankingModel rankingModel;
    private ScoreView scoreView;
    private SearchPresenter searchPresenter;
    private Thread taskThread;
    private HtmlHandler htmlHandler;

    public RankingPresenter(RankingModel rankingModel) {
        this.rankingModel = rankingModel;
        this.htmlHandler = new HtmlHandler();
        initListeners();
    }
    private void initListeners() {
        rankingModel.setListener(new RankingModelListener() {
            @Override
            public void rankingHasChanged() {
                showUpdatedRanking();
            }
        });
    }
    public void requestSeriesRanking() {
        taskThread = new Thread(() -> {
            rankingModel.updateRanking();
        });
        taskThread.start();
    }
    public void setRankingModel(RankingModel rankingModel) {
        this.rankingModel = rankingModel;
    }
    public void setSearchPresenter(SearchPresenter searchPresenter) {this.searchPresenter = searchPresenter;}
    public void setScoreView(ScoreView scoreView) {
        this.scoreView = scoreView;
    }
    private void showUpdatedRanking() {
        scoreView.clearRankingList();
        ArrayList<RatedSeries> rankingList = rankingModel.getLastUpdatedRanking();
        for (RatedSeries series : rankingList) {
            scoreView.addSeriesToRanking(series);
        }
    }
    public void onRankingSeriesSelected() {
        String selectedSeriesTitle = "";
        if(!scoreView.isSelectionEmpty()) {
            selectedSeriesTitle = scoreView.getSelectedSeriesTitle();
            searchPresenter.searchSeriesFromRanking(selectedSeriesTitle);
        }
    }

}
