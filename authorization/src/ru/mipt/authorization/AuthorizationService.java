package ru.mipt.authorization;

import ru.mipt.session.User;
import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    public User login(String name, String password) {
        return userStore.getUser(name, password);
    }

    public Long createUser(String name, String password) {
        if (userStore.isUserExist(name)) {
            return null;
        }
        return userStore.addUser(new User(name, password));
    }

}
