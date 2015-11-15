package ru.mipt.protocol;


import ru.mipt.message.Message;

import java.io.*;

/**
 *
 */
public class Protocol {

    public static Message decode(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Message message = null;
        try {
            in = new ObjectInputStream(bis);
            message = (Message)in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }
    }

    public static byte[] encode(Message msg) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] array = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(msg);
            array = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return array;
        }
    }
}