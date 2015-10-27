package ru.mipt.comands;

/**
 * Created by artem on 18.10.15.
 */
public class ReturnCode {
    public static final int SIMPLE_MESSAGE = 0;
    public static final int SUCCESS = 1;
    public static final int SESSION_ALREADY_HAVE_USER = 2;
    public static final int USER_ALREADY_EXIST = 3;
    public static final int USER_NOT_EXIST = 4;
    public static final int INCORRECT_ARGUMENTS = 5;
    public static final int COMMAND_NOT_FOUNDED = 6;
    public static final int SESSION_DOES_NOT_HAVE_USER = 7;
    public static final int INCORRECT_LOGIN_OR_PASSWORD = 8;
    public static final int NO_AUTHORIZE = 9;

    int returnCode;
    String msg;

    public ReturnCode(int returnCode, String msg) {
        this.returnCode = returnCode;
        this.msg = msg;
    }

    public ReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public static String getReturnCodeInfo(int code) {
        switch (code) {
            case ReturnCode.SUCCESS:
                return "SUCCESS ";
            case  ReturnCode.SESSION_ALREADY_HAVE_USER:
                return "SESSION_ALREADY_HAVE_USER ";
            case ReturnCode.USER_ALREADY_EXIST:
                return "USER_ALREADY_EXIST ";
            case ReturnCode.USER_NOT_EXIST:
                return "USER_NOT_EXIST ";
            case ReturnCode.INCORRECT_ARGUMENTS:
                return "INCORRECT_ARGUMENTS ";
            case ReturnCode.COMMAND_NOT_FOUNDED:
                return "COMMAND_NOT_FOUNDED ";
            case ReturnCode.SESSION_DOES_NOT_HAVE_USER:
                return "SESSION_DOES_NOT_HAVE_USER ";
            case ReturnCode.INCORRECT_LOGIN_OR_PASSWORD:
                return "INCORRECT_LOGIN_OR_PASSWORD ";
            case ReturnCode.NO_AUTHORIZE:
                return "NO_AUTHORIZE ";
            default:
                return "NO ERROR INFO ";
        }
    }
}
