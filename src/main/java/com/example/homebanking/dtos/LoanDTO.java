package com.example.homebanking.dtos;

import com.example.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
    private String name;
    private double maxAmount;
    private List<Integer> payments = new ArrayList<>();
    private double percentage;

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.percentage = loan.getPercentage();
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public double getPercentage(){
        return percentage;
    }
}
