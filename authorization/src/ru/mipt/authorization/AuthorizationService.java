package ru.mipt.authorization;

import ru.mipt.session.User;
import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;
    private Scanner scanner = new Scanner(System.in);

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }



    public User login(String name) {
        if (!userStore.isUserExist(name)) {
            System.out.println("Error: User does not exist");
            return null;
        }
        System.out.println("Please, enter password");
        String password = scanner.next();
        return userStore.getUser(name, password);
    }

    public User createUser() {

        System.out.println("Please, enter login");
        String name = scanner.next();
        if (userStore.isUserExist(name)) {
            return null;
        }
        System.out.println("Please, enter password");
        String password = scanner.next();

        return userStore.addUser(new User(name, password));
    }

    boolean isLogin() {
        return false;
    }
}
