package BankingSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Account implements BankAccount {
    protected final String accountNumber;
    protected double balance;
    private final int pin;
    protected final List<Transaction> transactions = new ArrayList<>();

    protected Account(String accountNumber, double balance, int pin) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
        addTransaction("ACCOUNT_OPEN", 0, "Account opened with initial balance ₹" + balance);
    }

    public boolean authenticate(int pin) {
        return this.pin == pin;
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Amount must be positive.");
        balance += amount;
        System.out.println("Deposited ₹" + amount + ". New Balance: ₹" + balance);
        addTransaction("DEPOSIT", amount, "Cash deposit");
    }

    @Override
    public void checkBalance() {
        System.out.println("Account Number: " + accountNumber + " | Balance: ₹" + balance);
    }

    @Override
    public void printTransactions() {
        System.out.println("\n--- Transaction History for " + accountNumber + " ---");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
        System.out.println("-----------------------------------------------\n");
    }

    public void transfer(Account target, double amount)
            throws InvalidAmountException, InsufficientBalanceException {
        if (target == null) throw new IllegalArgumentException("Target account cannot be null.");
        if (this == target) throw new IllegalArgumentException("Cannot transfer to the same account.");

        this.withdraw(amount);
        target.deposit(amount);

        addTransaction("TRANSFER_OUT", amount, "Transferred to " + target.accountNumber);
        target.addTransaction("TRANSFER_IN", amount, "Received from " + this.accountNumber);

        System.out.println("Transferred ₹" + amount + " to Account: " + target.accountNumber);
    }

    protected void addTransaction(String type, double amount, String description) {
        transactions.add(new Transaction(LocalDateTime.now(), type, amount, balance, description));
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public abstract void withdraw(double amount)
            throws InvalidAmountException, InsufficientBalanceException;
}
