package com.petrushin.task3.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Auction {

    private List<Lot> lots;
    private List<User> users;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();
    private static Auction instance;

    public static Auction getInstance(){
        if(!isCreated.get()){
            lock.lock();
            try{
                if(instance == null){
                    instance = new Auction();
                    isCreated.compareAndSet(true,false);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }



    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
