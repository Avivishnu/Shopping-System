import java.util.List;
import java.util.Scanner;

public class ShoppingSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserAuthenticationService authService = UserAuthenticationService.getInstance();
    private static String currentUser = null; // Tracks the currently logged-in user

    public static void main(String[] args) {
        boolean running = true;

        List<Product> products = ProductLoader.loadProducts("C:\\Users\\Chandrashekar Goud\\Downloads\\projectshop\\basicShoppinCart\\ShoppinCart\\src\\main\\resources\\products");

        System.out.println("Welcome to the Shopping System");
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int authChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (authChoice == 1) {
                if (login()) {
                    break; // Exit the loop if login is successful
                }
            } else if (authChoice == 2) {
                register();
                // Optionally, you can auto-login users after registration by calling login() here
            } else if (authChoice == 3) {
                System.out.println("Exiting Shopping System.");
                return; // Exit the application
            } else {
                System.out.println("Invalid choice.");
            }
        }

        ShoppingCart cart = new ShoppingCart(); // Create a new shopping cart

        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Available Products:");
                    for (Product product : products) {
                        System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice());
                    }
                    break;
                case 2:
                    // Add product to cart
                    addProductToCart(products, cart);
                    break;
                case 3:
                    viewCartItems(cart);
                    break;
                case 4:
                    checkout(cart);
                    break;
                case 5:
                    logout();
                    running = false; // Optionally, ask the user if they want to log in again instead of closing the application
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authService.loginUser(username, password)) {
            currentUser = username; // Set the current user
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Login failed. If you are a new user, please register.");
            return false;
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authService.registerUser(username, password)) {
            System.out.println("Registration successful. Please log in.");
        } else {
            System.out.println("Registration failed. User might already exist.");
        }
    }

    private static void addProductToCart(List<Product> products, ShoppingCart cart) {
        System.out.println("Enter product ID to add to cart: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Find the product with the specified ID
        Product selectedProduct = null;
        for (Product product : products) {
            if (product.getId() == productId) {
                selectedProduct = product;
                break;
            }
        }

        // If the product is found, add it to the cart
        if (selectedProduct != null) {
            cart.addItem(selectedProduct, quantity);
            System.out.println("Product '" + selectedProduct.getName() + "' (ID: " + selectedProduct.getId() +
                    ") added to cart. Quantity: " + quantity);
        } else {
            System.out.println("Invalid product ID.");
        }
    }


    private static void viewCartItems(ShoppingCart cart) {
        System.out.println("Cart Items:");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getProduct().getName() + " - Quantity: " + item.getQuantity());
        }
    }

    private static void checkout(ShoppingCart cart) {
        double totalPrice = cart.getTotalPrice();

        // Process payment
        boolean paymentSuccessful = PaymentProcessor.processPayment(totalPrice);

        if (paymentSuccessful) {
            System.out.println("Checking out. Total: $" + totalPrice);
            cart.clear(); // Clear the cart after successful checkout
            System.out.println("Checkout complete. Thank you for your purchase!");
        } else {
            System.out.println("Payment processing failed. Please try again.");
        }
    }


    private static void logout() {
        currentUser = null; // Clear the current user
        System.out.println("You have been logged out.");
        // Here you could loop back to the start to allow another user to log in or exit.
    }
}
