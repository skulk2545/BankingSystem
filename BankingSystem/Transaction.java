package BankingSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final LocalDateTime timestamp;
    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final String description;

    public Transaction(LocalDateTime timestamp, String type, double amount, double balanceAfter, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-14s ₹%-10.2f Balance: ₹%-10.2f | %s",
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                type, amount, balanceAfter, description);
    }
}
