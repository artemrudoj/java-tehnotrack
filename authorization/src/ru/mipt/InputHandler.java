package ru.mipt;


import ru.mipt.comands.Command;
import ru.mipt.comands.ReturnCode;
import ru.mipt.hisorystorage.HistoryStorage;
import ru.mipt.protocol.Message;
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

    private Map<String, Command> commandMap;

    public InputHandler(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public Message handle(String data, Session session) {
        Message message;

        if (data.startsWith("\\")) {
            String[] tokens = data.split(" ");
            Command cmd = commandMap.get(tokens[0]);
            //perform command; session can be null if we don't authorize and wan't to do it
            if (cmd != null) {
                ReturnCode returnCode = cmd.execute(session, tokens);
                message = new Message(returnCode.getReturnCode(),returnCode.getMsg());
                session.storeMessage(data, new Date().getTime());
            }
            else {
                if (session != null) {
                    message = new Message(ReturnCode.COMMAND_NOT_FOUNDED);
                    session.storeMessage(data, new Date().getTime());
                } else {
                    message = new Message(ReturnCode.NO_AUTHORIZE);
                }
            }
        } else { //check is it simple message
            if (session != null) {
                message = new Message(ReturnCode.SUCCESS, data);
                session.storeMessage(data, new Date().getTime());
            }
            else {
                message = new Message(ReturnCode.NO_AUTHORIZE);
            }
        }
        return message;
    }

}



