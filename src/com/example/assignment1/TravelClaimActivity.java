package com.example.assignment1;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assignment1.dialogs.ChangeDateDialogFragment;
import com.example.assignment1.dialogs.ChangeDateListener;
import com.example.assignment1.dialogs.DisplayCurrencyUsageInfoDialogFragment;

// Will be responsible for displaying a TravelClaim
public class TravelClaimActivity extends Activity implements FView<TravelClaim>, ChangeDateListener,
        TravelExpenseArrayAdapterListener {
    private static final int START_DATE_ID = 0;
    private static final int END_DATE_ID = 1;
    public static final String ARGUMENT_CLAIM_POSITION = "ClaimPosition";

    private TravelClaimController controller;

    private TextView currentStatus;

    private EditText name;
    private EditText description;

    private TextView startDateText;
    private TextView endDateText;
    private Button startDateButton;
    private Button endDateButton;

    private Button createExpenseButton;

    private TravelExpenseArrayAdapter expenseAdapter;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_claim);

        findLayoutItems();

        setUpClaimToDisplay();
    }

    private void setUpClaimToDisplay() {
        int claimPosition = getIntent().getIntExtra(ARGUMENT_CLAIM_POSITION, 0);
        TravelClaimOwner owner = TravelApplication.getMainOwner();
        controller = owner.getTravelClaimController(claimPosition);
        controller.addView(this);
    }

    private void findLayoutItems() {
        findButtons();
        findTextBoxes();
        setUpDescriptionTextBox();
        setUpNameTextBox();
        setUpExpensesList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        controller.requestUpdate();
    }

    private void findButtons() {
        startDateButton = (Button) findViewById(R.id.change_start_date);
        endDateButton = (Button) findViewById(R.id.change_end_date);
        createExpenseButton = (Button) findViewById(R.id.new_expense);
    }

    private void findTextBoxes() {
        currentStatus = (TextView) findViewById(R.id.current_status);
        startDateText = (TextView) findViewById(R.id.start_date_text);
        endDateText = (TextView) findViewById(R.id.end_date_text);
    }

    private void setUpDescriptionTextBox() {
        description = (EditText) findViewById(R.id.description);

        description.addTextChangedListener(new TextWatcher() {
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
        });

        // Save it so that it can be restored later
        description.setTag(description.getKeyListener());
    }

    private void setUpNameTextBox() {
        name = (EditText) findViewById(R.id.name);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                controller.setName(s.toString());
            }
        });

        // Save it so that it can be restored later
        name.setTag(name.getKeyListener());
    }

    private void setUpExpensesList() {
        expenseAdapter = new TravelExpenseArrayAdapter(this, this);
        ListView list = (ListView) findViewById(R.id.expense_list);

        list.setAdapter(expenseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.deleteView(this);
    }

    @Override
    public void update(TravelClaim model) {
        if (model.mayBeEdited()) {
            enableEditing();
        } else {
            disableEditing();
        }

        updateStatus(model);
        updateDates(model);
        updateName(model);
        updateDescription(model);
        updateStatesMenuBar(model);
        updateTravelExpenseList(model);
    }

    private void enableEditing() {
        description.setKeyListener((KeyListener) description.getTag());
        name.setKeyListener((KeyListener) name.getTag());
        startDateButton.setEnabled(true);
        endDateButton.setEnabled(true);
        createExpenseButton.setEnabled(true);
        expenseAdapter.setEditable(true);
    }

    private void disableEditing() {
        description.setKeyListener(null);
        name.setKeyListener(null);
        startDateButton.setEnabled(false);
        endDateButton.setEnabled(false);
        createExpenseButton.setEnabled(false);
        expenseAdapter.setEditable(false);
    }

    private void updateStatus(TravelClaim model) {
        String text = getResources().getString(R.string.current_status, model.getState());
        currentStatus.setText(text);
    }

    private void updateDates(TravelClaim model) {
        startDateText.setText(Utilities.getFormattedDateString(this, R.string.start_date, model.getStartDate()));
        startDateButton.setOnClickListener(new DateButtonClickListener(model.getStartDate(), START_DATE_ID));

        endDateText.setText(Utilities.getFormattedDateString(this, R.string.end_date, model.getEndDate()));
        endDateButton.setOnClickListener(new DateButtonClickListener(model.getEndDate(), END_DATE_ID));
    }

    private class DateButtonClickListener implements OnClickListener {
        private final Calendar initialDate;
        private final int dateId;

        DateButtonClickListener(Calendar initialDate, int dateId) {
            this.initialDate = initialDate;
            this.dateId = dateId;
        }

        @Override
        public void onClick(View v) {
            displayDialogFragment(ChangeDateDialogFragment.newInstance(initialDate, dateId));
        }
    }

    private void updateName(TravelClaim model) {
        String newText = model.getName();

        // This way, it will keep the position in the edit text
        if (!newText.equals(name.getText().toString())) {
            name.setText(newText);
        }
    }

    private void updateDescription(TravelClaim model) {
        String newText = model.getDescription();

        // This way, it will keep the position in the edit text
        if (!newText.equals(description.getText().toString())) {
            description.setText(newText);
        }

    }

    private void updateStatesMenuBar(TravelClaim model) {
        if (menu == null)
            return;

        boolean shouldShowSubmit = model.isValidStateChange(TravelClaimStates.SUBMITTED);
        menu.findItem(R.id.submit).setVisible(shouldShowSubmit);

        boolean shouldShowReturned = model.isValidStateChange(TravelClaimStates.RETURNED);
        menu.findItem(R.id.returned).setVisible(shouldShowReturned);

        boolean shouldShowApproved = model.isValidStateChange(TravelClaimStates.APPROVED);
        menu.findItem(R.id.approved).setVisible(shouldShowApproved);
    }

    private void updateTravelExpenseList(TravelClaim model) {
        expenseAdapter.setAllExpenses(model.getAllExpenses());
    }

    @Override
    public void dateChanged(Calendar newCalendarDate, int dateId) {
        if (dateId == START_DATE_ID) {
            controller.setStartDate(newCalendarDate);
        } else if (dateId == END_DATE_ID) {
            controller.setEndDate(newCalendarDate);
        }
    }

    private void displayDialogFragment(DialogFragment fragment) {
        fragment.show(getFragmentManager(), "dialogfragment");
    }

    // These two functions are for allowing the user to change the state of the
    // program
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.travel_claim_states, menu);

        this.menu = menu;

        controller.requestUpdate();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
        case R.id.submit:
            controller.setState(TravelClaimStates.SUBMITTED);
            return true;
        case R.id.returned:
            controller.setState(TravelClaimStates.RETURNED);
            return true;
        case R.id.approved:
            controller.setState(TravelClaimStates.APPROVED);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void createEmail(View v) {
        AndroidEmailSender sender = new AndroidEmailSender(this);

        sender.sendEmail(controller.getTravelClaim());
    }

    public void displayCurrency(View v) {
        List<Pair<String, Float>> mergedPayments = controller.getCurrencyInformation();
        DisplayCurrencyUsageInfoDialogFragment dialogFragment = DisplayCurrencyUsageInfoDialogFragment
                .newInstance(mergedPayments);
        displayDialogFragment(dialogFragment);
    }

    @Override
    public void deleteExpense(TravelExpense expense) {
        controller.deleteExpense(expense);

    }

    public void createExpense(View v) {
        int newExpensePos = controller.getNumberOfExpenses();
        controller.createExpense();

        displayExpense(newExpensePos);
    }

    @Override
    public void editExpense(TravelExpense expense) {
        int expensePosition = controller.getExpensePosition(expense);

        displayExpense(expensePosition);
    }

    private void displayExpense(int expensePosition) {
        int claimPosition = getIntent().getIntExtra(ARGUMENT_CLAIM_POSITION, 0);

        Intent intent = new Intent(this, TravelExpenseActivity.class);

        intent.putExtra(TravelExpenseActivity.ARGUMENT_CLAIM_POSITION, claimPosition);
        intent.putExtra(TravelExpenseActivity.ARGUMENT_EXPENSE_POSITION, expensePosition);

        startActivity(intent);
    }
}
