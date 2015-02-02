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

public class TravelExpenseArrayAdapter extends ArrayAdapter<TravelExpense> {
    // The idea to use this is from:
    // http://www.vogella.com/tutorials/AndroidListView/article.html on Feb 1,
    // 2015
    private final Context context;
    private final TravelExpenseArrayAdapterListener listener;
    private List<TravelExpense> allExpenses = null;
    private boolean isEditable;

    // Using a ViewHolder:
    // http://stackoverflow.com/a/21501329/2648858 on Feb 1, 2015
    static class TravelExpenseViewHolder {
        TextView currencyAndAmount;
        TextView description;
        TextView date;
        TextView category;
        Button editButton;
        Button deleteButton;
    }

    public TravelExpenseArrayAdapter(TravelExpenseArrayAdapterListener listener, Context context) {
        super(context, R.layout.row_travel_expense);
        this.listener = listener;
        this.context = context;
    }

    public void setAllExpenses(List<TravelExpense> allExpenses) {
        // Need to sort them by ascending date
        Collections.sort(allExpenses, new Comparator<TravelExpense>() {
            public int compare(TravelExpense arg0, TravelExpense arg1) {
                return arg0.getDate().compareTo(arg1.getDate());
            }
        });

        // Not the best way to do it, but it works
        super.clear();
        super.addAll(allExpenses);
        this.allExpenses = allExpenses;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TravelExpenseViewHolder holder;
        // By doing this, it will be far faster because it will not need to
        // always recreate a row
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_travel_expense, parent, false);

            holder = new TravelExpenseViewHolder();
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.currencyAndAmount = (TextView) convertView.findViewById(R.id.currency_and_amount);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.editButton = (Button) convertView.findViewById(R.id.edit_button);
            holder.deleteButton = (Button) convertView.findViewById(R.id.delete_button);

            convertView.setTag(holder);
        } else {
            holder = (TravelExpenseViewHolder) convertView.getTag();
        }

        TravelExpense currentExpense = allExpenses.get(position);

        holder.description.setText(currentExpense.getDescription());
        String currencyText = Float.toString(currentExpense.getAmount()) + " "
                + currentExpense.getCurrency().getCurrencyCode();
        holder.currencyAndAmount.setText(currencyText);
        
        holder.category.setText(currentExpense.getCategory().toString());

        holder.date.setText(Utilities.getFormattedDateString(context, R.string.date, currentExpense.getDate()));
        if (isEditable) {
            setButtonEditing(position, holder);
        } else {
            holder.deleteButton.setEnabled(false);
            holder.editButton.setEnabled(false);
        }
        return convertView;
    }

    private void setButtonEditing(final int position, TravelExpenseViewHolder holder) {
        holder.deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                listener.deleteExpense(allExpenses.get(position));
            }
        });
        holder.deleteButton.setEnabled(true);

        holder.editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.editExpense(allExpenses.get(position));
            }
        });
        holder.editButton.setEnabled(true);
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
}