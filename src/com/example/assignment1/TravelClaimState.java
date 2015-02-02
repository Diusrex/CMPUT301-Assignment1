package com.example.assignment1;

enum TravelClaimState {
    IN_PROGRESS("In Progress"), SUBMITTED("Submitted"), RETURNED("Returned"), APPROVED("Approved");

    private String str;

    TravelClaimState(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}