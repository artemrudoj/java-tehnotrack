package ru.mipt;


import java.util.ArrayList;

public class UserStore {

    ArrayList<User> users = new ArrayList<User>();

    boolean isUserExist(String name) {
        if ( findUserByName( name) == null)
            return false;
        else
            return true;
    }

    User addUser(User user) {
        users.add( user );
        return  users.get( users.size() - 1);
    }

    User findUserByName( String name ){
        for (User user : users) {
            if (name.equals(user.getName())) {
                return user;
            }
        }
        return null;
    }
    // Получить пользователя по имени и паролю
    User getUser(String name, String pass) {
        User user = findUserByName( name);
        return user.getPass().equals( pass) ? user : null;
    }
}
