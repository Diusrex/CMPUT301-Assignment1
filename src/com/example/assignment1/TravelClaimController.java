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
