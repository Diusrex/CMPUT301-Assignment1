package com.example.assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
