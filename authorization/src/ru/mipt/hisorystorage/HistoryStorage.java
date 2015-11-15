package ru.mipt.hisorystorage;

import java.util.ArrayList;

/**
 * Created by artem on 18.10.15.
 */
public interface HistoryStorage {

    void addMessage(String msg);

    ArrayList<String> findMessage(String msg);

    String[] returnMessage(int N);

}
