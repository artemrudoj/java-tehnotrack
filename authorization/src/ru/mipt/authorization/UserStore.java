package ru.mipt.authorization;


import ru.mipt.session.User;

import java.util.ArrayList;

public class UserStore {

    ArrayList<User> users = new ArrayList<User>();

    boolean isUserExist(String name) {
        if (findUserByName(name) == null)
            return false;
        else
            return true;
    }

    User addUser(User user) {
        users.add(user);
        return user;
    }

    User findUserByName(String name ){
        if (name != null) {
            for (User user : users) {
                if (name.equals(user.getName())) {
                    return user;
                }
            }
        }
        return null;
    }

    User getUser(String name, String pass) {
        User user = findUserByName(name);
        if (user != null) {
            user.setPass(pass);
        }
        return user;
    }
}
