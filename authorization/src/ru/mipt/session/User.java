package ru.mipt.session;


public class User {
    private String name;
    private String pass;
    private String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }



    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
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
}
