package com.example.homebanking.dtos;

import com.example.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long id;
    private long loan_id;
    private String loan_name;
    private double amount;
    private int payments;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loan_id = clientLoan.getLoan().getId();
        this.loan_name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }

    public long getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(long loan_id) {
        this.loan_id = loan_id;
    }

    public String getLoan_name() {
        return loan_name;
    }

    public void setLoan_name(String loan_name) {
        this.loan_name = loan_name;
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
}
