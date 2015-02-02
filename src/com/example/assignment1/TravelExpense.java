package com.example.assignment1;

import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;

import com.example.assignment1.TravelExpense.Category;

public class TravelExpense extends FModel<FView> {
    private Calendar date;
    private String description;
    private Category category;
    private float amount;
    private Currency currency;

    enum Category {
        AIR_FAIR("Air Fair"), GROUND_TRANSPORT("Ground Transport"), VEHICLE_RENTAL("Vehicle Rental"), FUEL("Fuel"), PARKING(
                "Parking"), REGISTARTION("Registration"), ACCOMMODATION("Accomodation"), MEAL("Meal"), OTHER("Other");

        private String asStr;

        Category(String str) {
            asStr = str;
        }

        @Override
        public String toString() {
            return asStr;
        }
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

    public void setDate(Calendar newDate) {
        if (!date.equals(newDate)) {
            date = newDate;
            updated();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String string) {
        if (!description.equals(string)) {
            description = string;
            updated();
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category newCategory) {
        if (!category.equals(newCategory)) {
            category = newCategory;
            updated();
        }
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float newAmount) {
        // Sufficiently different
        if (Utilities.floatsAreDifferent(amount, newAmount)) {
            amount = newAmount;
            updated();
        }
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency newCurrency) {
        if (!currency.equals(newCurrency)) {
            currency = newCurrency;
            updated();
        }

    }

    private void updated() {
        TravelClaimOwner owner = TravelApplication.getMainOwner();
        owner.dataHasBeenUpdated();
        notifyViews();
    }

}
