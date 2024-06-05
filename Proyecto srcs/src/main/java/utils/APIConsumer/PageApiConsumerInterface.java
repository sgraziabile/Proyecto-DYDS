package utils.APIConsumer;

import model.entities.Series;
import retrofit2.Response;

public interface PageApiConsumerInterface {
    Response<String> retrieveSeries(Series series ) throws Exception;
}
