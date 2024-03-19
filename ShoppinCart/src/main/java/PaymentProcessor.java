public class PaymentProcessor {
    // Simulate payment processing
    public static boolean processPayment(double amount) {
        // Here you can add any business logic related to payment processing, such as:
        // - Checking account balance
        // - Verifying credit card details
        // - Communicating with a payment gateway
        // For simplicity, let's assume the payment is always successful if the amount is greater than 0
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }

        // Assuming the payment is successful if the amount is greater than 0
        System.out.println("Processing payment of $" + amount);
        System.out.println("Payment successful.");
        return true;
    }
}
