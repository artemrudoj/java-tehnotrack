package ru.mipt.protocol;


/**
 *
 */
public class Protocol {

    public static Message decode(byte[] bytes) {
        short messageType = getMessageType(bytes);
        int sessionId = getSessionId(bytes);
        String message =  getPureMessage(bytes);
        return new Message(messageType, message);
    }

    public static byte[] encode(Message msg) {
        return msg.getMessage().getBytes();

    }

    static short getMessageType(byte[] bytes){
        return 0;
    }

    static int getSessionId(byte[] bytes){
        return 0;
    }

    static String getPureMessage(byte[] bytes) {
        return null;
    }

}