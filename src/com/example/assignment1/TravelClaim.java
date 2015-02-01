package com.example.assignment1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.assignment1.TravelClaim.State;

public class TravelClaim extends FModel<FView> {
    private Calendar startDate, endDate;
    private String description;
    private ArrayList<TravelExpense> allExpenses;

    private State currentState;

    enum State {
        IN_PROGRESS("In Progress"), SUBMITTED("Submitted"), RETURNED("Returned"), APPROVED("Approved");
        
        private String str;
        State(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }

    public TravelClaim() {
        startDate = new GregorianCalendar();
        endDate = new GregorianCalendar();
        description = "";
        allExpenses = new ArrayList<TravelExpense>();
        currentState = State.IN_PROGRESS;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        if (!description.equals(newDescription) && mayBeEdited()) {
            description = newDescription;
            updated();
        }
    }

    public void setStartDate(Calendar newDate) {
        if (!startDate.equals(newDate) && mayBeEdited()) {

            // Not possible for end date to be less than start date
            if (calLessThan(endDate, newDate)) {
                startDate = endDate;
            } else {
                startDate = newDate;
            }
            updated();
        }
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar newEndDate) {
        if (!endDate.equals(newEndDate) && mayBeEdited()) {

            // Not possible for end date to be less than start date
            if (calLessThan(newEndDate, startDate)) {
                endDate = startDate;
            } else {
                endDate = newEndDate;
            }
            updated();
        }
    }

    public void setState(State newState) {
        if (isValidStateChange(newState)) {
            currentState = newState;
            
            // EDIT: Need to possibly do email
            updated();
        }
    }

    public boolean isValidStateChange(State newState) {
        switch (currentState)
        {
        case IN_PROGRESS:
            // Should fall through
        case RETURNED:
            return newState == State.SUBMITTED;
            
        case SUBMITTED:
            return newState == State.APPROVED || newState == State.RETURNED;
            
        default:
            return false;
        }
    }

    public State getState() {
        return currentState;
    }

    public boolean mayBeEdited() {
        return (currentState == State.IN_PROGRESS || currentState == State.RETURNED);
    }

    private void updated() {
        TravelClaimOwner owner = TravelApplication.getMainOwner();
        owner.dataHasBeenUpdated();
        notifyViews();
    }

    private boolean calLessThan(Calendar n1, Calendar n2) {
        return n1.getTimeInMillis() < n2.getTimeInMillis();
    }

}
