/*
 * Copyright 2015 Morgan Redshaw
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.example.assignment1;

import java.util.ArrayList;
import java.util.List;

// This class is from https://eclass.srv.ualberta.ca/pluginfile.php/1889146/mod_resource/content/2/03.1-Android-MVC.4up.pdf
// Feb 1 2015
@SuppressWarnings("rawtypes")
public class FModel<V extends FView> {
    private transient List<V> views;

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
