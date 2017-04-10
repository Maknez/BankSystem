package bank.system;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;

import static bank.system.Account.createNewAccount;

public class BankSystem implements Serializable {

    private Account firstAccount;
    private Account secondAccount;
    protected Database accountsDatabase;
    private String databaseName;
    private Scanner scanner;

    Menu menu = new Menu();

    public BankSystem() throws IOException {

        this.scanner = new Scanner(System.in);
        this.setDatabase();

    }

    private void setDatabase () throws IOException {

        System.out.println("Bank management application - TUI Version\n");
        System.out.println("Enter (L) to load EXISTING database on server");
        System.out.println("Enter (C) to create NEW database on server");
        String choose = scanner.nextLine();

        if (choose.contains("l") || choose.contains("L")) {

            System.out.print("\nType name of EXISTING database: ");

            databaseName = scanner.nextLine();
            accountsDatabase = new Database(databaseName);
            accountsDatabase = accountsDatabase.load(databaseName);

        }

        else if (choose.contains("c") || choose.contains("C")) {

            System.out.print("\nType name of NEW database file you want to create: ");

            databaseName = scanner.nextLine();
            accountsDatabase = new Database(databaseName);
            accountsDatabase.saveToFile(databaseName);

        }

        else {

            setDatabase();

        }

    }

    private void setFirstAccount() {

        System.out.print("Insert ID of account: ");

        try {

            firstAccount = accountsDatabase.get(scanner.nextBigDecimal());
            scanner.nextLine();

        }

        catch(InputMismatchException e) {

            scanner.nextLine();
            System.out.println("\nInvalid ID");
            System.out.println("ID contains only integer numbers.\n");
            setFirstAccount();

        }

    }

    private void setSecondAccount() {
        System.out.print("\nInsert ID of account where you want to transfer money: ");
        try {
            secondAccount = accountsDatabase.get(scanner.nextBigDecimal());
            scanner.nextLine();
        }
        catch(InputMismatchException e) {
            System.out.println("\nInvalid ID to transfer.");
            System.out.println("ID contains only integer numbers.\n");
            setSecondAccount();
        }
    }



    public void startApplication()   {

        int choice;
        int contiOrAbort = 1;
        do {
            menu.showTheMenu();

            try {
                choice = menu.returnTheOperation();
                scanner.nextLine();
            }
            catch(InputMismatchException e) {
                scanner.nextLine();
                System.out.println("You have just put wrong number of operation. Choose from 1 to 8.");
                choice = 9;
            }
            try {
                switch (choice) {

                    case 1:
                        createNewAccount(accountsDatabase);
                        contiOrAbort = menu.continueTheApplication();
                        break;

                    case 2:
                        setFirstAccount();
                        firstAccount.removeTheAccount(accountsDatabase);
                        contiOrAbort = menu.continueTheApplication();
                        break;

                    case 3:
                        firstAccount.depositCash();
                        contiOrAbort = menu.continueTheApplication();
                        break;

                    case 4:
                        firstAccount.withdrawCash();
                        contiOrAbort = menu.continueTheApplication();
                        break;

                    case 5:
                        setFirstAccount();
                        setSecondAccount();
                        firstAccount.transferCash(secondAccount);
                        contiOrAbort = menu.continueTheApplication();
                        break;

                    case 6:
                        setFirstAccount();
                        firstAccount.showOneClient();
                        contiOrAbort = menu.continueTheApplication();
                        break;

                    case 7:
                        accountsDatabase.printAllClients();
                        contiOrAbort = menu.continueTheApplication();
                        break;

                    case 8:
                        accountsDatabase.saveToFile(databaseName);
                        System.exit(0);
                        break;

                    case 9:
                        break;

                    default:
                        System.out.println("You have just put wrong number of operation. Choose from 1 to 8.");
                        menu.showTheMenu();
                        break;
                }
            }
            catch (IOException e) {
                System.out.println("Oho! Something has gone wrong :(");
                startApplication();
            }

        } while (contiOrAbort == 1);

    }

}
