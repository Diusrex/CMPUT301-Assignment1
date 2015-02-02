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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TravelClaimArrayAdapter extends ArrayAdapter<TravelClaim> {
    // The idea to use this is from:
    // http://www.vogella.com/tutorials/AndroidListView/article.html on Feb 1,
    // 2015
    private final Context context;
    private final TravelClaimArrayAdapterListener listener;
    private List<TravelClaim> allClaims;

    // Using a ViewHolder:
    // http://stackoverflow.com/a/21501329/2648858 on Feb 1, 2015
    static class TravelExpenseViewHolder {
        TextView name;
        TextView startDate;
        TextView endDate;
        TextView status;
        Button editButton;
        Button deleteButton;
    }

    public TravelClaimArrayAdapter(TravelClaimArrayAdapterListener listener, Context context) {
        super(context, R.layout.row_travel_expense);
        this.listener = listener;
        this.context = context;
    }

    public void setAllClaims(List<TravelClaim> allClaims) {
        // Need to sort them by ascending date
        Collections.sort(allClaims, new Comparator<TravelClaim>() {
            public int compare(TravelClaim arg0, TravelClaim arg1) {
                return arg0.getStartDate().compareTo(arg1.getStartDate());
            }
        });
        
        // Not the best way to do it, but it works
        clear();
        addAll(allClaims);
        
        this.allClaims = allClaims;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TravelExpenseViewHolder holder;
        // By doing this, it will be far faster because it will not need to
        // always recreate a row
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_travel_claim, parent, false);

            holder = new TravelExpenseViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.startDate = (TextView) convertView.findViewById(R.id.start_date_text);
            holder.endDate = (TextView) convertView.findViewById(R.id.end_date_text);
            holder.status = (TextView) convertView.findViewById(R.id.status);

            holder.editButton = (Button) convertView.findViewById(R.id.edit_button);
            holder.deleteButton = (Button) convertView.findViewById(R.id.delete_button);

            convertView.setTag(holder);
        } else {
            holder = (TravelExpenseViewHolder) convertView.getTag();
        }

        
        TravelClaim currentClaim = allClaims.get(position);
        holder.name.setText(currentClaim.getName());
        holder.status.setText(currentClaim.getState().toString());
        
        holder.startDate.setText(Utilities.getFormattedDateString(context, 
                R.string.start_date, currentClaim.getStartDate()));
        
        holder.endDate.setText(Utilities.getFormattedDateString(context, 
                R.string.end_date, currentClaim.getEndDate()));

        setButtonEditing(position, holder);
        
        return convertView;
    }

    private void setButtonEditing(final int position, TravelExpenseViewHolder holder) {
        holder.deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                listener.deleteClaim(allClaims.get(position));
            }
        });

        holder.editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.editClaim(allClaims.get(position));
            }
        });
    }
}
