package BankingSystem;
public interface BankAccount {
    void deposit(double amount) throws InvalidAmountException;
    void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException;
    void checkBalance();
    void printTransactions();
}

