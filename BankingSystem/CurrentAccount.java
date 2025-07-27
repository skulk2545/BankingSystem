package BankingSystem;

public class CurrentAccount extends Account {
    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, double balance, int pin, double overdraftLimit) {
        super(accountNumber, balance, pin);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) throw new InvalidAmountException("Amount must be positive.");
        if (balance + overdraftLimit < amount) {
            throw new InsufficientBalanceException(
                "Overdraft limit exceeded! Available (including overdraft): ₹" + (balance + overdraftLimit));
        }
        balance -= amount;
        System.out.println("Withdrawn ₹" + amount + " from Current. Remaining Balance: ₹" + balance);
        addTransaction("WITHDRAW", amount, "Current withdrawal (overdraft allowed up to ₹" + overdraftLimit + ")");
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
