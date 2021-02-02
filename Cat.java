package lesson5.online;

import lesson5.online.Animals.Animals;

public class Cat extends Animals {

    Cat(String name, float run, float jump) {
        super(name, run, jump);
    }
    @Override
    public void swim(float task) {
        System.out.println(name + " не умет плавать");
    }





}
