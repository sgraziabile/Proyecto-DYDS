package presenter;

import model.RankingModel;
import model.ScoreModel;
import model.listeners.RankingModelListener;
import utils.HtmlHandler;
import views.ScoreView;

public class RankingPresenter {
    private RankingModel rankingModel;
    private ScoreView scoreView;
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
                //showUpdatedRanking();
            }
        });
    }
    public void setRankingModel(RankingModel rankingModel) {
        this.rankingModel = rankingModel;
    }
    public void setScoreView(ScoreView scoreView) {
        this.scoreView = scoreView;
    }
    private void showUpdatedRanking() {
        //
    }

}
