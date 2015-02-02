package com.example.assignment1;

import java.util.ArrayList;

import android.util.Log;

public class TravelClaimOwner {
    SaverAndLoader saverAndLoader;
    private ArrayList<TravelClaim> allClaims;
    
    TravelClaimOwner(SaverAndLoader saverAndLoader) {
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
}
