package bank.system;

import java.util.Scanner;

 public class Account {

    private String name;
    private String surname;
    private Long personalIdNumber;
    protected Long login;
    protected String password;
    private Long depositedCash;

    Scanner scanner = new Scanner(System.in);
    Account account = new Account();
    Menu menu = new Menu();
    Authorization authorization = new Authorization();

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

        int condition;
        boolean authorizationCondition = authorization.signingIn();

        if (authorizationCondition) {

            System.out.print("Put the value of money you would like to pay into: ");
            account.depositedCash = Long.parseLong(scanner.nextLine());
            System.out.print("The money have been deposited.");

        }

        else {

            System.out.print("You have just put wrong login or password.");
            System.out.print("Would you like continue(1) or break(2) the operation?");
            condition = scanner.nextInt();

            while(true) {

                if (condition == 1) {

                    depositCash();

                }

                else if( condition == 2) {

                    break;

                }

                else {

                    System.out.println("Put right number: ");
                    System.out.println("1 - Continue this operation.");
                    System.out.println("2 - Break this operation.");
                    condition = scanner.nextInt();

                }
            }

        }

    }

 }
