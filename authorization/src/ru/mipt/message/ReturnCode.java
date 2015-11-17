package ru.mipt.message;

/**
 * Created by artem on 18.10.15.
 */
public class ReturnCode {
    public static final short NO_CURRENT_SESSION = -1;
    public static final short SUCCESS = 1;
    public static final short SESSION_ALREADY_HAVE_USER = 2;
    public static final short USER_ALREADY_EXIST = 3;
    public static final short USER_NOT_EXIST = 4;
    public static final short INCORRECT_ARGUMENTS = 5;
    public static final short COMMAND_NOT_FOUNDED = 6;
    public static final short COMMAND_TO_SERVER = 7;
    public static final short INCORRECT_LOGIN_OR_PASSWORD = 8;
    public static final short NO_AUTHORIZE = 9;
    public static final short CHAT_IS_NOT_EXIST = 10;

    public short getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(short returnCode) {
        this.returnCode = returnCode;
    }

    short returnCode;
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ReturnCode(short returnCode, String msg) {
        this.returnCode = returnCode;
        this.msg = msg;
    }

    public ReturnCode(short returnCode) {
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
            case ReturnCode.NO_CURRENT_SESSION:
                return "NO_CURRENT_SESSION";
            case ReturnCode.COMMAND_TO_SERVER:
                return "COMMAND_TO_SERVER ";
            case ReturnCode.INCORRECT_LOGIN_OR_PASSWORD:
                return "INCORRECT_LOGIN_OR_PASSWORD ";
            case ReturnCode.NO_AUTHORIZE:
                return "NO_AUTHORIZE ";
            case ReturnCode.CHAT_IS_NOT_EXIST:
                return "CHAT_IS_NOT_EXIST ";
            default:
                return "NO ERROR INFO ";
        }
    }
}
