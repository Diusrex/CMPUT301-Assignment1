package com.example.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity implements TravelClaimArrayAdapterListener {
    private TravelClaimOwner claimsOwner;
    
    private TravelClaimArrayAdapter claimAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        claimsOwner = TravelApplication.getMainOwner();
        setUpClaimsList();
    }

    private void setUpClaimsList() {
        claimAdapter = new TravelClaimArrayAdapter(this, this);
        ListView list = (ListView) findViewById(R.id.claims_list);

        list.setAdapter(claimAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        
        updateClaimsList();
    }

    private void updateClaimsList() {
        claimAdapter.setAllClaims(claimsOwner.getAllClaimsClone());
    }

    public void newClaim(View v) {
        int newClaimPos = claimsOwner.getNumberClaims();

        claimsOwner.createNewClaim();

        displayTravelClaim(newClaimPos);
    }

    @Override
    public void deleteClaim(TravelClaim claim) {
        claimsOwner.deleteClaim(claim);
        updateClaimsList();
    }

    @Override
    public void editClaim(TravelClaim claim) {
        int position = claimsOwner.getClaimPosition(claim);
        displayTravelClaim(position);
    }

    private void displayTravelClaim(int pos) {
        Intent intent = new Intent(this, TravelClaimActivity.class);
        intent.putExtra(TravelClaimActivity.ARGUMENT_CLAIM_POSITION, pos);
        startActivity(intent);
    }
}
