package com.example.assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import android.util.Pair;

// Pure static class
public final class CurrencyHandler {

    private CurrencyHandler() {

    }

    public static Currency getLocalDefaultCurrency() {
        return Currency.getInstance(Locale.getDefault());
    }
    
    public static Set<Currency> getAllCurrencies()
    {
        // This function is entirely from http://stackoverflow.com/a/3537085/2648858
        // on Feb 1, 2015
        Set<Currency> toret = new HashSet<Currency>();
        Locale[] locs = Locale.getAvailableLocales();

        for(Locale loc : locs) {
            try {
                toret.add( Currency.getInstance( loc ) );
            } catch(Exception exc)
            {
                // Locale not found
            }
        }

        return toret;
    }
    
    
}
