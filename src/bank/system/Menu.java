package bank.system;


public class Menu extends Account{

    public void showTheMenu() {

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("-------------------------------Welcome-------------------------------------");
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
/*
    public int returnTheOperation() {

        return scanner.nextInt();
    }*/
}