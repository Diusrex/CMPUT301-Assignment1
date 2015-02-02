package com.example.assignment1;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class FModel<V extends FView> {
    private transient ArrayList<V> views;

    public FModel() {
        views = new ArrayList<V>();
    }

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    @SuppressWarnings("unchecked")
    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }

}
