package presenter;

import model.RankingModel;
import model.ScoreModel;
import model.SearchRankingModel;
import model.entities.RatedSeries;
import model.listeners.RankingModelListener;
import model.listeners.SearchRankingModelListener;
import utils.HtmlHandler;
import views.ScoreView;

import java.sql.SQLException;
import java.util.ArrayList;

public class RankingPresenter {
    private RankingModel rankingModel;
    private SearchRankingModel searchRankingModel;
    private ScoreView scoreView;
    private SearchPresenter searchPresenter;
    private Thread taskThread;
    private HtmlHandler htmlHandler;
    private String lastSelectedSeriesTitle;

    public RankingPresenter(RankingModel rankingModel, SearchRankingModel searchRankingModel){
        this.rankingModel = rankingModel;
        this.searchRankingModel = searchRankingModel;
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
        searchRankingModel.setListener(new SearchRankingModelListener() {
            @Override
            public void searchFromRankingHasFinished(){
                showSearchResultFromRanking();
            }
        });
    }
    public void requestSeriesRanking() {
        taskThread = new Thread(() -> {
            try {
            rankingModel.updateRanking();
            } catch (SQLException e) {
                scoreView.showEventNotifier("No results could be found for term");
            }
        });
        taskThread.start();
    }
    public void setRankingModel(RankingModel rankingModel) {
        this.rankingModel = rankingModel;
    }
    public void setSearchRankingModel(SearchRankingModel searchRankingModel) {this.searchRankingModel = searchRankingModel;}
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
        try {
            if (!scoreView.isSelectionEmpty()) {
                lastSelectedSeriesTitle = scoreView.getSelectedSeriesTitle();
                searchRankingModel.searchTerm(lastSelectedSeriesTitle, 5);
            }
        }catch(Exception e) {
            searchPresenter.notifySearchError();
        }
    }
    private void showSearchResultFromRanking(){
        searchPresenter.findRankingSeriesID(lastSelectedSeriesTitle, searchRankingModel.getLastSearchResult());
    }

}
