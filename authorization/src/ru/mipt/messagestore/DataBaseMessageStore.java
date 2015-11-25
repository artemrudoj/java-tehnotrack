package ru.mipt.messagestore;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.postgresql.jdbc2.optional.ConnectionPool;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by artem on 23.11.15.
 */
public class DataBaseMessageStore implements MessageStore {

    ComboPooledDataSource connectionPool;;
    private AtomicLong internalCounter;
    public DataBaseMessageStore(ComboPooledDataSource connectionPool) {
        this.connectionPool = connectionPool;
        this.internalCounter = new AtomicLong(0);
    }

    @Override
    public long addMessage(Message msg) {
        try {
            Connection connection = connectionPool.getConnection();
            long messageId = internalCounter.incrementAndGet();
            String sql = "insert into \"messages\" (chatId,senderId,message,time,messageId) " + "values (?,?,?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, msg.getChatId());
            stmt.setLong(2, msg.getSenderId());
            stmt.setString(3, msg.getMessage());
            stmt.setLong(4, msg.getTime());
            stmt.setLong(5, messageId);
            System.out.println(stmt);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            return messageId;
        } catch (SQLException e) {
            System.out.println(e);
            return ReturnCode.MESSAGES_NOT_ADDED;
        } catch (Exception e) {
            System.out.println(e);
            return ReturnCode.MESSAGES_NOT_ADDED;
        }
    }

    @Override
    public Message getMessageById(long id) {
        try {
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM \"messages\" WHERE messageId=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            Message msg = null;
            if (rs.next()) {
                msg = new Message(rs.getString("message"));
                msg.setChatId(rs.getLong("chatId"));
                msg.setSenderId(rs.getLong("senderId"));
                msg.setTime(rs.getLong("time"));
                msg.setMessageId(rs.getLong("messageId"));
            }
            rs.close();
            stmt.close();
            connection.close();
            return msg;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<Message> findMessage(String msg, long chatId) {
        ArrayList<Message> findedMessages = new ArrayList<Message>();
        ArrayList<Message> messagesOfChat = getMessages(chatId);
        for (Message currentMsg : messagesOfChat) {
            for (String data : currentMsg.getMessage().split(" ")) {
                if (msg.equals(data)) {
                    findedMessages.add(currentMsg);
                    break;
                }
            }
        }
        return findedMessages;
    }

    @Override
    public ArrayList<Message> getMessages(long chatId) {
        try {
            ArrayList<Message> messages = new ArrayList<>();
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM \"messages\" WHERE chatId=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, chatId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message(rs.getString("message"));
                msg.setChatId(rs.getLong("chatId"));
                msg.setSenderId(rs.getLong("senderId"));
                msg.setTime(rs.getLong("time"));
                msg.setMessageId(rs.getLong("messageId"));
                messages.add(msg);
            }
            rs.close();
            stmt.close();
            connection.close();
            return messages;
        } catch (Exception e) {
            return null;
        }
    }
}
