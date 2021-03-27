package ru.myitschool.vsu2020.myecoproject.logic;

import android.content.Context;

public class World {
    private int money = 0;
    Context context;
    public World(Context context){
        this.context = context;
    }
    public int getMoney(){
        return this.money;
    }
    public void addmoney(){
        this.money += 5;
    }
}
