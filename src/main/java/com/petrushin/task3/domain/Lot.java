package com.petrushin.task3.domain;

import java.util.*;

public class Lot {

    private int id;
    private int price;
    private int lifeTime = 1;
    private State state;
    private Map<User, Integer> userMap;

    public Lot() {
    }

    public Lot(int id, int price, int lifeTime) {
        this.id = id;
        this.price = price;
        this.lifeTime = lifeTime;
        userMap = new HashMap<>();
    }

    public Lot(int id, int price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price=price;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public void addUser(User user){
        userMap.put(user, 0);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Map<User, Integer> getUserMap() {
        return userMap;
    }

    public Set<User> getUserSet(){
        return userMap.keySet();
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
