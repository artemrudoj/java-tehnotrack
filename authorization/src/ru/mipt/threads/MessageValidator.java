package ru.mipt.threads;

import ru.mipt.chat.Chat;
import ru.mipt.comands.CommandType;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;

import java.util.HashMap;

/**
 * Created by artem on 16.11.15.
 */
public class MessageValidator {
    HashMap<String, Short> commands;

    public MessageValidator() {
        commands = new HashMap<>();
        commands.put("\\find", CommandType.FIND);
        commands.put("\\user", CommandType.USER);
        commands.put("\\history", CommandType.HISTORY);
        commands.put("\\login", CommandType.LOGIN);
        commands.put("\\registration", CommandType.REGISTRATION);
        commands.put("\\help", CommandType.HELP);
        commands.put("\\chat", CommandType.CHAT);
    }

    Short getCommandTypeByName(String command) {
        if( command.startsWith("\\")) {
            Short commandId;
            commandId = commands.get(command);
            return commandId;
        } else
            return null;
    }

    boolean isValidCommandType(String command, short commandType) {
        Short type = getCommandTypeByName(command);
        return type == commandType;
    }


    // in the future I will add validate all commands
    Message prepareMessage(String messageBody, long userId, long sessionId) {
        if (messageBody == null)
            return null;
        String[] tokens = messageBody.split(" ");
        Short messageType = getCommandTypeByName(tokens[0]);
        if (messageType == null)
            return null;
        long chatId;
        if (messageType == CommandType.CHAT) {
            if (tokens[1].equals("send")){
                if (tokens.length > 3) {
                    try {
                        chatId = Integer.parseInt(tokens[2]);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                } else
                //incorrect format
                    return null;
            } else
                chatId = Chat.MESSAGE_ONLY_FOR_SERVER;
        } else
            chatId = Chat.MESSAGE_ONLY_FOR_SERVER;

        Message message = new Message(messageBody);
        message.setChatId(chatId);
        message.setType(messageType);
        message.setSessionId(sessionId);
        message.setSenderId(userId);
        return message;
    }

    String messageDecarator(Message msg) {
        StringBuilder builder = new StringBuilder();
        if (msg.getChatId() == Chat.MESSAGE_ONLY_FOR_SERVER) {
            builder.append("[server]: ");
            builder.append(ReturnCode.getReturnCodeInfo(msg.getReturnCode()));
        } else {
            builder.append("[chat ID ")
                    .append(Long.toString(msg.getChatId()))
                    .append("]: ")
                    .append("[user ID ")
                    .append(Long.toString(msg.getSenderId()))
                    .append("]: ");

        }
        if (msg.getMessage() != null)
            builder.append(msg.getMessage());
        return builder.toString();
    }
}