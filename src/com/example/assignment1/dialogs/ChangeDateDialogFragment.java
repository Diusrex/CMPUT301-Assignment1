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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class ChangeDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String CURRENT_TIME = "GivenTime";
    private static final String DATE_ID = "DateId";
    private ChangeDateListener listener;
    private int dateId;

    public static ChangeDateDialogFragment newInstance(Calendar origional, int dateId) {
        ChangeDateDialogFragment f = new ChangeDateDialogFragment();

        Bundle args = new Bundle();

        args.putLong(CURRENT_TIME, origional.getTimeInMillis());
        args.putInt(DATE_ID, dateId);

        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof ChangeDateListener)) {
            throw new RuntimeException(
                    "ChangeDateDialogFragment must be attached to an Activity that implements DateChangeListener");
        }

        listener = (ChangeDateListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateId = getArguments().getInt(DATE_ID);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(getArguments().getLong(CURRENT_TIME));
        return new DatePickerDialog(getActivity(), this, cal.get(Calendar.YEAR), cal
                .get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(year, month, day);
        
        listener.dateChanged(cal, dateId);
    }
}
