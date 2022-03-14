package com.example.homebanking.dtos;

import org.springframework.http.ResponseEntity;

public class LoanApplicationDTO {

    private String name;
    private double amount;
    private int payments;
    private String number;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(String name,double amount, int payments, String number) {
        this.name = name;
        this.amount = amount;
        this.payments = payments;
        this.number = number;
    }

    public LoanApplicationDTO(String name, double amount, int payments){
        this.name = name;
        this.amount = amount;
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
