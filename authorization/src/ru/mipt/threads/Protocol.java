package ru.mipt.threads;


import ru.mipt.hisorystorage.Message;

/**
 *
 */
public class Protocol {

    public static Message decode(byte[] bytes) {
        String data = new String(bytes);
        return new Message(data, messageType);
    }

    public static byte[] encode(Message msg) {
        return msg.getMessage().getBytes();

    }

}