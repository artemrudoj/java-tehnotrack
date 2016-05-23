package ru.mipt.authorization;


import ru.mipt.session.User;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleUserStore implements UserStore{

    ArrayList<User> users = new ArrayList<User>();
    AtomicLong id = new AtomicLong(0);

    @Override
    public boolean isUserExist(String name) {
        if (findUserByName(name) == null)
            return false;
        else
            return true;
    }

    @Override
    public long addUser(User user) {
        long userId = id.incrementAndGet();
        user.setUserId(userId);
        users.add(user);
        return userId;
    }

    User findUserByName(String name) {
        if (name != null) {
            for (User user : users) {
                if (name.equals(user.getName())) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User findUserById(Long id) {
        if (id != null) {
            for (User user : users) {
                if (id == user.getUserId()) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User getUser(String name, String pass) {
        User user = findUserByName(name);
        if (user != null) {
            if (user.getPass().equals(pass))
                return user;
        }
        return null;
    }

    @Override
    public boolean isUserExist(Long userId) {
        if (findUserById(userId) != null)
            return true;
        else
            return false;
    }
}
