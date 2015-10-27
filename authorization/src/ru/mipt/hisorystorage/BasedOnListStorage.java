package ru.mipt.hisorystorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Created by artem on 18.10.15.
 */
public class BasedOnListStorage implements HistoryStorage {

    LinkedList<Message> listStorage = new LinkedList<Message>();

    @Override
    public void addMessage(String msg, long time) {
        Message currentMessage = new Message(messageType);
        currentMessage.setMessage(msg);
        currentMessage.setTime(time);
        listStorage.add(currentMessage);
    }

    @Override
    public ArrayList<String> findMessage(String msg) {
        ArrayList<String> findedCommands = new ArrayList<String>();
        for (Message currentMsg : listStorage) {
            for (String data : currentMsg.getMessage().split(" ")) {
                if (msg.equals(data)) {
                    findedCommands.add(currentMsg.getMessage());
                    break;
                }
            }
        }
        return findedCommands;
    }

    @Override
    public String[] returnMessage(int N) {
        String[] returnedStrings = new String[N];
        Iterator<Message> iterator = listStorage.descendingIterator();
        for (int i = 0; i < N && iterator.hasNext(); i++) {
            returnedStrings[i] = iterator.next().getMessage();
        }
        return  returnedStrings;
    }
}
