package presenter;

import model.ScoreModel;
import model.listeners.ScoreModelListener;
import utils.HtmlHandler;
import views.SearchView;

public class ScorePresenter implements Presenter{
    private ScoreModel scoreModel;
    private SearchView searchView;
    private RankingPresenter rankingPresenter;
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
    public void setScoreModel(ScoreModel scoreModel) {
        this.scoreModel = scoreModel;
    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
    public void setRankingPresenter(RankingPresenter rankingPresenter) {
        this.rankingPresenter = rankingPresenter;
    }
    private void showUpdatedRanking() {
        //llamar al rankingPresenter para que actualice la vista
    }
}
