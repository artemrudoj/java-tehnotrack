package ru.mipt.threads;


import ru.mipt.comands.Command;
import ru.mipt.comands.LoginCommand;
import ru.mipt.message.ReturnCode;
import ru.mipt.message.Message;
import ru.mipt.session.Session;

import java.util.Date;
import java.util.Map;


public class InputHandler {

    private Map<String, Command> commandMap;

    public InputHandler(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public Message handle(Message recivedMessage, Session session, long currentSessionId) {
        assert(recivedMessage != null);

        String data = recivedMessage.getMessage();
        short code;

        //if is it command
        if (data.startsWith("\\")) {
            String[] tokens = data.split(" ");
            Command cmd = commandMap.get(tokens[0]);
            //perform command; session can be null if we don't authorize and wan't to do it
            if (cmd != null) {
                ReturnCode returnCode;
                if (tokens[0].equals("\\login")) {
                    LoginCommand login = (LoginCommand) cmd;
                    login.setPossibleSessionId(currentSessionId);
                    returnCode = login.execute(session, tokens);
                    code = returnCode.getReturnCode();
                    if (code == ReturnCode.SUCCESS)
                        recivedMessage.setSessionId(currentSessionId);
                } else {
                    returnCode = cmd.execute(session, tokens);
                    code = returnCode.getReturnCode();
                }
                data = returnCode.getMsg();

                assert(session != null);
                //FIXME must be Message Type
               // session.storeMessage(data);
            }
            else {
                if (session != null) {
                    code = ReturnCode.COMMAND_NOT_FOUNDED;
                    //FIXME
                    //session.storeMessage(data, new Date().getTime());
                } else {
                    code = ReturnCode.NO_AUTHORIZE;
                }
            }
        } else {
            //check is it simple message
            if (session != null) {
                code = ReturnCode.SUCCESS;
                //FIXME
                //session.storeMessage(data, new Date().getTime());
            }
            else {
                code = ReturnCode.NO_AUTHORIZE;
            }
        }
        wrapMessage(recivedMessage, code, new Date().getTime(), data);
        return recivedMessage;
    }

    void wrapMessage(Message message, short code, long time, String msg){
        message.setMessageType(code);
        message.setTime(time);
        message.setMessage(msg);
    }

}



