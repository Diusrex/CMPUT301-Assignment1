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

import java.util.List;

public class TravelClaimOwner {
    PersistenceHandler saverAndLoader;
    private List<TravelClaim> allClaims;

    TravelClaimOwner(PersistenceHandler saverAndLoader) {
        this.saverAndLoader = saverAndLoader;
        allClaims = saverAndLoader.loadAllTravelClaims();
    }

    public TravelClaim getClaim(int claimPosition) {
        return allClaims.get(claimPosition);
    }

    public void dataHasBeenUpdated() {
        saverAndLoader.saveAllTravelClaims(allClaims);
    }

    public TravelClaimController getTravelClaimController(int claimPosition) {
        return new TravelClaimController(getClaim(claimPosition));
    }

    public TravelExpenseController getTravelExpenseController(int claimPosition, int expensePosition) {
        TravelClaim claim = allClaims.get(claimPosition);
        TravelExpense expense = claim.getAllExpenses().get(expensePosition);
        return new TravelExpenseController(expense);
    }

    public void createNewClaim() {
        allClaims.add(new TravelClaim());
        dataHasBeenUpdated();
    }

    public int getNumberClaims() {
        return allClaims.size();
    }

    public List<TravelClaim> getAllClaimsClone() {
        return allClaims;
    }

    public void deleteClaim(TravelClaim claim) {
        allClaims.remove(claim);
        dataHasBeenUpdated();
    }

    public int getClaimPosition(TravelClaim claim) {
        return allClaims.indexOf(claim);
    }

}
