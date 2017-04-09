package bank.system;

import java.io.Serializable;
import java.util.Scanner;

public class BankSystem implements Serializable {

    private Account firstAccount;
    private Account secondAccount;
    private Database accountsDatabase;
    private String databaseName;
    private Scanner scanner;

    public BankSystem() {

        this.scanner = new Scanner(System.in);
        this.setDatabase();
    }

    private void setDatabase() {

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

}
