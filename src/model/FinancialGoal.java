
package model;

import java.sql.Date;

public class FinancialGoal {

    private int id;
    private int userId;
    private String goalName;
    private double targetAmount;
    private double savedAmount;
    private Date targetDate;

    public FinancialGoal(int userId, String goalName,
                         double targetAmount, double savedAmount,
                         Date targetDate) {
        this.userId = userId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.savedAmount = savedAmount;
        this.targetDate = targetDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public String getGoalName() {
        return goalName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }
}