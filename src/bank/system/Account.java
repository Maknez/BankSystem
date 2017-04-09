package bank.system;

import java.math.BigDecimal;
import java.util.Scanner;

 public class Account {

    private String name;
    private String surname;
    private BigDecimal pesel;
    private BigDecimal login;
    private double depositedCash;

    Menu menu = new Menu();
    Scanner scanner = new Scanner(System.in);
    Account account = new Account();

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
/*
     public double getDepositedCash() {

         return depositedCash;

     }

     public static void createNewAccount() {

        System.out.print("Put your first name: ");
        account.name = scanner.nextLine();
        System.out.print("Put your surname: ");
        account.surname = scanner.nextLine();
        System.out.print("Put your personal identity number(PESEL): ");
        account.pesel = scanner.nextBigDecimal();
        System.out.print("Your account has been just successfully created.");

    }

     public void removeTheAccount() {

     }

     public void depositCash() {

        menu.showTheMenu();

        int condition;
        boolean authorizationCondition = authorization.signingIn();

        if (authorizationCondition) {

            System.out.print("Put the value of money you would like to pay into: ");
            account.depositedCash = scanner.nextDouble();
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

     public void withdrawCash() {

    }

     public void transferCash() {

    }

     public void showOneClient() {

    }

     public void showAllClients() {

    }
*/


 }
