package com.example.assignment1;

import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;

public class TravelExpense {
    private Calendar date;
    private String description;
    private Category category;
    private float amount;
    private Currency currency;

    enum Category {
        AIR_FAIR, GROUND_TRANSPORT, VEHICLE_RENTAL, FUEL, PARKING, REGISTARTION, ACCOMMODATION, MEAL, OTHER
    };

    TravelExpense() {
        date = new GregorianCalendar();
        description = "";
        category = Category.OTHER;
        amount = 0;
        currency = CurrencyHandler.getLocalDefaultCurrency();
    }

    public Calendar getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public float getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

}
