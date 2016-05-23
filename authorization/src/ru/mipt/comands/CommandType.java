package ru.mipt.comands;

/**
 * Created by artem on 16.11.15.
 */
public class CommandType {
    public static final short FIRST_REGISTRED_COMMAND = CommandType.SIMPLE_MESSAGE;
    public static final short LAST_REGISTERD_COMMAND = CommandType.CHAT;

    public static final short SIMPLE_MESSAGE = 0;
    public static final short HELP = 1;
    public static final short LOGIN = 2;
    public static final short REGISTRATION = 3;
    public static final short USER = 4;
    public static final short CHAT = 5;
}
