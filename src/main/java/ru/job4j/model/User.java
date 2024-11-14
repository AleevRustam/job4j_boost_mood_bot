package ru.job4j.model;

import java.util.Objects;

public class User {
    private Long id;
    private Long clientId;
    private Long chatId;

    public User(Long id, Long clientId, Long chatId) {
        this.id = id;
        this.clientId = clientId;
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(id, user.id)
                && Objects.equals(clientId, user.clientId)
                && Objects.equals(chatId, user.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, chatId);
    }
}
