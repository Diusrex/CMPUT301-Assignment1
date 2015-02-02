package com.example.assignment1;

public abstract class EmailSender {
    public abstract void sendEmail(TravelClaim claim);

    protected String createMessage(TravelClaim claim) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Name: %s\nDescription of claim: %s\n%s to %s\n\n", claim.getName(),
                claim.getDescription(), Utilities.getFormattedDateString("Start date: %s", claim.getStartDate()),
                Utilities.getFormattedDateString("End date: %s", claim.getEndDate())));

        builder.append("Expenses:\n\n");
        for (TravelExpense expense : claim.getAllExpenses()) {
            builder.append(String.format("Description: %s\nCategory: %s, Currency: %s, Amount: %.2f.\nDate: %s\n\n",
                    expense.getDescription(), expense.getCategory(), expense.getCurrency(), expense.getAmount(),
                    Utilities.getFormattedDateString("%s", expense.getDate())));
        }

        return builder.toString();
    }
}
