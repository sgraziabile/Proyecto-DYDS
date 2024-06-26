package presenter;

import model.ScoreModel;
import model.listeners.ScoreModelListener;
import utils.HtmlHandler;
import views.SearchView;

public class ScorePresenter implements Presenter{
    private ScoreModel scoreModel;
    private SearchView searchView;
    private RankingPresenter rankingPresenter;
    private SearchPresenter searchPresenter;
    private Thread taskThread;
    private HtmlHandler htmlHandler;

    public ScorePresenter(ScoreModel scoreModel) {
        this.scoreModel = scoreModel;
        this.htmlHandler = new HtmlHandler();
        initListeners();
    }
    private void initListeners() {
        scoreModel.setListener(new ScoreModelListener() {
            @Override
            public void scoreHasChanged() {
                showUpdatedRanking();
            }
        });
    }
    public void setScoreModel(ScoreModel scoreModel) {this.scoreModel = scoreModel;}
    public void setSearchView(SearchView searchView) {this.searchView = searchView;}
    public void setRankingPresenter(RankingPresenter rankingPresenter) {this.rankingPresenter = rankingPresenter;}
    public void setSearchPresenter(SearchPresenter searchPresenter) {this.searchPresenter = searchPresenter;}
    public void onUpdateScoreButtonClicked() {
        searchView.setWaitingStatus();
        requestUpdateScore();
    }
    private void requestUpdateScore(){
        taskThread = new Thread(() -> {
            String seriesTitle = searchPresenter.getLastSeriesTitle();
            String seriesScore = searchView.getScore();
            try {
                scoreModel.updateSeriesScore(seriesTitle, seriesScore);
            } catch (Exception e) {
                searchView.showEventNotifier("Error updating score");
            }
        });
        taskThread.start();
    }
    private void showUpdatedRanking() {
        searchView.showEventNotifier("Score updated successfully");
        rankingPresenter.requestSeriesRanking();
    }
}
