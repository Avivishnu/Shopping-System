
 Shopping System

This is a simple shopping system application that allows users to browse products, add them to their cart, view their cart, and checkout. Users can also log in to their accounts to access their saved carts.

 Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) version 8 or higher
- Maven (optional, if you want to build the project from source)

 Getting Started

1. Clone this repository to your local machine:

   ```
   git clone https://github.com/your-username/shopping-system.git
   ```

2. Navigate to the project directory:

   ```
   cd shopping-system
   ```

3. Compile the source code:

   If you have Maven installed:

   ```
   mvn compile
   ```

   If you don't have Maven installed, you can compile the Java files manually using `javac`.

 Running the Application

1. Ensure you are in the project directory.

2. Run the main class `ShoppingSystem`:

   If you have Maven installed:

   ```
   mvn exec:java -Dexec.mainClass="com.example.shopping.ShoppingSystem"
   ```

   If you don't have Maven installed, you can run the compiled Java class directly:

   ```
   java -classpath target/classes com.example.shopping.ShoppingSystem
   ```

3. Follow the on-screen prompts to interact with the application.

 Usage

- Choose "Login" to log in to your account or "Register" to create a new account.
- Once logged in, you can view available products, add products to your cart, view your cart, and proceed to checkout.
- After checking out, you can log out to end your session.

 Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, please open an issue or submit a pull request.

