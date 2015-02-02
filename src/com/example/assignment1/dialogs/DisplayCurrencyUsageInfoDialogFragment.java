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
package com.example.assignment1.dialogs;

import java.util.List;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.assignment1.R;

public class DisplayCurrencyUsageInfoDialogFragment extends DialogFragment {
    // Will be two different Lists. The element at i in one is related to the
    // element at i in the other.
    // There will not be duplicate currencies in
    private static final String ALL_CURRENCIES = "AllCurrencies";
    private static final String ALL_AMOUNTS = "AllAmounts";

    // Will not merge the given payments at all
    public static DisplayCurrencyUsageInfoDialogFragment newInstance(List<Pair<String, Float>> mergedPayments) {
        DisplayCurrencyUsageInfoDialogFragment f = new DisplayCurrencyUsageInfoDialogFragment();

        Bundle args = new Bundle();

        String[] currencyNames = new String[mergedPayments.size()];
        float[] currencyValues = new float[mergedPayments.size()];

        for (int i = 0; i < mergedPayments.size(); ++i) {
            currencyNames[i] = mergedPayments.get(i).first;
            currencyValues[i] = mergedPayments.get(i).second;
        }

        args.putStringArray(ALL_CURRENCIES, currencyNames);
        args.putFloatArray(ALL_AMOUNTS, currencyValues);

        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_display_currency, container, false);

        ScrollView scrollLeftView = (ScrollView) view.findViewById(R.id.currency_scroll_left);
        ScrollView scrollRightView = (ScrollView) view.findViewById(R.id.currency_scroll_right);

        String[] currencyNames = getArguments().getStringArray(ALL_CURRENCIES);
        float[] currencyValues = getArguments().getFloatArray(ALL_AMOUNTS);

        for (int i = 0; i < currencyNames.length; ++i) {
            View v = inflater.inflate(R.layout.row_currency, null);
            TextView tv = (TextView) v.findViewById(R.id.currency);
            tv.setText(currencyNames[i]);

            tv = (TextView) v.findViewById(R.id.value);
            tv.setText(String.format("%.2f", currencyValues[i]));

            if (i % 2 == 0) {
                scrollLeftView.addView(v);
            } else {
                scrollRightView.addView(v);
            }
        }

        // Set up the button
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        getDialog().setTitle(R.string.currencies);
        return view;
    }

}
