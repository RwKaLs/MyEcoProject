package ru.myitschool.vsu2020.myecoproject.logic;

import java.util.HashMap;


public class World {

    private int money;
    private int k;
    private int level;
    private int ceff_money;
    private int ySpeed;
    private int[] tokens = new int[10];
    final String RUSSIA = "Россия", CHINA = "Китай", FRANCE = "Франция", ITALY = "Италия", ENGLAND = "Англия",
            USA = "США", CANADA = "Канада", BRAZIL = "Бразилия", MEXICO = "Мексика", COLOMBIA = "Колумбия";

    public void setMoney(int money) {
        this.money = money;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setCeff_money(int ceff_money) {
        this.ceff_money = ceff_money;
    }
    //order: Россия, Китай, Франция, Италия, Англия
    //       США, Канада, Бразилия, Мексика, Колумбия
    public void setToken(String k) {
        if(tokens[countryToArray(k)] != 0){
            tokens[countryToArray(k)] += 1;
        } else {
            tokens[countryToArray(k)] = 1;
        }
    }

    public void setToken(String k, int v){
        tokens[countryToArray(k)] = v;
    }

    //test method
    public int[] getTokens(){
        return tokens;
    }


    public int getCountryCount(String k){
        return tokens[countryToArray(k)];
    }

    public int countryToArray(String k){
        int res = 100;
        switch (k){
            case RUSSIA: res = 0; break;
            case CHINA: res =  1; break;
            case FRANCE: res =  2; break;
            case ITALY: res = 3; break;
            case ENGLAND: res = 4; break;
            case USA: res = 5; break;
            case CANADA: res = 6; break;
            case BRAZIL: res = 7; break;
            case MEXICO: res = 8; break;
            case COLOMBIA: res = 9; break;
        }
        return res;
    }

    public int getMoney() {
        return this.money;
    }

    public int getySpeed() {
        return this.ySpeed;
    }

    public int getK() {
        return this.k;
    }

    public int getLevel() {
        return this.level;
    }

    public int getCeff_money() {
        return this.ceff_money;
    }

    public void addMoney() {
        this.money += ceff_money;
    }

    public void addCeffMoney() {
        this.ceff_money += 2;
    }

    public void addK() {
        this.k *= 2;
    }

    public void addLevel() {
        this.level += 1;
    }

    public void spendMoney(int price) {
        this.money -= price;
    }
}
