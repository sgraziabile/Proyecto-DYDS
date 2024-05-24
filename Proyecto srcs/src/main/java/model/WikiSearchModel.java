package model;

import java.util.ArrayList;

public class WikiSearchModel {
    private ArrayList<ModelListener> listeners = new ArrayList<>();
    //aca iria las clases de las API

    public void addListener(ModelListener listener) {
        this.listeners.add(listener);
    }
}
