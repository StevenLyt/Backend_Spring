package com.example.pokersc.entity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Action {
    enum Act {
        RAISE,
        CHECK,
        FOLD
    }
    private User user;
    private Act act;
    private int raise_amount = 0;

    public Action(User user, Act act){
        this.user = user;
        this.act = act;

    }
    public Action(User user, Act act, int raise_amount){
        this(user, act);
        this.raise_amount = raise_amount;
    }

    public User getUser() {
        return user;
    }

    public Act getAct() {
        return act;
    }

    public int getRaise_amount() {
        return raise_amount;
    }
}
