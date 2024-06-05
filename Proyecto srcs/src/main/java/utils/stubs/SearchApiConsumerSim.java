package utils.stubs;

import retrofit2.Response;
import utils.APIConsumer.SearchApiConsumerInterface;

public class SearchApiConsumerSim implements SearchApiConsumerInterface {

    public Response<String> searchTerm(String termToSearch, int limit) throws Exception {
        String simulatedResponse = simulateResponse(termToSearch,limit);
        return Response.success(simulatedResponse);
    }
    private String simulateResponse(String termToSearch, int limit) {
        String simulatedResponse = "";
        if(termToSearch == "") {
            simulatedResponse = "Default search for empty term with limit: " + limit;
        }
        else {
            simulatedResponse = "Simulated response for term: " + termToSearch + " with limit: " + limit;
        }
        return simulatedResponse;
    }
}
