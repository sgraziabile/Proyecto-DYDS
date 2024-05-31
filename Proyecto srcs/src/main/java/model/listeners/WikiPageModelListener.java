package model.listeners;

import model.entities.SearchResult;

public interface WikiPageModelListener {
    void seriesRetrieved(SearchResult searchResult);
}
