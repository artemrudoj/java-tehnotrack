package ru.mipt;


import javax.jws.soap.SOAPBinding;
import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;
    private Scanner scanner;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    void startAuthorization() {

        scanner = new Scanner(System.in);

        while (true) {
        System.out.println("Authorization is staring:");
        System.out.println("Press 1 - for login");
        System.out.println("Press 2 - for registration");

        int num = scanner.nextInt();
            User user;
            switch (num) {
                case 1:
                    System.out.println("You have chosen \"loggin\"");
                    user = login();
                    if ( user == null )
                        System.out.println("Incorrect login or password");
                    else
                        System.out.println(login().toString());
                    break;
                case 2:
                    System.out.println("You have chosen \"regisration\"");
                    user = creatUser();
                    if ( user == null )
                        System.out.println("User already exist");
                    else
                        System.out.println(user.toString());
                    break;
                default:
                    System.out.println("Input data was not correct");
                    return;
            }
        }
    }

    User login() {
        System.out.println("Please, enter name");
        String name = scanner.next();
        if ( !userStore.isUserExist( name) ) {
            System.out.println("Error: User does not exist");
            return null;
        }
        System.out.println("Please, enter password");
        String password = scanner.next();
        return userStore.getUser( name, password);
    }

    User creatUser() {

        System.out.println("Please, enter name");
        String name = scanner.next();
        if ( userStore.isUserExist( name) ) {
            System.out.println("Error: User already exists");
            return null;
        }
        System.out.println("Please, enter password");
        String password = scanner.next();

        return userStore.addUser( new User( name, password));
    }

    boolean isLogin() {
        return false;
    }
}
