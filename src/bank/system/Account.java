package bank.system;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;

 public class Account extends BankSystem{

    private String name;
    private String surname;
    private BigDecimal pesel;
    private BigDecimal login;
    private double depositedCash;

    Menu menu = new Menu();
    Scanner scanner = new Scanner(System.in);

     public Account(String name, String surname, BigDecimal pesel, BigDecimal login) throws IOException {
         this.name = name;
         this.surname = surname;
         this.pesel = pesel;
         this.login = login;
     }

     public String getName() {

        return name;

    }

     public String getSurname() {

         return surname;

     }

     public BigDecimal getPesel() {

         return pesel;

     }

     public BigDecimal getLogin() {

         return login;

     }

     public double getDepositedCash() {

         return depositedCash;

     }

     public static void createNewAccount(Database database) throws IOException {
         Scanner scanner = new Scanner(System.in);
         String name;
         String surname;
         BigDecimal pesel;
         BigDecimal login;

         try {
             System.out.print("Insert clients name: ");
             name = scanner.nextLine();
             System.out.print("Insert clients surname: ");
             surname = scanner.nextLine();
             System.out.print("Insert clients pesel number: ");
             pesel = scanner.nextBigDecimal();
             scanner.nextLine();
             System.out.print("Adding original and unique clients login number: ");
             login = scanner.nextBigDecimal();
             scanner.nextLine();
             try {
                 database.isIDDuplicated(login);
                 database.put(login, new Account(name, surname, pesel, login));
             }
             catch(IDDuplicatedException e) {
                 System.out.println("\nID of value: [" + login +"] already exists in database.");
                 System.out.println("Choose another ID for this client.\n");
                 createNewAccount(database);
             }
             database.saveToFile(database.getName());
             System.out.println("\n\nAccount created succesfully, has been added to database.");
             System.out.println("[" + login + "]   " + name + " " + surname);
         }
         catch(InputMismatchException e) {
             System.out.println("\nPESEL and ID fields require only numbers.");
             System.out.println("Type proper values.\n");
             scanner.nextLine();
             createNewAccount(database);
         }

    }

     public void removeTheAccount(Database database) {

         database.getDatabase().remove(this.getLogin());
         System.out.println("\nAccount: " + this.getLogin() + " has been removed from databse.");
         database.saveToFile(database.getName());

     }

     public void depositCash() {

         System.out.print("Amount to deposit to [" + this.login +"]: ");
         try {
             double value = scanner.nextDouble();
             scanner.nextLine();
             this.depositedCash = getDepositedCash() + value;
             System.out.println(value + " euro has been has been added to " + this.getLogin());
         }
         catch(InputMismatchException e ) {
             System.out.println("\nDeposit value contains only float numbers.\n");
             scanner.nextLine();
             depositCash();
         }

    }

     public void withdrawCash() {

             System.out.print("Amount to withdraw from [" + this.getLogin() + "]: ");
             double value = scanner.nextDouble();
             depositedCash = getDepositedCash() - value;

             System.out.println(value + " euro has been withdrawn from " + this.getLogin());

     }

     public void transferCash(Account secondAccount) {

         try {

             System.out.print("Cash amount to transfer: ");
             double value = scanner.nextDouble();
             depositedCash = getDepositedCash() - value;
             secondAccount.depositedCash = secondAccount.depositedCash+ value;
             System.out.println("Cash value of " + value + " has been transfered to account of ID: " + secondAccount.getLogin());

         }

         catch(InputMismatchException e ) {

             System.out.println("Inproper cash value.");
             System.out.print("Do you want to try again? PRESS 'Y', press anything else to abort: ");

             if(scanner.nextLine().equals("Y") || scanner.nextLine().equals("y")) {

                 transferCash(secondAccount);

             }

         }


    }

     public void showOneClient() throws InputMismatchException, IOException {
         int choice;
         try {
             choice = scanner.nextInt();
             scanner.nextLine();
             switch (choice) {
                 case 1:
                     System.out.print("\nEnter a name: ");
                     String name = scanner.nextLine();
                     accountsDatabase.searchByName(name).printAllClients();
                     if (menu.continueTheApplication() == 1) {
                         startApplication();
                     }
                     break;
                 case 2:
                     System.out.print("\nEnter a surname: ");
                     String surname = scanner.nextLine();
                     accountsDatabase.searchBySurname(surname).printAllClients();
                     if (menu.continueTheApplication() == 1) {
                         startApplication();
                     }
                     break;
                 case 3:
                     System.out.print("\nEnter login: ");
                     BigDecimal login = scanner.nextBigDecimal();
                     scanner.nextLine();
                     accountsDatabase.searchByID(login).printAllClients();
                     if (menu.continueTheApplication() == 1) {
                         startApplication();
                     }
                     break;
                 case 4:
                     System.out.print("\nEnter pesel: ");
                     BigDecimal pesel = scanner.nextBigDecimal();
                     scanner.nextLine();
                     accountsDatabase.searchByPESEL(pesel).printAllClients();
                     if (menu.continueTheApplication() == 1) {
                         startApplication();
                     }
                     break;

                 default:
                     System.out.println("\nUnidentified operation. Choose from 1-5 to choose proper searching number. ID, PESEL contains only integer numbers.");
                     menu.showSearchMenu();
                     showOneClient();
                     break;
             }
         }
         catch(InputMismatchException e ) {
             System.out.println("\nUnidentified operation. Choose from 1-5 to choose proper searching number. ID, PESEL contains only integer numbers.");
             scanner.nextLine();
             menu.showSearchMenu();
             showOneClient();
         }
    }

     public void showAllClients() {

    }



 }
