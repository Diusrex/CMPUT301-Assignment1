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

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

public class AndroidEmailSender extends EmailSender {
    private Activity activity;

    AndroidEmailSender(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void sendEmail(TravelClaim claim) {
        Intent i = createEmailIntent(claim);

        try {
            activity.startActivity(i);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    // The code from this function, as well as the idea to return an intent, is
    // from
    // http://stackoverflow.com/a/14342890/2648858 Feb 2, 2015
    private Intent createEmailIntent(TravelClaim claim) {
        String toEmail = "";
        String subject = "";
        String message = createMessage(claim);

        // Emulators do not like ACTION_SENDTO, so just defaulting to ACTION_SEND
        Intent send = new Intent(Intent.ACTION_SEND);

        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_EMAIL, new String[] { toEmail });
        send.putExtra(Intent.EXTRA_SUBJECT, subject);
        send.putExtra(Intent.EXTRA_TEXT, message);

        return Intent.createChooser(send, "Send mail...");
    }
}
