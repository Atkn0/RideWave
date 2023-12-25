package org.example.Models;

public class userModel {
    private String email;
    private String password;
    private String cardNumber;
    private String balance;

    public userModel(String email, String password, String cardNumber, String balance) {
        this.email = email;
        this.password = password;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
