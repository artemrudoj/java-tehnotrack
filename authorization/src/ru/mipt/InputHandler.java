package ru.mipt;


import ru.mipt.comands.Command;
import ru.mipt.hisorystorage.HistoryStorage;
import ru.mipt.session.Session;

import java.util.Date;
import java.util.Map;

/**
 * Основная задача класса принимать на вход данные от пользователя и решать, что с ними делать
 *
 * Можно крутить в цикле
 *
 */
public class InputHandler {

    private Session session;
    private Map<String, Command> commandMap;

    public InputHandler(Session session, Map<String, Command> commandMap) {
        this.session = session;
        this.commandMap = commandMap;
    }

    public void handle(String data) {
        Date time = new Date();
        session.storeMessage(data, time.getTime());
        if (data.startsWith("\\")) {
            String[] tokens = data.split(" ");

            Command cmd = commandMap.get(tokens[0]);
            cmd.execute(session, tokens);
        } else {
            System.out.println(">" + data);
        }
    }
}



