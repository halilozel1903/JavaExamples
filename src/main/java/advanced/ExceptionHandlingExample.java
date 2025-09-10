package advanced;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Demonstrates comprehensive exception handling in Java including
 * try-catch blocks, custom exceptions, try-with-resources, and best practices.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class ExceptionHandlingExample {

    // Custom checked exception
    static class InsufficientFundsException extends Exception {
        private final double balance;
        private final double requestedAmount;

        public InsufficientFundsException(double balance, double requestedAmount) {
            super(String.format("Insufficient funds: Balance=%.2f, Requested=%.2f", 
                               balance, requestedAmount));
            this.balance = balance;
            this.requestedAmount = requestedAmount;
        }

        public double getBalance() { return balance; }
        public double getRequestedAmount() { return requestedAmount; }
    }

    // Custom unchecked exception
    static class InvalidAccountException extends RuntimeException {
        public InvalidAccountException(String accountNumber) {
            super("Invalid account number: " + accountNumber);
        }
    }

    // Bank account class demonstrating exception handling
    static class BankAccount {
        private final String accountNumber;
        private double balance;
        private boolean isActive;

        public BankAccount(String accountNumber, double initialBalance) {
            if (accountNumber == null || accountNumber.trim().isEmpty()) {
                throw new InvalidAccountException("Account number cannot be null or empty");
            }
            if (initialBalance < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative");
            }
            
            this.accountNumber = accountNumber;
            this.balance = initialBalance;
            this.isActive = true;
        }

        public void withdraw(double amount) throws InsufficientFundsException {
            validateAccount();
            validateAmount(amount);
            
            if (amount > balance) {
                throw new InsufficientFundsException(balance, amount);
            }
            
            balance -= amount;
            System.out.printf("Withdrawn $%.2f. New balance: $%.2f%n", amount, balance);
        }

        public void deposit(double amount) {
            validateAccount();
            validateAmount(amount);
            
            balance += amount;
            System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
        }

        private void validateAccount() {
            if (!isActive) {
                throw new IllegalStateException("Account is closed");
            }
        }

        private void validateAmount(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            if (Double.isNaN(amount) || Double.isInfinite(amount)) {
                throw new IllegalArgumentException("Invalid amount: " + amount);
            }
        }

        public double getBalance() { return balance; }
        public String getAccountNumber() { return accountNumber; }
        public void closeAccount() { this.isActive = false; }
    }

    public static void main(String[] args) {
        demonstrateBasicExceptionHandling();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateCustomExceptions();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateTryWithResources();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateMultipleCatchBlocks();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateExceptionChaining();
    }

    /**
     * Demonstrates basic exception handling with try-catch-finally
     */
    private static void demonstrateBasicExceptionHandling() {
        System.out.println("=== BASIC EXCEPTION HANDLING ===");

        // Array index exception
        int[] numbers = {1, 2, 3, 4, 5};
        
        try {
            System.out.println("Accessing valid index: " + numbers[2]);
            System.out.println("Accessing invalid index: " + numbers[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array index error: " + e.getMessage());
        } finally {
            System.out.println("Finally block always executes");
        }

        // Number format exception
        String[] numberStrings = {"123", "456", "abc", "789"};
        
        for (String numberString : numberStrings) {
            try {
                int number = Integer.parseInt(numberString);
                System.out.println("Parsed number: " + number);
            } catch (NumberFormatException e) {
                System.err.println("Cannot parse '" + numberString + "' as integer");
            }
        }

        // Null pointer exception
        String nullString = null;
        try {
            System.out.println("Length: " + nullString.length());
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        }
    }

    /**
     * Demonstrates custom exception usage
     */
    private static void demonstrateCustomExceptions() {
        System.out.println("=== CUSTOM EXCEPTIONS ===");

        try {
            // Test invalid account creation
            var invalidAccount = new BankAccount("", 100);
        } catch (InvalidAccountException e) {
            System.err.println("Account creation failed: " + e.getMessage());
        }

        try {
            // Test negative initial balance
            var negativeAccount = new BankAccount("ACC123", -50);
        } catch (IllegalArgumentException e) {
            System.err.println("Account creation failed: " + e.getMessage());
        }

        // Create valid account and test operations
        try {
            var account = new BankAccount("ACC001", 1000.0);
            
            // Valid operations
            account.deposit(500.0);
            account.withdraw(200.0);
            
            // This should throw InsufficientFundsException
            account.withdraw(2000.0);
            
        } catch (InsufficientFundsException e) {
            System.err.println("Transaction failed: " + e.getMessage());
            System.err.printf("Available: $%.2f, Attempted: $%.2f%n", 
                            e.getBalance(), e.getRequestedAmount());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        // Test closed account
        try {
            var account = new BankAccount("ACC002", 500.0);
            account.closeAccount();
            account.deposit(100.0); // Should throw IllegalStateException
        } catch (IllegalStateException e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
    }

    /**
     * Demonstrates try-with-resources for automatic resource management
     */
    private static void demonstrateTryWithResources() {
        System.out.println("=== TRY-WITH-RESOURCES ===");

        String fileName = "/tmp/test-file.txt";
        String content = "Hello, World!\nThis is a test file.\nJava Exception Handling Demo.";

        // Writing to file using try-with-resources
        try (FileWriter writer = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            
            bufferedWriter.write(content);
            System.out.println("File written successfully");
            
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }

        // Reading from file using try-with-resources
        try (FileReader reader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            
            System.out.println("File contents:");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("  " + line);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Using modern NIO.2 APIs with try-with-resources
        try {
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);
            System.out.println("Lines read using NIO.2:");
            lines.forEach(line -> System.out.println("  " + line));
            
            // Clean up
            Files.deleteIfExists(path);
            
        } catch (IOException e) {
            System.err.println("Error with NIO.2: " + e.getMessage());
        }
    }

    /**
     * Demonstrates multiple catch blocks and exception hierarchy
     */
    private static void demonstrateMultipleCatchBlocks() {
        System.out.println("=== MULTIPLE CATCH BLOCKS ===");

        String[] operations = {"divide", "parse", "array", "null"};
        
        for (String operation : operations) {
            try {
                switch (operation) {
                    case "divide" -> {
                        int result = 10 / 0; // ArithmeticException
                        System.out.println("Division result: " + result);
                    }
                    case "parse" -> {
                        int number = Integer.parseInt("not-a-number"); // NumberFormatException
                        System.out.println("Parsed number: " + number);
                    }
                    case "array" -> {
                        int[] arr = new int[5];
                        System.out.println("Array element: " + arr[10]); // ArrayIndexOutOfBoundsException
                    }
                    case "null" -> {
                        String str = null;
                        System.out.println("String length: " + str.length()); // NullPointerException
                    }
                }
            } catch (ArithmeticException e) {
                System.err.println("Arithmetic error in " + operation + ": " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Number format error in " + operation + ": " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Array index error in " + operation + ": " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("Null pointer error in " + operation + ": " + e.getMessage());
            } catch (Exception e) {
                // Catch-all for any other exceptions
                System.err.println("Unexpected error in " + operation + ": " + e.getClass().getSimpleName());
            }
        }

        // Modern multi-catch syntax (Java 7+)
        for (int i = 0; i < 3; i++) {
            try {
                switch (ThreadLocalRandom.current().nextInt(3)) {
                    case 0 -> throw new IllegalArgumentException("Random illegal argument");
                    case 1 -> throw new IllegalStateException("Random illegal state");
                    case 2 -> throw new UnsupportedOperationException("Random unsupported operation");
                }
            } catch (IllegalArgumentException | IllegalStateException | UnsupportedOperationException e) {
                System.err.println("Caught multiple exception types: " + e.getClass().getSimpleName());
            }
        }
    }

    /**
     * Demonstrates exception chaining and cause tracking
     */
    private static void demonstrateExceptionChaining() {
        System.out.println("=== EXCEPTION CHAINING ===");

        try {
            performComplexOperation();
        } catch (Exception e) {
            System.err.println("Top-level exception: " + e.getMessage());
            
            // Print the complete exception chain
            Throwable cause = e.getCause();
            int level = 1;
            while (cause != null) {
                System.err.println("  Caused by (level " + level + "): " + 
                                 cause.getClass().getSimpleName() + " - " + cause.getMessage());
                cause = cause.getCause();
                level++;
            }
            
            // Print full stack trace
            System.err.println("\nFull stack trace:");
            e.printStackTrace();
        }
    }

    /**
     * Helper method that demonstrates exception chaining
     */
    private static void performComplexOperation() throws Exception {
        try {
            performDatabaseOperation();
        } catch (RuntimeException e) {
            throw new Exception("Complex operation failed", e);
        }
    }

    private static void performDatabaseOperation() {
        try {
            performNetworkOperation();
        } catch (IOException e) {
            throw new RuntimeException("Database operation failed", e);
        }
    }

    private static void performNetworkOperation() throws IOException {
        throw new IOException("Network connection timeout");
    }
}