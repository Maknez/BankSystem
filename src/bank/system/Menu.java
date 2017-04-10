package bank.system;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    public void showTheMenu() {

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("1. Create an account.");
        System.out.println("2. Delete an account.");
        System.out.println("3. Deposit some money.");
        System.out.println("4. Withdraw some money.");
        System.out.println("5. Transfer some money.");
        System.out.println("6. Check information about an account.");
        System.out.println("7. Check information about all clients.");
        System.out.println("8. Exit the application.");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("\n");
        System.out.println("Choose from 1 to 8 which operation would you like to make.");

    }

    public void showSearchMenu() {

        System.out.println("\n\nSearching accountant\n");
        System.out.println("1. Search by name");
        System.out.println("2. Search by surname");
        System.out.println("4. Search by login");
        System.out.println("5. Search by pesel\n\n");
        System.out.print("Your choice: ");

    }

    public int returnTheOperation() {
        try {
            System.out.print("Your choice: ");
            return scanner.nextInt();

        }

        catch (InputMismatchException e) {

            System.out.println("You have just put wrong operation.");
            showTheMenu();
            return returnTheOperation();
        }
    }

    public int continueTheApplication() {

        System.out.println("Would you like to continue (Y/N)?");
        System.out.print("Your choice: ");
        String choice = new String(scanner.nextLine());

        if (choice.equals("Y") || choice.equals("y")) {

            return 1;

        }

        else if (choice.equals("N") || choice.equals("n")) {

            return 0;

        }

        else {

            System.out.println("You have just put wrong value. Press Y to continue or N to leave the application");
            return continueTheApplication();

        }

    }

}