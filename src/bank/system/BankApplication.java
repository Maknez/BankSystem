package bank.system;


import java.util.Scanner;

public class BankApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu();
        /*Authorization authorization = new Authorization();
        Account account = new Account();
*/
        menu.showTheMenu();
    }

}
