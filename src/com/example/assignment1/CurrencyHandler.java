package com.example.assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

// Pure static class
public final class CurrencyHandler {
    private static List<Currency> currencyList = null;

    private CurrencyHandler() {

    }

    public static Currency getLocalDefaultCurrency() {
        return Currency.getInstance(Locale.getDefault());
    }

    public static List<Currency> getAllCurrencies() {
        if (currencyList != null)
            return currencyList;

        // Setting up the Set of currencies is from
        // http://stackoverflow.com/a/3537085/2648858
        // on Feb 1, 2015
        Set<Currency> setOfCurrencies = new HashSet<Currency>();
        Locale[] locs = Locale.getAvailableLocales();

        for (Locale loc : locs) {
            try {
                setOfCurrencies.add(Currency.getInstance(loc));
            } catch (Exception exc) {
                // Locale not found
            }
        }

        currencyList = new ArrayList<Currency>(setOfCurrencies);
        // Sort the list to make it easier to go through
        Collections.sort(currencyList, new Comparator<Currency>() {
            public int compare(Currency arg0, Currency arg1) {
                return arg0.toString().compareTo(arg1.toString());
            }
        });

        return currencyList;
    }

    public static int getCurrencyPos(Currency wantedCurrency) {
        List<Currency> currencies = getAllCurrencies();
        
        int pos = 0;
        
        for (Currency currency : currencies) {
            if (currency.getCurrencyCode().equals(wantedCurrency.getCurrencyCode()))
                return pos;
            ++pos;
        }
        
        return -1;
    }
}
