package ru.mipt.comands;

import ru.mipt.authorization.UserStore;
import ru.mipt.chat.Chat;
import ru.mipt.chat.ChatStorage;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.messagestore.MessageStore;
import ru.mipt.session.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by artem on 16.11.15.
 */
public class ChatCommand implements Command {

    ChatStorage chatStorage;
    UserStore userStore;
    MessageStore messageStore;

    public ChatCommand(ChatStorage chatStorage, UserStore userStore, MessageStore messageStore) {
        this.chatStorage = chatStorage;
        this.userStore = userStore;
        this.messageStore = messageStore;
    }

    @Override
    public ReturnCode execute(Session session, Message message) {


        if (session == null)
            return new ReturnCode(ReturnCode.NO_CURRENT_SESSION);
        String args[] = message.getMessage().split(" ");
        if (args[1] == null)
            return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        if (args[1].equals("list")) {
            long userId = session.getSessionUser().getUserId();
            ArrayList<Long> chatIds = chatStorage.getChatsForUser(userId);
            if (chatIds == null)
                return new ReturnCode(ReturnCode.SUCCESS, "chats not found");
            else {
                StringBuffer chats = new StringBuffer();
                for (long id : chatIds) {
                    chats.append(id);
                    chats.append(" ");
                }
                return new ReturnCode(ReturnCode.SUCCESS, "avalible chats : " + chats.toString());
            }
        } else if (args[1].equals("create")) {
            //mnimum one id
            if (args.length < 3)
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            try {
                Chat chat = new Chat();
                for ( int i = 2; i < args.length; i++) {
                    Long userId = Long.parseLong(args[i]);
                    if(userStore.isUserExist(userId))
                        chat.addParticipant(userId);
                    else
                        return new ReturnCode(ReturnCode.USER_NOT_EXIST, userId.toString());
                }
                //add himself to chat
                chat.addParticipant(session.getSessionUser().getUserId());
                Long chatId = chatStorage.addChat(chat);
                return new ReturnCode(ReturnCode.SUCCESS, "chat ID " + chatId.toString());
            } catch (NumberFormatException e) {
                    return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
                }
        } else if (args[1].equals("send")) {
            if (args.length < 3) {
                message.setChatId(Chat.MESSAGE_ONLY_FOR_SERVER);
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            }
            else {
                try {
                    if (!chatStorage.isChatExist(session.getSessionUser().getUserId(), Long.parseLong(args[2]))) {
                        message.setChatId(Chat.MESSAGE_ONLY_FOR_SERVER);
                        return new ReturnCode(ReturnCode.CHAT_IS_NOT_EXIST);
                    }
                    StringBuilder builder = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        if (builder.length() > 0) {
                            builder.append(" ");
                        }
                        builder.append(args[i]);
                    }
                    messageStore.addMessage(message);
                    return new ReturnCode(ReturnCode.SUCCESS, builder.toString());
                } catch (NumberFormatException e) {
                    message.setChatId(Chat.MESSAGE_ONLY_FOR_SERVER);
                    return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
                }
            }
        } else if (args[1].equals("history")) {
            if (args.length != 3)
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            try {
                if (!chatStorage.isChatExist(session.getSessionUser().getUserId(), Long.parseLong(args[2])))
                    return new ReturnCode(ReturnCode.CHAT_IS_NOT_EXIST);
                ArrayList<Message> messages = messageStore.getMessages(message.getChatId());
                StringBuilder builder = new StringBuilder();
                for (Message msg : messages) {
                    String formatMessage = String.format("[chat id = %d ] : [user id = %d]:%s\n", msg.getChatId(), msg.getSenderId(), msg.getMessage());
                    builder.append(formatMessage);
                }
                return new ReturnCode(ReturnCode.SUCCESS, builder.toString());
            } catch (NumberFormatException e) {
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            }
        } else if (args[1].equals("find")) {
            if (args.length != 4)
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            try {
                if (!chatStorage.isChatExist(session.getSessionUser().getUserId(), Long.parseLong(args[2])))
                    return new ReturnCode(ReturnCode.CHAT_IS_NOT_EXIST);
                ArrayList<Message> messages = messageStore.findMessage(args[3], message.getChatId());
                StringBuilder builder = new StringBuilder();
                for (Message msg : messages) {
                    String formatMessage = String.format("[chat id = %d ] : [user id = %d]:%s\n", msg.getChatId(), msg.getSenderId(), msg.getMessage());
                    builder.append(formatMessage);
                }
                if (messages == null)
                    builder.append("Nothind is found\n");
                return new ReturnCode(ReturnCode.SUCCESS, builder.toString());
            } catch (NumberFormatException e) {
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            }
        } else
            return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
    }

}
