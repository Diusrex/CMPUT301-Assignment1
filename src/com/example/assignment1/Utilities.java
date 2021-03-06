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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.util.Pair;

public final class Utilities {
    private static final float epsilon = 0.001f;

    private Utilities() {

    }

    public static boolean calLessThan(Calendar n1, Calendar n2) {
        return n1.getTimeInMillis() < n2.getTimeInMillis();
    }

    // Original list will be changed
    public static List<Pair<String, Float>> mergeAllDuplicatedPayments(List<Pair<String, Float>> allPayments) {
        sortList(allPayments);

        String previousItem = null;
        List<Pair<String, Float>> mergedPayments = new ArrayList<Pair<String, Float>>();
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

    private static void sortList(List<Pair<String, Float>> allPayments) {
        // Will sort them based on the strings. This way, can scroll through
        // very easily
        Collections.sort(allPayments, new Comparator<Pair<String, Float>>() {
            @Override
            public int compare(Pair<String, Float> arg0, Pair<String, Float> arg1) {
                return arg0.first.compareTo(arg1.first);
            }
        });
    }

    public static String getFormattedDateString(Context context, int stringId, Calendar date) {
        String dateOutput = DateFormat.getDateInstance().format(date.getTime());
        return context.getResources().getString(stringId, dateOutput);
    }
    
    public static String getFormattedDateString(String str, Calendar date) {
        String dateValue = DateFormat.getDateInstance().format(date.getTime());
        return String.format(str, dateValue);
    }

    public static boolean floatsAreDifferent(float f1, float f2) {
        return (Math.abs(f1 - f2) > epsilon);
    }
}
