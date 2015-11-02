package ru.mipt.protocol;

/**
 * Created by artem on 28.10.15.
 */
public class MessageBean {
    int commandId;
    int sessionId;

    public class LoginAndRegisterInfoClient {
        int sessionId;
    }

    public class LoginAndRegisterInfoServer {
        String login;
        String password;
    }



}
