package ru.mipt.authorization;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by artem on 25.11.15.
 */
public class DataBaseUserStore implements UserStore {


    ComboPooledDataSource connectionPool;;
    private AtomicLong internalCounter;
    public DataBaseUserStore(ComboPooledDataSource connectionPool) {
        this.connectionPool = connectionPool;
        this.internalCounter = new AtomicLong(0);
    }


    @Override
    public long addUser(User user) {
        try {
            long id = internalCounter.incrementAndGet();
            Connection connection = connectionPool.getConnection();
            long messageId = internalCounter.incrementAndGet();
            String sql = "insert into \"user\" (id,name,nick,pass) " + "values (?,?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getNick());
            stmt.setString(4, user.getPass());
            System.out.println(stmt);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            return messageId;
        } catch (SQLException e) {
            System.out.println(e);
            return ReturnCode.USER_NOT_ADDED;
        } catch (Exception e) {
            System.out.println(e);
            return ReturnCode.USER_NOT_ADDED;
        }
    }

    @Override
    public boolean isUserExist(String name) {
        try {
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM \"user\" WHERE name=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                connection.close();
                return true;
            }
            rs.close();
            stmt.close();
            connection.close();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User getUser(String name, String password) {
        try {
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM \"user\" WHERE name=? AND pass=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("name"), rs.getString("pass"));
                user.setUserId(rs.getLong("id"));
            }
            rs.close();
            stmt.close();
            connection.close();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isUserExist(Long userId) {
        try {
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM \"user\" WHERE id=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                connection.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                connection.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findUserById(Long userId) {
        try {
            Connection connection = connectionPool.getConnection();
            String sql = "SELECT * FROM \"user\" WHERE id=?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("name"), rs.getString("pass"));
            }
            rs.close();
            stmt.close();
            connection.close();
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
