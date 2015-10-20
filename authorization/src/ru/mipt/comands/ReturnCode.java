package ru.mipt.comands;

/**
 * Created by artem on 18.10.15.
 */
public class ReturnCode {
    static final int SUCCESS = 0;
    static final int SESSION_ALREADY_HAVE_USER = 1;
    static final int USER_ALREADY_EXIST = 2;
    static final int USER_NOT_EXIST = 3;
    static final int INCORRECT_ARGUMENTS = 4;
    static final int COMMAND_NOT_FOUNDED = 5;
}
