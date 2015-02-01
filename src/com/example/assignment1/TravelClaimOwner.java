package com.example.assignment1;

import java.util.ArrayList;

public class TravelClaimOwner {
    SaverAndLoader saverAndLoader;
    private ArrayList<TravelClaim> allClaims;
    
    TravelClaimOwner(SaverAndLoader saverAndLoader) {
        this.saverAndLoader = saverAndLoader;
        allClaims = new ArrayList<TravelClaim>();//saverAndLoader.loadAllTravelClaims();
        allClaims.add(new TravelClaim());
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
