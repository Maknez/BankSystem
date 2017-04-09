package bank.system;

import java.util.Scanner;

 public class Account {

    private String name;
    private String surname;
    private Long personalIdNumber;
    private Long login;
    private String password;
    private Long depositedCash;

    private Scanner scanner = new Scanner(System.in);
    private Account account = new Account();
    private Menu menu = new Menu();

    public void createNewAccount() {

        menu.showTheMenu();

        System.out.print("Put your first name: ");
        account.name = scanner.nextLine();
        System.out.print("Put your surname: ");
        account.surname = scanner.nextLine();
        System.out.print("Put your personal identity number: ");
        account.personalIdNumber = Long.parseLong(scanner.nextLine());
        System.out.print("Put your account login(8 numbers): ");
        account.login = Long.parseLong(scanner.nextLine());
        System.out.print("Put your password: ");
        account.password = scanner.nextLine();
        System.out.print("Your account has been just successfully created.");
    }

    public void depositCash() {

        menu.showTheMenu();

        System.out.print("Put the value of money you would like to pay into: ");
        account.depositedCash = Long.parseLong(scanner.nextLine());
        System.out.print("The money have been deposited.");

    }

 }
