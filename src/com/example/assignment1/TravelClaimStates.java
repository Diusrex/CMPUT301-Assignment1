package com.example.assignment1;

enum TravelClaimStates {
    IN_PROGRESS("In Progress"), SUBMITTED("Submitted"), RETURNED("Returned"), APPROVED("Approved");

    private String str;

    TravelClaimStates(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}