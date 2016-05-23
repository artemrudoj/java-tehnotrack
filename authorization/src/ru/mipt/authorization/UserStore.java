package ru.mipt.authorization;

import ru.mipt.session.User;

/**
 * Created by artem on 25.11.15.
 */
public interface UserStore {
    long addUser(User user);

    boolean isUserExist(String name);

    User getUser(String name, String password);

    boolean isUserExist(Long userId);

    User findUserById(Long userId);
}
