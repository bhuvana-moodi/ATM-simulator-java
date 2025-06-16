import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🏧 Welcome to the ATM Simulator");
        System.out.print("Enter your Card Number: ");
        String cardNumber = scanner.nextLine();

        UserAccount user = UserAccount.getUser(cardNumber);
        if (user == null) {
            System.out.println("❌ Card number not found.");
            return;
        }

        System.out.print("Enter your 4-digit PIN: ");
        int pin = scanner.nextInt();

        if (user.authenticate(cardNumber, pin)) {
            System.out.println("✅ Login successful!");
            ATM atm = new ATM(user);
            atm.start();
        } else {
            System.out.println("❌ Incorrect PIN.");
        }

        scanner.close();
    }
}
