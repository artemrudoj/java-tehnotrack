package ru.mipt.threads;


import ru.mipt.comands.Command;
import ru.mipt.comands.CommandType;
import ru.mipt.comands.LoginCommand;
import ru.mipt.message.ReturnCode;
import ru.mipt.message.Message;
import ru.mipt.session.Session;

import java.util.Date;
import java.util.Map;


public class InputHandler {
    private MessageValidator validator;
    private Map<String, Command> commandMap;

    public InputHandler(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
        validator = new MessageValidator();
    }

    public Message handle(Message recivedMessage, Session session, long currentSessionId) {
        assert(recivedMessage != null);

        short commandType = recivedMessage.getType();
        if (!validator.isValidMessageType(commandType)) {
            recivedMessage.setReturnCode(ReturnCode.COMMAND_NOT_FOUNDED);
            return recivedMessage;
        }

        String data = recivedMessage.getMessage();
        String[] tokens = data.split(" ");
        if (!validator.isValidCommandType(tokens[0], commandType)) {
            recivedMessage.setReturnCode(ReturnCode.COMMAND_NOT_FOUNDED);
            return recivedMessage;
        }

        Command cmd = commandMap.get(tokens[0]);
            //perform command; session can be null if we don't authorize and wan't to do it
        short code;
        if (cmd != null) {
            ReturnCode returnCode;
            if (recivedMessage.getType() == CommandType.LOGIN) {
                LoginCommand login = (LoginCommand) cmd;
                login.setPossibleSessionId(currentSessionId);
                returnCode = login.execute(session, recivedMessage);
                code = returnCode.getReturnCode();
                if (code == ReturnCode.SUCCESS)
                    recivedMessage.setSessionId(currentSessionId);
            } else {
                    returnCode = cmd.execute(session, recivedMessage);
                    code = returnCode.getReturnCode();
            }
            data = returnCode.getMsg();

            assert(session != null);
        } else {
            if (session != null) {
                code = ReturnCode.COMMAND_NOT_FOUNDED;
            } else {
                code = ReturnCode.NO_CURRENT_SESSION;
            }
        }
        wrapMessage(recivedMessage, code, data);
        return recivedMessage;
    }

    void wrapMessage(Message message, short code, String msg) {
        message.setReturnCode(code);
        message.setMessage(msg);
    }

}



