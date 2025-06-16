
import java.util.Scanner;

public class ATM {
    private UserAccount user;

    public ATM(UserAccount user) {
        this.user = user;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n========= ATM Menu =========");
            System.out.println("1. 💰 Check Balance");
            System.out.println("2. ➕ Deposit");
            System.out.println("3. ➖ Withdraw");
            System.out.println("4. 📄 Transaction History");
            System.out.println("5. 🔒 Change PIN");
            System.out.println("6. 🚪 Exit");
            System.out.print("Enter your choice (1-6): ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("💳 Your Current Balance: ₹" + user.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ₹");
                    double depositAmt = sc.nextDouble();
                    user.deposit(depositAmt);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdrawAmt = sc.nextDouble();
                    user.withdraw(withdrawAmt);
                    break;
                case 4:
                    user.showTransactions();
                    break;
                case 5:
                    System.out.print("Enter new 4-digit PIN: ");
                    int newPin = sc.nextInt();
                    user.changePin(newPin);
                    break;
                case 6:
                    System.out.println("👋 Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
