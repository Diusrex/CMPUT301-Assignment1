/*
 * Copyright 2015 Morgan Redshaw
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.example.assignment1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AndroidPersistence implements PersistenceHandler {
    private static final String SAVE_FILE = "save_file";
    private Context context;

    public AndroidPersistence(Context context) {
        this.context = context;
    }

    // All of the code in this class is based on what we discussed in Lab 3 of
    // CMPUT 301
    public List<TravelClaim> loadAllTravelClaims() {
        Gson gson = new Gson();
        try {
            FileInputStream fis = context.openFileInput(SAVE_FILE);
            // based on
            // http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/index.html
            Type listType = new TypeToken<List<TravelClaim>>() {
            }.getType();

            InputStreamReader isr = new InputStreamReader(fis);
            List<TravelClaim> travelClaims = gson.fromJson(isr, listType);
            if (travelClaims == null) {
                travelClaims = new ArrayList<TravelClaim>();
            }
            fis.close();

            return travelClaims;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<TravelClaim>();
    }

    public void saveAllTravelClaims(List<TravelClaim> allClaims) {
        Gson gson = new Gson();

        try {
            FileOutputStream fos = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            gson.toJson(allClaims, osw);
            osw.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
