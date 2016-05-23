package ru.mipt.session;


public class User {
    public static final int NO_USER_ID = -1;
    private String name;
    private String pass;
    private String nick;
    long userId;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }



    public User(String name, String pass ) {
        this.name = name;
        this.pass = pass;
    }
    public User(String name, String pass, long userId) {
        this.name = name;
        this.pass = pass;
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("login = ");
        str.append(name);
        str.append("\npassword = ");
        str.append(pass);
        return str.toString();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
