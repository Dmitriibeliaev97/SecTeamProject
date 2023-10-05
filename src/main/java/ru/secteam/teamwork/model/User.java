package ru.secteam.teamwork.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.sql.Timestamp;
import java.util.Objects;

@Entity (name = "Users")
public class User {
    @Id
    private long chatId;
    private String firstName;
    private String secondName;
    private String userName;
    private Timestamp registeredAt;

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return chatId == user.chatId && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(userName, user.userName) && Objects.equals(registeredAt, user.registeredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, firstName, secondName, userName, registeredAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
