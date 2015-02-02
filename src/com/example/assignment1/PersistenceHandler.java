package com.example.assignment1;

import java.util.ArrayList;
import java.util.List;

public interface PersistenceHandler {
    public List<TravelClaim> loadAllTravelClaims();
    
    public void saveAllTravelClaims(List<TravelClaim> allClaims);
}
