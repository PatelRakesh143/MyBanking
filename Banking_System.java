import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class Account {
    private String accountNumber;
    private double balance;
    private double interestRate; // Annual interest rate (e.g., 0.05 for 5%)
    private List<Transaction> transactions; // Assuming you have a Transactions class

    public Account(String accountNumber,double interestRate) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.interestRate = interestRate;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Insufficient funds in the account.");
        }
         public void calculateInterest(int months) {
        double monthlyInterestRate = interestRate / 12; // Monthly interest rate
        for (int i = 0; i < months; i++) {
            double monthlyInterest = balance * monthlyInterestRate;
            balance += monthlyInterest;
            transactions.add(new Transaction("Interest Credit", monthlyInterest));
        }
    }
}

public class BankApplication {
    private static List<Account> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Bank Application Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display Account Information");
            System.out.println("5. displayMiniStatement");//display ministatment
            System.out.println("6.interest calculation ");//calculate interestrate
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        displayAccountInfo();
                        break;
                    case 5:
                        displayMiniStatement();
                        break;
                    case 6:
                        interestCalculation();
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = new Account(accountNumber);
        accounts.add(account);
        System.out.println("Account created successfully.");
    }

    private static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);

        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
            account.deposit(amount);
            System.out.println("Deposit successful.");
        }
    }

    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);

        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            try {
                account.withdraw(amount);
                System.out.println("Withdrawal successful.");
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void displayAccountInfo() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);

        if (account != null) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
        }
    }
    private static void displayMiniStatement() {
    System.out.print("Enter account number: ");
    String accountNumber = scanner.nextLine();
    Account account = findAccount(accountNumber);

    if (account != null) {
        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.println("Mini-Statement for Account Number: " + account.getAccountNumber());
            for (Transaction transaction : transactions) {
                System.out.println("Timestamp: " + transaction.getTimestamp());
                System.out.println("Description: " + transaction.getDescription());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println();
            }
        }
    }
private static void calculateInterest() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);

        if (account != null) {
            System.out.print("Enter the number of months to calculate interest: ");
            int months = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            account.calculateInterest(months);
            System.out.println("Interest calculation successful.");
        }
}


    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        System.out.println("Account not found.");
        return null;
    }
}
