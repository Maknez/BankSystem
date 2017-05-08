package bank.system;


import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import static bank.system.Account.*;


public class BankSystem implements Serializable {

    private Account     accountRef1;
    private Account     accountRef2;
    private Database    accountsDatabase;
    private String      databaseName;
    private Scanner     scnr;


    public BankSystem() {
        scnr = new Scanner(System.in);
        this.setDatabase();
        this.menu();
    }

    private void setDatabase() {
        Database.printDatabases();
        System.out.println("Bank management application - TUI Version\n");
        System.out.println("Enter (E) to load EXISTING database on server");
        System.out.println("Enter (N) to create NEW database on server");
        String choose = scnr.nextLine();
        if(choose.contains("e") || choose.contains("E")) {
            System.out.print("\nType name of EXISTING database: ");
            try {
                databaseName = scnr.nextLine();
                accountsDatabase = new Database(databaseName);
                accountsDatabase = accountsDatabase.load(databaseName);
            }
            catch(InputMismatchException e) {
                System.out.println("\nUnidentified operation: ");
                System.out.println("Type proper number of action.");
                setDatabase();
            } catch (IOException e) {
                System.out.println("\nDATABASE_LOAD_ERROR. Typed database DOESN'T EXIST or cannot be loaded properly.");
                System.out.println("Try again or create NEW database\n");
                setDatabase();
            }
        }
        else if(choose.contains("n") || choose.contains("N")) {
            System.out.print("\nType name of NEW database file you want to create: ");
            try {
                databaseName = scnr.nextLine();
                accountsDatabase = new Database(databaseName);
                accountsDatabase.saveToFile(databaseName);
            }
            catch(InputMismatchException e) {
                System.out.println("\nUnidentified operation: ");
                System.out.println("Type proper number of action.");
                setDatabase();
            }
        }
        else {
            setDatabase();
        }
    }

    private void setAccountRef1() {
        System.out.print("Insert ID of account: ");
        try {
            accountRef1 = accountsDatabase.get(scnr.nextBigDecimal());
            scnr.nextLine();
            if(accountRef1 == null) {
                throw new NoSuchAccountIDException();
            }
        }
        catch(InputMismatchException e) {
            scnr.nextLine();
            System.out.println("\nInvalid ID");
            System.out.println("ID contains only integer numbers.\n");
            setAccountRef1();
        }
        catch(NoSuchAccountIDException e) {
            System.out.println("\nInvalid ID");
            System.out.println("No such account in database.");
            setAccountRef1();
        }
    }

    private void setAccountRef2() {
        System.out.print("\nInsert ID of account where you want to transfer money: ");
        try {
            accountRef2 = accountsDatabase.get(scnr.nextBigDecimal());
            scnr.nextLine();
        }
        catch(InputMismatchException e) {
            System.out.println("\nInvalid ID to transfer.");
            System.out.println("ID contains only integer numbers.\n");
            setAccountRef1();
        }
    }

    private void menu() {
        int choice;
        do {
            printMenu();
            try {
                choice = scnr.nextInt();
                scnr.nextLine();
            }
            catch(InputMismatchException e) {
                scnr.nextLine();
                System.out.println("\nUnidentified operation. Choose from 1-8 to choose proper opearation");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addAccount(accountsDatabase);
                    choice = 1;
                    break;
                case 2:
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.removeAccount(accountsDatabase);
                    choice = 1;
                    break;
                case 3:
                    printSearchMenu();
                    searchMenu();
                    choice = 1;
                    break;
                case 4:
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.deposit();
                    choice = 1;
                    break;
                case 5:
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.withdraw();
                    choice = 1;
                    break;
                case 6:
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    setAccountRef2();
                    accountRef1.transfer(accountRef2);
                    choice = 1;
                    break;
                case 7:
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.displayAccountInformation();
                    choice = 1;
                    break;
                case 8:
                    accountsDatabase.printAllClients();
                    choice = 1;
                    break;
                case 0:
                    accountsDatabase.saveToFile(databaseName);
                    System.exit(0);
                case -1:
                    break;
                default:
                    System.out.println("\nUnidentified operation. Choose from 1-8 to choose proper opearation");
                    break;
            }
        }while(choice != 0);
    }

    private void searchMenu() {
        int choice;
        try {
            choice = scnr.nextInt();
            scnr.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("\nEnter a name: ");
                    String name = scnr.nextLine();
                    accountsDatabase.searchByName(name).printAllClients();
                    if (isContinued() == -1) {
                        menu();
                    }
                    break;
                case 2:
                    System.out.print("\nEnter a surname: ");
                    String surname = scnr.nextLine();
                    accountsDatabase.searchBySurname(surname).printAllClients();
                    if (isContinued() == -1) {
                        menu();
                    }
                    break;
                case 3:
                    System.out.print("\nEnter an address: ");
                    String address = scnr.nextLine();
                    accountsDatabase.searchByAddress(address).printAllClients();
                    isContinued();
                    break;
                case 4:
                    System.out.print("\nEnter ID: ");
                    BigDecimal ID = scnr.nextBigDecimal();
                    scnr.nextLine();
                    accountsDatabase.searchByID(ID).printAllClients();
                    if (isContinued() == -1) {
                        menu();
                    }
                    break;
                case 5:
                    System.out.print("\nEnter PESEL: ");
                    BigDecimal PESEL = scnr.nextBigDecimal();
                    scnr.nextLine();
                    accountsDatabase.searchByPESEL(PESEL).printAllClients();
                    if (isContinued() == -1) {
                        menu();
                    }
                    break;
                default:
                    System.out.println("\nUnidentified operation. Choose from 1-5 to choose proper searching number. ID, PESEL contains only integer numbers.");
                    printSearchMenu();
                    searchMenu();
                    break;
            }
        }
        catch(InputMismatchException e ) {
            System.out.println("\nUnidentified operation. Choose from 1-5 to choose proper searching number. ID, PESEL contains only integer numbers.");
            scnr.nextLine();
            printSearchMenu();
            searchMenu();
        }
    }

    private void printMenu() {
        System.out.println("\nBank management application - TUI Version\n");
        System.out.println("1. Add an account");
        System.out.println("2. Remove an account");
        System.out.println("3. Search an account");
        System.out.println("4. Deposit cash");
        System.out.println("5. Withdraw cash");
        System.out.println("6. Transfer cash");
        System.out.println("7. Display an account information");
        System.out.println("8. Print all clients in database");
        System.out.println("0. Exit application\n\n");
        System.out.print("Enter: ");
    }

    private void printSearchMenu() {
        System.out.println("\n\nSearching accountant\n");
        System.out.println("1. Search by name");
        System.out.println("2. Search by surname");
        System.out.println("3. Search by address");
        System.out.println("4. Search by ID");
        System.out.println("5. Search by PESEL\n\n");
        System.out.print("Enter: ");
    }

    private int isContinued() {
        String yesOrNo;
        System.out.print("\n\nDo you want to continue? (Y/N): ");
        yesOrNo = new String(scnr.nextLine());

        if (yesOrNo.equals("y") || yesOrNo.equals("Y")) {
            return -1;
        }
        else if (yesOrNo.equals("n") || yesOrNo.equals("N")) {
            accountsDatabase.saveToFile(databaseName);
            System.exit(0);
            return 0;
        }
        else {
            System.out.println("\nInproper value. Type 'Y' to continue or 'N' to quit.");
            return isContinued();
        }
    }

    private boolean isDataBaseEmpty() {
        if (accountsDatabase.getDatabase().isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}