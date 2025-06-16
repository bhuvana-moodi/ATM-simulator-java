
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;



public class UserAccount {
    private String cardNumber;
    private int pin;
    private double balance;

    // Constructor
    public UserAccount(String cardNumber, int pin, double balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    // Retrieve user from database
    public static UserAccount getUser(String cardNumber) {
        try (Connection con = Database.getConnection()) {
            String sql = "SELECT * FROM users WHERE card_number = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int pin = rs.getInt("pin");
                double balance = rs.getDouble("balance");
                return new UserAccount(cardNumber, pin, balance);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    // PIN authentication
    public boolean authenticate(String inputCard, int inputPin) {
        return this.cardNumber.equals(inputCard) && this.pin == inputPin;
    }

    // Deposit
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid deposit amount.");
            return;
        }
        balance += amount;
        updateBalance();
        addTransaction("Deposited ₹" + amount);
        System.out.println("✅ ₹" + amount + " deposited.");
    }

    // Withdraw
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid withdrawal amount.");
            return;
        }
        if (amount > balance) {
            System.out.println("❌ Insufficient balance.");
            return;
        }
        balance -= amount;
        updateBalance();
        addTransaction("Withdrew ₹" + amount);
        System.out.println("✅ ₹" + amount + " withdrawn.");
    }

    // Balance update in DB
    private void updateBalance() {
        try (Connection con = Database.getConnection()) {
            String sql = "UPDATE users SET balance = ? WHERE card_number = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, balance);
            stmt.setString(2, cardNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

    // Transaction logging
    public void addTransaction(String description) {
        try (Connection con = Database.getConnection()) {
            String sql = "INSERT INTO transactions (card_number, description) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            stmt.setString(2, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error recording transaction: " + e.getMessage());
        }
    }

    // Show transaction history
    public void showTransactions() {
        try (Connection con = Database.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE card_number = ? ORDER BY date DESC";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n📜 Transaction History:");
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("date");
                String desc = rs.getString("description");
                System.out.println(ts + ": " + desc);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
        }
    }

    // PIN change
    public void changePin(int newPin) {
        this.pin = newPin;
        try (Connection con = Database.getConnection()) {
            String sql = "UPDATE users SET pin = ? WHERE card_number = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, newPin);
            stmt.setString(2, cardNumber);
            stmt.executeUpdate();
            System.out.println("✅ PIN updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating PIN: " + e.getMessage());
        }
    }

    // Get current balance
    public double getBalance() {
        return balance;
    }

    // Get card number
    public String getCardNumber() {
        return cardNumber;
    }
}
