package BankingSystem;
interface BankAccount {
    void deposit(double amount);
    void withdraw(double amount);
    void checkBalance();
}

abstract class Account implements BankAccount {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited ₹" + amount + ". New Balance: ₹" + balance);
    }

    public void checkBalance() {
        System.out.println("Account Number: " + accountNumber + " | Balance: ₹" + balance);
    }

    public abstract void withdraw(double amount);
}

class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public void withdraw(double amount) {
        if (balance - amount >= 500) {
            balance -= amount;
            System.out.println("Withdrawn Rs" + amount + " from Savings. Remaining Balance: RS" + balance);
        } else {
            System.out.println("Cannot withdraw Rs" + amount + " from Savings. Minimum balance of Rs500 required.");
        }
    }
}

class CurrentAccount extends Account {
    public CurrentAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public void withdraw(double amount) {
        balance -= amount;
        System.out.println("Withdrawn RS" + amount + " from Current. Remaining Balance: RS" + balance);
    }
}

public class data {
    public static void main(String[] args) {
        SavingsAccount savings = new SavingsAccount("SA123", 1000);
        CurrentAccount current = new CurrentAccount("CA456", 2000);

        System.out.println(" Savings Account Operations");
        savings.deposit(500);
        savings.withdraw(700);
        savings.withdraw(500);
        savings.checkBalance();

        System.out.println(" Current Account Operations");
        current.deposit(1000);
        current.withdraw(3500);
        current.checkBalance();
    }
}
