package com.example.pokersc.entity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Action {
    enum Act {
        RAISE,
        CHECK,
        FOLD,
        CALL,
    }
    private User user;
    private Act act;
    private int amount = 0;

    public Action(User user, Act act){
        this.user = user;
        this.act = act;

    }
    public Action(User user, Act act, int amount){
        this(user, act);
        this.amount = amount;
    }

    public User getUser() {
        return this.user;
    }

    public Act getAct() {
        return this.act;
    }

    public int getAmount() {
        return this.amount;
    }
}
