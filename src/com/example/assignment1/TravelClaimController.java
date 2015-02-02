package com.example.assignment1;

import java.util.ArrayList;
import java.util.Calendar;

import android.util.Pair;

import com.example.assignment1.TravelClaim.State;

public class TravelClaimController {
    // Don't even need to store the position of the claim, because this stores a
    // reference
    private TravelClaim claim;

    public TravelClaimController(TravelClaim claim) {
        this.claim = claim;
    }

    public void setDescription(String description) {
        claim.setDescription(description);
    }

    public void setStartDate(Calendar startDate) {
        claim.setStartDate(startDate);
    }

    public void setEndDate(Calendar endDate) {
        claim.setEndDate(endDate);
    }

    public void addView(FView<TravelClaim> view) {
        claim.addView(view);
    }

    public void requestUpdate() {
        claim.notifyViews();
    }

    public void deleteView(FView<TravelClaim> view) {
        claim.deleteView(view);
    }

    public void setState(State state) {
        claim.setState(state);
        
    }

    public void deleteExpense(TravelExpense expense) {
        claim.deleteExpense(expense);
        
    }

    public ArrayList<Pair<String, Float>> getCurrencyInformation() {
        return claim.getCurrencyInformation();
        
    }
}
