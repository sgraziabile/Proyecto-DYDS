package utils.APIConsumer;

import retrofit2.Response;

public interface SearchApiConsumerInterface {
    Response<String> searchTerm(String termToSearch, int limit) throws Exception;
}
