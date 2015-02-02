package com.example.assignment1;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

public class EmailSender {
    private Activity activity;

    EmailSender(Activity activity) {
        this.activity = activity;
    }

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

        Intent sendTo = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode(toEmail) + "?subject=" + Uri.encode(subject) + "&body="
                + Uri.encode(message);
        Uri uri = Uri.parse(uriText);
        sendTo.setData(uri);

        List<ResolveInfo> resolveInfos = activity.getPackageManager().queryIntentActivities(sendTo, 0);

        // Emulators may not like this check...
        if (!resolveInfos.isEmpty()) {
            return sendTo;
        }

        // Nothing resolves send to, so fallback to send...
        Intent send = new Intent(Intent.ACTION_SEND);

        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_EMAIL, new String[] { toEmail });
        send.putExtra(Intent.EXTRA_SUBJECT, subject);
        send.putExtra(Intent.EXTRA_TEXT, message);

        return Intent.createChooser(send, "Send mail...");

    }

    private String createMessage(TravelClaim claim) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Description of claim: %s\n%s to %s\n\n", claim.getDescription(),
                Utilities.getFormattedDateString(activity, R.string.start_date, claim.getStartDate()),
                Utilities.getFormattedDateString(activity, R.string.end_date, claim.getEndDate())));

        builder.append("Expenses:\n\n");
        for (TravelExpense expense : claim.getAllExpenses()) {
            builder.append(String.format("Description: %s\nCategory: %s, Currency: %s, Amount: %.2f.\nDate: %s\n\n", expense.getDescription(),
                    expense.getCategory(), expense.getCurrency(), expense.getAmount(),
                    Utilities.getFormattedDateString(activity, R.string.date, expense.getDate())));
        }

        return builder.toString();
    }
}
