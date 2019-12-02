package com.hci.registration;

public class BasicInfo {

    private int totalCreditHour;
    private int inProgress;
    private double GPA;

    public BasicInfo() {
        super();
    }

    public BasicInfo(int totalCreditHour, int inProgress, double GPA) {
        this.totalCreditHour = totalCreditHour;
        this.inProgress = inProgress;
        this.GPA = GPA;
    }

    public int getTotalCreditHour() {
        return totalCreditHour;
    }

    public void setTotalCreditHour(int totalCreditHour) {
        this.totalCreditHour = totalCreditHour;
    }

    public int getInProgress() {
        return inProgress;
    }

    public void setInProgress(int inProgress) {
        this.inProgress = inProgress;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = (double)Math.round(GPA*100)/100;
    }

    public void setGPA(int GPA){
        if (GPA == 0){
            this.GPA = 0.00;
        } else {
            this.GPA = (double)Math.round(GPA*100)/100;
        }
    }

}
