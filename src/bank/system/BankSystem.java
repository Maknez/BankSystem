package bank.system;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import static bank.system.Account.*;

public class BankSystem implements Serializable {

    private Account accountRef1;
    private Account accountRef2;
    private Database accountsDatabase;
    private String databaseName;
    private Scanner scnr;

    public BankSystem() {
        scnr = new Scanner(System.in);
        this.setDatabase();
        this.menu();
    }
    private void setDatabase() {
        Database.printDatabases();
        System.out.println("Bank application\n");
        System.out.println("Enter (L) to LOAD EXISTING database");
        System.out.println("Enter (C) to CREATE NEW database");
        String choose = scnr.nextLine();
        if(choose.equals("L") || choose.equals("l")) {
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
        else if(choose.contains("c") || choose.contains("C")) {
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
                    System.out.println("\n\n\n\n\n");
                    addAccount(accountsDatabase);
                    choice = 1;
                    break;
                case 2:
                    System.out.println("\n\n\n\n\n");
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.removeAccount(accountsDatabase);
                    choice = 1;
                    break;
                case 3:
                    System.out.println("\n\n\n\n\n");
                    printSearchMenu();
                    searchMenu();
                    choice = 1;
                    break;
                case 4:
                    System.out.println("\n\n\n\n\n");
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.deposit();
                    choice = 1;
                    break;
                case 5:
                    System.out.println("\n\n\n\n\n");
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.withdraw();
                    choice = 1;
                    break;
                case 6:
                    System.out.println("\n\n\n\n\n");
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    do {
                        setAccountRef1();
                        setAccountRef2();
                        if (accountRef1 == accountRef2) {
                            System.out.println("You cannot send money to yourself. Try again");
                        }
                    }   while (accountRef1 == accountRef2);
                    accountRef1.transfer(accountRef2);
                    choice = 1;
                    break;
                case 7:
                    System.out.println("\n\n\n\n\n");
                    if(isDataBaseEmpty()) {
                        System.out.println("\nDatabase is EMPTY.\n");
                        menu();
                    }
                    setAccountRef1();
                    accountRef1.displayAccountInformation();
                    choice = 1;
                    break;
                case 8:
                    System.out.println("\n\n\n\n\n");
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
            accountsDatabase.saveToFile(databaseName);
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
                    menu();
                    break;
                case 2:
                    System.out.print("\nEnter a surname: ");
                    String surname = scnr.nextLine();
                    accountsDatabase.searchBySurname(surname).printAllClients();
                    menu();
                    break;
                case 3:
                    System.out.print("\nEnter an address: ");
                    String address = scnr.nextLine();
                    accountsDatabase.searchByAddress(address).printAllClients();
                    break;
                case 4:
                    System.out.print("\nEnter ID: ");
                    BigDecimal ID = scnr.nextBigDecimal();
                    scnr.nextLine();
                    accountsDatabase.searchByID(ID).printAllClients();
                    menu();
                    break;
                case 5:
                    System.out.print("\nEnter PESEL: ");
                    BigDecimal PESEL = scnr.nextBigDecimal();
                    scnr.nextLine();
                    accountsDatabase.searchByPESEL(PESEL).printAllClients();
                    menu();
                    break;
                default:
                    System.out.println("\nBAD OPERATION. Choose from 1-5 to choose proper searching number. ID and PESEL contains only integer numbers.");
                    printSearchMenu();
                    searchMenu();
                    break;
            }
        }
        catch(InputMismatchException e ) {
            System.out.println("\nBAD OPERATION. Choose from 1-5 to choose proper searching number. ID and PESEL contains only integer numbers.");
            scnr.nextLine();
            printSearchMenu();
            searchMenu();
        }
    }
    private void printMenu() {
        System.out.println("\n\n\n\n\n");
        System.out.println("------------------------");
        System.out.println("----Bank application----");
        System.out.println("------------------------\n");
        System.out.println("1. ADD an account");
        System.out.println("2. REMOVE an account");
        System.out.println("3. SEARCH an account");
        System.out.println("4. DEPOSIT cash");
        System.out.println("5. WITHDRAW cash");
        System.out.println("6. TRANSFER cash");
        System.out.println("7. PRINT an account information");
        System.out.println("8. PRINT all clients in database");
        System.out.println("0. EXIT application\n\n");
        System.out.print("Enter: ");
    }
    private void printSearchMenu() {
        System.out.println("\n\n\n\n");
        System.out.println("\n\nSearching accountant\n");
        System.out.println("1. Search by NAME");
        System.out.println("2. Search by SURNAME");
        System.out.println("3. Search by ADRESS");
        System.out.println("4. Search by ID");
        System.out.println("5. Search by PESEL\n\n");
        System.out.print("Enter: ");
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