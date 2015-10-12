package ru.mipt;


public class User {
    private String name;
    private String pass;

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
