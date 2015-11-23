package ru.mipt.chat;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import ru.mipt.message.ReturnCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by artem on 23.11.15.
 */
public class DataBaseChatStorage implements ChatStorage{


    ComboPooledDataSource connectionPool;;
    private AtomicLong internalCounter;


    public DataBaseChatStorage(ComboPooledDataSource connectionPool) {
        this.connectionPool = connectionPool;
        this.internalCounter = new AtomicLong(0);
    }

    @Override
    public long addChat(Chat chat) {
        try {
            Connection connection = connectionPool.getConnection();
            long chatId = internalCounter.incrementAndGet();
            PreparedStatement stmt = null;
            assert (chat.getParticipantIds() != null);
            for (long userId : chat.getParticipantIds()) {
                String sql = "insert into chat_to_user (chatId,userId) " + "values (?,?);";
                stmt = connection.prepareStatement(sql);
                stmt.setLong(1, chatId);
                stmt.setLong(2, userId);
                System.out.println(stmt);
                stmt.executeUpdate();
            }
            assert (stmt != null);
            stmt.close();
            connection.close();
            return chatId;
        } catch (SQLException e) {
            System.out.println(e);
            return ReturnCode.CHAT_NOT_ADDED;
        } catch (Exception e) {
            System.out.println(e);
            return ReturnCode.CHAT_NOT_ADDED;
        }
    }


    @Override
    public ArrayList<Long> getChatsForUser(long userId) {
        try {
            ArrayList<Long> ids = new ArrayList<>();
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM chat_to_user WHERE userId=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("chatId");
                ids.add(id);
            }
            rs.close();
            stmt.close();
            connection.close();
            return ids;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isChatExist(long userId, long charId) {
        ArrayList<Long>  userIds = getParticipantIds(charId);
        for (long id : userIds) {
            if (id == userId)
                return true;
        }
        return false;
    }

    @Override
    public ArrayList<Long> getParticipantIds(long chatId) {
        try {
            ArrayList<Long> ids = new ArrayList<>();
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM chat_to_user WHERE chatId=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, chatId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("userId");
                ids.add(id);
            }
            rs.close();
            stmt.close();
            connection.close();
            return ids;
        } catch (Exception e) {
            return null;
        }
    }
}
