package com.example.assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.Pair;

public final class Utilities {
    private Utilities() {
        
    }
    
    // Origional list will be changed
    public static ArrayList<Pair<String, Float>> mergeAllDuplicatedPayments(ArrayList<Pair<String, Float>> allPayments) {
        sortList(allPayments);


        String previousItem = null;
        ArrayList<Pair<String, Float>> mergedPayments = new ArrayList<Pair<String,Float>>();
        int pos = -1;
        for (Pair<String, Float> item : allPayments) {
            if (item.first.equals(previousItem)) {
                // This is the same name as the previous guy
                mergedPayments.set(pos, combinePairs(mergedPayments.get(pos), item));
            } else {
                mergedPayments.add(item);
                previousItem = item.first;
                ++pos;
            }
        }
        
        return mergedPayments;
    }

    private static Pair<String, Float> combinePairs(Pair<String, Float> item1, Pair<String, Float> item2) {
        return new Pair<String, Float>(item1.first, item1.second + item2.second);
    }

    private static void sortList(ArrayList<Pair<String, Float>> allPayments) {
        // Will sort them based on the strings. This way, can scroll through
        // very easily
        Collections.sort(allPayments, new Comparator<Pair<String, Float>>() {
            @Override
            public int compare(Pair<String, Float> arg0, Pair<String, Float> arg1) {
                return arg0.first.compareTo(arg1.first);
            }
        });
    }
}
