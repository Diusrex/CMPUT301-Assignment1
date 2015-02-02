package com.example.assignment1;

import java.util.Calendar;
import java.util.Currency;

import com.example.assignment1.TravelExpense.Category;

public class TravelExpenseController {
    // Don't even need to store the position of the claim, because this stores a
    // reference
    private TravelExpense expense;

    public TravelExpenseController(TravelExpense expense) {
        this.expense = expense;
    }

    public void requestUpdate() {
        expense.notifyViews();

    }

    public void addView(FView<TravelExpense> view) {
        expense.addView(view);
    }

    public void deleteView(FView<TravelExpense> view) {
        expense.deleteView(view);
    }

    public void setDescription(String string) {
        expense.setDescription(string);
    }

    public void setAmount(float amount) {
        expense.setAmount(amount);
    }

    public void setDate(Calendar newDate) {
        expense.setDate(newDate);
    }

    public void currencySelected(Currency selected) {
        expense.setCurrency(selected);
    }

    public void categroySelected(Category selected) {
        expense.setCategory(selected);
    }

}