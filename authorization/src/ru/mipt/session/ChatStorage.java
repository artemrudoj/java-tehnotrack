package ru.mipt.session;

import java.util.List;

/**
 * Created by artem on 29.10.15.
 */
public class ChatStorage {
    //simple add to end o(1), the order is not important
    List<Long> userIds;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
