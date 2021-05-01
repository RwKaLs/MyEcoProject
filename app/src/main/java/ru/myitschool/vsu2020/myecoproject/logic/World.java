package ru.myitschool.vsu2020.myecoproject.logic;

import java.util.AbstractMap;

public class World {

    private int money;
    private int k;
    private int level;
    private int ceff_money;
    private int ySpeed;
    private AbstractMap<String, Integer> tokens;

    public void setMoney(int money) {
        this.money = money;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setySpeed(int ySpeed){
        this.ySpeed = ySpeed;
    }

    public void setCeff_money(int ceff_money) {
        this.ceff_money = ceff_money;
    }

    public int getMoney() {
        return this.money;
    }

    public int getySpeed(){
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
