package com.example.pokersc.entity;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String profile_url;
    private int total_win;
    private int total_round;
    private int buyin;

    public int getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(int total_profit) {
        this.total_profit = total_profit;
    }

    private int total_profit;

    protected User(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public int getTotal_round() {
        return total_round;
    }

    public void setTotal_round(int total_round) {
        this.total_round = total_round;
    }

    public int getBuyin() {
        return buyin;
    }

    public void setBuyin(int buyin) {
        this.buyin = buyin;
    }

    public void addAmount(double amount) {
        this.buyin += amount;
    }

    public User(String username, String password, String profile_url) {
        this.username = username;
        this.password = password;
        this.profile_url = profile_url;
        this.buyin = 0;
        this.total_round = 0;
        this.total_profit = 0;
    }


}
