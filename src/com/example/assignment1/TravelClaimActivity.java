package com.example.assignment1;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment1.dialogs.ChangeDateDialogFragment;
import com.example.assignment1.dialogs.ChangeDateListener;

// Will be responsible for displaying a TravelClaim
public class TravelClaimActivity extends Activity implements FView<TravelClaim>, ChangeDateListener {
    private static final int START_DATE_ID = 0;
    private static final int END_DATE_ID = 1;
    public static final String ARGUMENT_CLAIM_POSITION = "ClaimPosition";

    private TravelClaimController controller;

    TextView currentStatus;

    EditText description;

    TextView startDateText;
    TextView endDateText;
    Button startDateButton;
    Button endDateButton;
    
    Menu menu;

    TextWatcher descriptionTextWather = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            controller.setDescription(s.toString());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_claim);

        setUpLayout();

        setUpClaimToDisplay();
    }

    private void setUpClaimToDisplay() {
        int claimPosition = getIntent().getIntExtra(ARGUMENT_CLAIM_POSITION, 0);
        TravelClaimOwner owner = TravelApplication.getMainOwner();
        controller = owner.getTravelClaimController(claimPosition);
        controller.addView(this);
    }

    private void setUpLayout() {
        setUpButtons();
        setUpTextBoxes();
        setUpDescriptionTextBox();
    }

    private void setUpButtons() {
        startDateButton = (Button) findViewById(R.id.change_start_date);
        endDateButton = (Button) findViewById(R.id.change_end_date);
    }

    private void setUpTextBoxes() {
        currentStatus = (TextView) findViewById(R.id.current_status);
        startDateText = (TextView) findViewById(R.id.start_date_text);
        endDateText = (TextView) findViewById(R.id.end_date_text);
    }

    private void setUpDescriptionTextBox() {
        description = (EditText) findViewById(R.id.description);

        description.addTextChangedListener(descriptionTextWather);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.deleteView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        
    }

    @Override
    public void update(TravelClaim model) {
        updateStatus(model);
        updateDates(model);
        updateDescription(model);
        updateStatesMenuBar(model);
    }

    private void updateStatus(TravelClaim model) {
        String text = getResources().getString(R.string.current_status, model.getState());
        currentStatus.setText(text);
    }

    private void updateDates(TravelClaim model) {
        startDateText.setText(getFormattedDateString(R.string.start_date, model.getStartDate()));
        startDateButton.setOnClickListener(new DateButtonClickListener(model.getStartDate(), START_DATE_ID));

        endDateText.setText(getFormattedDateString(R.string.end_date, model.getEndDate()));
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

    private void updateDescription(TravelClaim model) {
        String newText = model.getDescription();

        // This way, it will keep the position in the edit text
        if (!newText.equals(description.getText().toString())) {
            description.setText(model.getDescription());
        }
    }

    private void updateStatesMenuBar(TravelClaim model) {
        boolean shouldShowSubmit = model.isValidStateChange(TravelClaim.State.SUBMITTED);
        menu.findItem(R.id.submit).setVisible(shouldShowSubmit);
        
        boolean shouldShowReturned = model.isValidStateChange(TravelClaim.State.RETURNED);
        menu.findItem(R.id.returned).setVisible(shouldShowReturned);
        
        boolean shouldShowApproved = model.isValidStateChange(TravelClaim.State.APPROVED);
        menu.findItem(R.id.approved).setVisible(shouldShowApproved);
    }

    private String getFormattedDateString(int stringId, Calendar date) {
        String dateOutput = DateFormat.getDateInstance().format(date.getTime());
        return getResources().getString(stringId, dateOutput);
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

    // These two functions are for allowing the user to change the state of the program
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
        Log.w("TravelClaimActivity", "Value: " + item.getItemId() + " others: " + R.id.submit + " " + R.id.returned + " " + R.id.approved);
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.submit:
                controller.setState(TravelClaim.State.SUBMITTED);
                return true;
            case R.id.returned:
                controller.setState(TravelClaim.State.RETURNED);
                return true;
            case R.id.approved:
                controller.setState(TravelClaim.State.APPROVED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
