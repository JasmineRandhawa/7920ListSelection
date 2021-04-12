package com.example.ListSelection.Main;

import java.io.Serializable;
import java.util.List;

public class Trial implements Serializable , Comparable<Trial> {
    private int TrialNumber;
    private List<TrialAttempt> TrialAttempts;
    private int TotalAttempts ;
    private String TrialType;
    private int TotalSuccessfulAttempts ;
    private int TotalFailureAttempts ;


    public Trial(int trialNumber, String trialType, List<TrialAttempt> trialAttempts, int totalAttempts, int totalSuccessfulAttempts,
                 int totalFailureAttempts) {
        this.TrialNumber = trialNumber;
        this.TrialAttempts = trialAttempts;
        this.TrialType = trialType;
        this.TotalAttempts = totalAttempts;
        this.TotalSuccessfulAttempts = totalSuccessfulAttempts;
        this.TotalFailureAttempts = totalFailureAttempts;
    }

    public int getTrialNumber() {
        return TrialNumber;
    }

    public List<TrialAttempt> getTrialAttempts() {
        return TrialAttempts;
    }

    public void setTrialAttempts(List<TrialAttempt> trialAttempts) {
        TrialAttempts = trialAttempts;
    }

    public String getTrialType() {
        return TrialType;
    }

    @Override
    public int compareTo(Trial trial) {
        return (new Integer(this.getTrialNumber())).compareTo(trial.getTrialNumber());
    }

}
