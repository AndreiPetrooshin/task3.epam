package com.petrushin.task3.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private AtomicInteger cash;
    private String name;

    public User() {
    }

    public User(int cash, String name) {
        this.cash = new AtomicInteger(cash);
        this.name = name;
    }

    public int getCash() {
        return cash.get();
    }

    public void setCash(int cash) {
        this.cash.set(cash);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (cash != null ? !cash.equals(user.cash) : user.cash != null) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = cash != null ? cash.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "cash=" + cash +
                ", name='" + name + '\'' +
                '}';
    }
}
