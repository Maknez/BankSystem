package bank.system;

import java.io.*;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Account implements Serializable {

    private String name;
    private String surname;
    private BigDecimal PESEL;
    private String address;
    private BigDecimal ID;
    private double balance;
    private static Scanner scnr;

    static {
        scnr = new Scanner(System.in);
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public BigDecimal getPESEL() {
        return PESEL;
    }
    public String getAddress() {
        return address;
    }
    public BigDecimal getID() {
        return ID;
    }
    public double getBalance() {
        return balance;
    }
    public Account(String name, String surname, BigDecimal PESEL, String address, BigDecimal ID) {
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        this.address = address;
        this.ID = ID;
    }
    public static void addAccount(Database database) {
        String name;
        String surname;
        BigDecimal PESEL;
        String address;
        BigDecimal ID;

        try {
            System.out.print("Insert clients name: ");
            name = scnr.nextLine();
            System.out.print("Insert clients surname: ");
            surname = scnr.nextLine();
            System.out.print("Insert clients PESEL number: ");
            PESEL = scnr.nextBigDecimal();
            scnr.nextLine();
            System.out.print("Insert clients address: ");
            address = scnr.nextLine();
            System.out.print("Adding original and unique clients ID number: ");
            ID = scnr.nextBigDecimal();
            scnr.nextLine();
            try {
                database.isIDDuplicated(ID);
                database.put(ID, new Account(name, surname, PESEL, address, ID));
            }
            catch(IDDuplicatedException e) {
                System.out.println("\nID of value: [" + ID +"] already exists in database.");
                System.out.println("Choose another ID for this client.\n");
                addAccount(database);
            }
            System.out.println("\n\nAccount created succesfully, has been added to database.");
            System.out.println("[" + ID + "]   " + name + " " + surname);
        }
        catch(InputMismatchException e) {
            System.out.println("\nPESEL and ID fields require only numbers.");
            System.out.println("Type proper values.\n");
            scnr.nextLine();
            addAccount(database);
        }
    }
    public void removeAccount(Database database) {
        try {
            confirm();
            database.getDatabase().remove(this.getID());
            System.out.println("\nAccount: " + this.getID() + " has been removed from databse.");
            database.saveToFile(database.getName());
        }
        catch(ConfirmationException e) {
            System.out.println("\nAccount of ID: " + this.getID() + " hasn't been removed from database.");
        }
    }
    public void transfer(Account account2) {
        try {
            double value;
            do {
                System.out.print("Cash amount to transfer: ");
                value = scnr.nextDouble();
                if (value <= 0) {
                    System.out.println("Do you really want to send this amount of money??");
                    System.out.println("Try again");
                }
            } while (value <= 0);
            scnr.nextLine();
            confirm();
            balance = getBalance() - value;
            account2.balance = account2.balance + value;
            System.out.println("Cash value of " + value + " has been transfered to account of ID: " + account2.getID());
        }
        catch(InputMismatchException e ) {
            System.out.println("Inproper cash value.");
            System.out.print("Do you want to try again? PRESS 'Y', press anything else to abort: ");
            if(scnr.nextLine().equals("Y") || scnr.nextLine().equals("y")) {
                transfer(account2);
            }
        }
        catch(ConfirmationException e) {
            System.out.println("Money haven't been transfered from account" + this.getID() + " to account" + account2.getID());
        }
    }
    public void withdraw() {
        try {
            double value;
            do {
                System.out.print("Amount to withdraw from [" + this.getID() + "]: ");
                value = scnr.nextDouble();
                if (value <= 0) {
                    System.out.println("You cannot withdraw this amount of money. Try again");
                }
            } while (value <= 0);
            scnr.nextLine();
            confirm();
            balance = getBalance() - value;

            System.out.println(value + " euro has been withdrawn from " + this.getID());
        }
        catch(ConfirmationException e) {
            System.out.println("Operation of withdrawing cash from account: " + this.getID() + "has been aborted");
        }
    }
    public void deposit() {

        try {
            double value;
            do {
                System.out.print("Amount to deposit to [" + this.ID +"]: ");
                value = scnr.nextDouble();
                if (value <= 0) {
                    System.out.println("You cannot send this amount of money. Try again");
                }
            } while (value <= 0);
            scnr.nextLine();
            confirm();
            this.balance = getBalance() + value;
            System.out.println(value + " euro has been has been added to " + this.getID());
        }
        catch(ConfirmationException e) {
            System.out.println("\nOperation of deposit has been aborted\n");
        }
        catch(InputMismatchException e ) {
            System.out.println("\nDeposit value contains only float numbers.\n");
            scnr.nextLine();
            deposit();
        }
    }
    public void displayAccountInformation() {
        System.out.println("\nAccount of ID: " + this.getID() + "\n");
        System.out.println("Name: " + this.getName());
        System.out.println("Surname: " + this.getSurname());
        System.out.println("PESEL: " + this.getPESEL());
        System.out.println("Address of client: " + this.getAddress());
        System.out.println("Available cash: " + this.getBalance() + "\n");
    }
    private void confirm() throws ConfirmationException{
        System.out.print("\nType 'C' if you want to CONFIRM operation or 'A' if you want to ABORT: ");
        String option = scnr.nextLine();
        if(option.equals("C") || option.equals("c")) {

        }
        else if(option.equals("A") || option.equals("a")) {
            throw new ConfirmationException();
        }
        else {
            System.out.println("Unhandled option.");
            confirm();
        }
    }
    public String toString() {
        return ("\n\nAccount of ID: " + this.getID() + "\n\n" +
                "Name: " + this.getName() + "\n" +
                "Surname: " + this.getSurname() + "\n" +
                "PESEL: " + this.getPESEL() + "\n" +
                "Address of client: " + this.getAddress() + "\n" +
                "Available cash: " + this.getBalance());
    }
}