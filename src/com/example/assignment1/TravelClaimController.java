package com.example.assignment1;

import java.util.Calendar;
import java.util.List;

import android.util.Pair;

public class TravelClaimController {
    // Don't even need to store the position of the claim, because this stores a
    // reference
    private TravelClaim claim;

    public TravelClaimController(TravelClaim claim) {
        this.claim = claim;
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

    public void setName(String newName) {
        claim.setName(newName);
    }

    public void setDescription(String newDescription) {
        claim.setDescription(newDescription);
    }

    public void setStartDate(Calendar newStartDate) {
        claim.setStartDate(newStartDate);
    }

    public void setEndDate(Calendar newEndDate) {
        claim.setEndDate(newEndDate);
    }

    public void setState(TravelClaimStates newState) {
        claim.setState(newState);
    }

    public int getNumberOfExpenses() {
        return claim.getAllExpenses().size();
    }

    public void createExpense() {
        claim.createExpense();
    }

    public void deleteExpense(TravelExpense expense) {
        claim.deleteExpense(expense);
    }

    public List<Pair<String, Float>> getCurrencyInformation() {
        return claim.getCurrencyInformation();
    }

    public int getExpensePosition(TravelExpense expense) {
        return claim.getExpensePosition(expense);
    }

    public TravelClaim getTravelClaim() {
        return claim;
    }

}
