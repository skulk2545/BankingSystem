package BankingSystem;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankingSystemApp {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Map<String, Account> ACCOUNTS = new HashMap<>();

    public static void main(String[] args) {
        seedAccounts();

        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> handleDeposit();
                    case 2 -> handleWithdraw();
                    case 3 -> handleTransfer();
                    case 4 -> handleCheckBalance();
                    case 5 -> handlePrintTransactions();
                    case 6 -> handleAddInterest();
                    case 7 -> handleCreateAccount();
                    case 8 -> handleChangePin();
                    case 9 -> handleCloseAccount();
                    case 10 -> handleSearchAccount();
                    case 11 -> handleTotalBankBalance();
                    case 12 -> handleAccountStatement();
                    case 0 -> {
                        System.out.println("Thank you for using the Banking System. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("====== Banking System ======");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Check Balance");
        System.out.println("5. Print Transaction History");
        System.out.println("6. Add Interest (Savings Only)");
        System.out.println("7. Create New Account");
        System.out.println("8. Change PIN");
        System.out.println("9. Close Account");
        System.out.println("10. Search Account");
        System.out.println("11. Total Bank Balance");
        System.out.println("12. Account Statement (Date Range)");
        System.out.println("0. Exit");
        System.out.println("============================");
    }

    /* ----------------------- Handlers ----------------------- */

    private static void handleDeposit() throws AuthenticationException, InvalidAmountException {
        Account acc = authenticateAndGetAccount();
        double amount = readDouble("Enter amount to deposit: ");
        acc.deposit(amount);
    }

    private static void handleWithdraw() throws AuthenticationException, InvalidAmountException, InsufficientBalanceException {
        Account acc = authenticateAndGetAccount();
        double amount = readDouble("Enter amount to withdraw: ");
        acc.withdraw(amount);
    }

    private static void handleTransfer() throws AuthenticationException, InvalidAmountException, InsufficientBalanceException {
        Account from = authenticateAndGetAccount();
        String targetAccNum = readString("Enter target account number: ");
        Account to = ACCOUNTS.get(targetAccNum);

        if (to == null) {
            System.out.println("Target account not found.");
            return;
        }

        double amount = readDouble("Enter amount to transfer: ");
        from.transfer(to, amount);
    }

    private static void handleCheckBalance() throws AuthenticationException {
        Account acc = authenticateAndGetAccount();
        acc.checkBalance();
    }

    private static void handlePrintTransactions() throws AuthenticationException {
        Account acc = authenticateAndGetAccount();
        acc.printTransactions();
    }

    private static void handleAddInterest() throws AuthenticationException, InvalidAmountException {
        Account acc = authenticateAndGetAccount();
        if (acc instanceof SavingsAccount sa) {
            double rate = readDouble("Enter annual interest rate (%): ");
            sa.addInterest(rate);
        } else {
            System.out.println("Interest can only be added to Savings Accounts.");
        }
    }

    private static void handleChangePin() throws AuthenticationException {
        Account acc = authenticateAndGetAccount();
        int oldPin = readInt("Enter old PIN again: ");
        int newPin = readInt("Enter new PIN: ");
        try {
            acc.changePin(oldPin, newPin);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleCloseAccount() throws AuthenticationException {
        Account acc = authenticateAndGetAccount();
        ACCOUNTS.remove(acc.getAccountNumber());
        System.out.println("Account " + acc.getAccountNumber() + " closed successfully.");
    }

    private static void handleSearchAccount() {
        String keyword = readString("Enter account number or part of it: ");
        for (Account acc : ACCOUNTS.values()) {
            if (acc.getAccountNumber().contains(keyword)) {
                System.out.println("Found: " + acc.getAccountNumber());
            }
        }
    }

    private static void handleTotalBankBalance() {
        double total = 0;
        for (Account acc : ACCOUNTS.values()) {
            total += acc.balance;
        }
        System.out.println("Total Balance across all accounts: ₹" + total);
    }

    private static void handleAccountStatement() throws AuthenticationException {
        Account acc = authenticateAndGetAccount();
        String startStr = readString("Enter start date (yyyy-MM-dd): ");
        String endStr = readString("Enter end date (yyyy-MM-dd): ");
        LocalDateTime start = LocalDateTime.parse(startStr + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endStr + "T23:59:59");
        acc.printStatement(start, end);
    }

    private static void handleCreateAccount() {
        System.out.println("Choose Account Type:");
        System.out.println("1. Savings");
        System.out.println("2. Current");
        int type = readInt("Your choice: ");

        String accNum = readString("Enter new Account Number: ");
        if (ACCOUNTS.containsKey(accNum)) {
            System.out.println("Account number already exists!");
            return;
        }

        double openingBalance = readDouble("Enter opening balance: ");
        int pin = readInt("Set a 4-digit PIN: ");

        switch (type) {
            case 1 -> {
                ACCOUNTS.put(accNum, new SavingsAccount(accNum, openingBalance, pin));
                System.out.println("Savings Account created successfully!");
            }
            case 2 -> {
                double od = readDouble("Set overdraft limit: ");
                ACCOUNTS.put(accNum, new CurrentAccount(accNum, openingBalance, pin, od));
                System.out.println("Current Account created successfully!");
            }
            default -> System.out.println("Invalid account type selected.");
        }
    }

    /* -------------------- Helpers ------------------------ */

    private static Account authenticateAndGetAccount() throws AuthenticationException {
        String accNum = readString("Enter account number: ");
        Account acc = ACCOUNTS.get(accNum);
        if (acc == null) throw new AuthenticationException("Account not found.");

        int pin = readInt("Enter PIN: ");
        if (!acc.authenticate(pin)) throw new AuthenticationException("Invalid PIN.");
        return acc;
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    private static void seedAccounts() {
        ACCOUNTS.put("SA123", new SavingsAccount("SA123", 1000, 1111));
        ACCOUNTS.put("CA456", new CurrentAccount("CA456", 2000, 2222, 1000));
        System.out.println("Seeded demo accounts:");
        System.out.println(" - Savings:  SA123 | PIN: 1111 | Opening Balance: ₹1000.00");
        System.out.println(" - Current:  CA456 | PIN: 2222 | Opening Balance: ₹2000.00 | Overdraft: ₹1000.00\n");
    }
}
