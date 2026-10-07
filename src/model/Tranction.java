package model;

import java.sql.Date;

public class Tranction {

    private int id;
    private int userId;
    private String type;
    private double amount;
    private String category;
    private String description;
    private Date transactionDate;

    public Tranction(
            int userId,
            String type,
            double amount,
            String category,
            String description,
            Date transactionDate) {

        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}

