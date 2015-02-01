package com.example.assignment1;

import java.util.ArrayList;

public interface SaverAndLoader {
    public ArrayList<TravelClaim> loadAllTravelClaims();
    
    public void saveAllTravelClaims(ArrayList<TravelClaim> allClaims);
}
