package bank.system;


import java.util.Scanner;

public class Authorization extends Account {

    private Long tempLogin;
    private String tempPassword;

    private Scanner scanner = new Scanner(System.in);
    private Authorization authorization = new Authorization();

    public boolean signingIn() {

        System.out.print("Login: ");
        authorization.tempLogin = Long.parseLong(scanner.nextLine());
        System.out.print("Password: ");
        authorization.tempPassword = scanner.nextLine();

        if(tempLogin == account.login) {

            if(tempPassword == account.password) {

                return true;

            }

        }

            return false;

    }

}
