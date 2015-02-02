package com.example.assignment1;

import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assignment1.TravelExpense.Category;
import com.example.assignment1.dialogs.ChangeDateDialogFragment;
import com.example.assignment1.dialogs.ChangeDateListener;

public class TravelExpenseActivity extends Activity implements FView<TravelExpense>, ChangeDateListener,
        OnItemSelectedListener {
    private static final int DATE_ID = 0;
    public static final String ARGUMENT_CLAIM_POSITION = "ClaimPosition";
    public static final String ARGUMENT_EXPENSE_POSITION = "ExpensePosition";

    private TravelExpenseController controller;

    private EditText description;
    private EditText amount;

    private TextView dateText;

    private Calendar date;

    private Spinner currencySpinner;
    private ArrayAdapter<Currency> currencyAdapter;

    private Spinner categorySpinner;
    private ArrayAdapter<TravelExpense.Category> categoryAdapter;

    private TextWatcher descriptionTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            controller.setDescription(s.toString());
        }
    };

    // http://stackoverflow.com/a/13716269/2648858 Feb 1, 2015
    public class CurrencyFormatInputFilter implements InputFilter {

        Pattern mPattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            String result = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());

            Matcher matcher = mPattern.matcher(result);

            if (!matcher.matches())
                return dest.subSequence(dstart, dend);

            return null;
        }
    }

    private TextWatcher floatTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                controller.setAmount(Float.valueOf(s.toString()));
            } catch (NumberFormatException e) {
                // There was nothing in s. If they quit, will just be the last
                // digit that was left
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense);

        findLayoutItems();
        setUpSpinners();

        setUpExpenseToDisplay();
    }

    private void setUpExpenseToDisplay() {
        int claimPosition = getIntent().getIntExtra(ARGUMENT_CLAIM_POSITION, 0);
        int expensePosition = getIntent().getIntExtra(ARGUMENT_EXPENSE_POSITION, 0);

        TravelClaimOwner owner = TravelApplication.getMainOwner();
        controller = owner.getTravelExpenseController(claimPosition, expensePosition);
        controller.addView(this);
    }

    private void findLayoutItems() {
        dateText = (TextView) findViewById(R.id.date_text);

        description = (EditText) findViewById(R.id.description);
        description.addTextChangedListener(descriptionTextWatcher);

        amount = (EditText) findViewById(R.id.amount);
        amount.setFilters(new InputFilter[] { new CurrencyFormatInputFilter() });
        amount.addTextChangedListener(floatTextWatcher);

        currencySpinner = (Spinner) findViewById(R.id.currency);
        categorySpinner = (Spinner) findViewById(R.id.category);
    }

    private void setUpSpinners() {
        setUpCurrencySpinner();
        setUpCategorySpinner();
    }

    private void setUpCurrencySpinner() {
        List<Currency> allCurrencies = CurrencyHandler.getAllCurrencies();

        currencyAdapter = new ArrayAdapter<Currency>(this, android.R.layout.simple_spinner_item, allCurrencies);

        currencySpinner.setAdapter(currencyAdapter);
        currencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Currency selected = currencyAdapter.getItem(position);
                controller.currencySelected(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void setUpCategorySpinner() {
        Category[] allCategories = Category.values();

        categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, allCategories);

        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Category selected = categoryAdapter.getItem(position);
                controller.categroySelected(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        controller.deleteView(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        controller.requestUpdate();
    }

    @Override
    public void update(TravelExpense model) {
        // TODO Auto-generated method stub
        date = model.getDate();

        updateDescription(model);
        updateAmount(model);

        dateText.setText(Utilities.getFormattedDateString(this, R.string.current_date, model.getDate()));

        updateSpinners(model);
    }

    private void updateDescription(TravelExpense model) {
        String newText = model.getDescription();

        // This way, it will keep the position in the edit text
        if (!newText.equals(description.getText().toString())) {
            description.setText(model.getDescription());
        }
    }

    private void updateAmount(TravelExpense model) {
        boolean shouldUpdate = true;
        try {
            float ownAmount = Float.valueOf(amount.getText().toString());

            shouldUpdate = Utilities.floatsAreDifferent(ownAmount, model.getAmount());
        } catch (NumberFormatException e) {
            // The amount was invalid
        }

        if (shouldUpdate) {
            amount.setText(Float.toString(model.getAmount()));
        }
    }

    private void updateSpinners(TravelExpense model) {
        int currencyPos = currencyAdapter.getPosition(model.getCurrency());
        currencySpinner.setSelection(currencyPos);

        int categoryPos = categoryAdapter.getPosition(model.getCategory());
        categorySpinner.setSelection(categoryPos);
    }

    public void changeDate(View v) {
        displayDialogFragment(ChangeDateDialogFragment.newInstance(date, DATE_ID));
    }

    private void displayDialogFragment(DialogFragment fragment) {
        fragment.show(getFragmentManager(), "dialogfragment");
    }

    @Override
    public void dateChanged(Calendar newDate, int dateId) {
        controller.setDate(newDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
