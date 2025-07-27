package BankingSystem;

public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500.0;

    public SavingsAccount(String accountNumber, double balance, int pin) {
        super(accountNumber, balance, pin);
        if (balance < MIN_BALANCE) {
            System.out.println("Warning: Opening balance is below the minimum required ₹" + MIN_BALANCE);
        }
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) throw new InvalidAmountException("Amount must be positive.");
        if (balance - amount < MIN_BALANCE) {
            throw new InsufficientBalanceException(
                "Cannot withdraw ₹" + amount + ". Minimum balance of ₹" + MIN_BALANCE + " must be maintained.");
        }
        balance -= amount;
        System.out.println("Withdrawn ₹" + amount + " from Savings. Remaining Balance: ₹" + balance);
        addTransaction("WITHDRAW", amount, "Savings withdrawal");
    }

    public void addInterest(double annualRatePercent) throws InvalidAmountException {
        if (annualRatePercent < 0) throw new InvalidAmountException("Interest rate cannot be negative.");
        double interest = balance * annualRatePercent / 100.0;
        balance += interest;
        System.out.println("Interest of ₹" + interest + " added. New Balance: ₹" + balance);
        addTransaction("INTEREST", interest, "Interest added at " + annualRatePercent + "%");
    }
}

