package ru.myitschool.vsu2020.myecoproject.logic;

public class World {
    private int money;

    public void setMoney(int money){
        this.money = money;
    }
    public int getMoney(){
        return this.money;
    }
    public void addmoney(){
        this.money += 1;
    }
}
