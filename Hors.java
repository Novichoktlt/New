package lesson5.online;

import lesson5.online.Animals.Animals;

public class Hors extends Animals{

    protected float swim;

    Hors(String name, float run, float jump, float swim){
            super(name, run, jump);
        this.swim = swim;
    }

    @Override
    public void swim(float task){
        if(task < this.swim){
            System.out.println(name + " доплыла");
        }else{ System.out.println(name + " не доплыла");
        }
    }
}
