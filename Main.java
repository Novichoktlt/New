package lesson5.online;

import lesson5.online.Animals.Animals;

import java.util.Random;


public class Main {

    public static final Random RANDOM = new Random();

    public static void main(String[] args) {

        Animals cat = new Cat("Маня", 200, 2);
        Animals dog = new Dog("Крошка", 500, 0.5f, 10);
        Animals hors = new Hors("Валя", 1500, 3, 100);
        Animals fowl = new Fowl("Лиза", 5, 0.2f);
        Animals dog1 = new Dog("Марфуша", runN(), jumpN(), swimN());
        Animals cat1 = new Cat("Василиса", runN(), jumpN());


        Animals[] game = new Animals[6];
        game[0] = cat;
        game[1] = dog;
        game[2] = hors;
        game[3] = fowl;
        game[4] = dog1;
        game[5] = cat1;

        for(int i = 0; i < game.length; i++){
            game[i].run(runN());
            game[i].jump(jumpN());
            game[i].swim(swimN());

        }



    }

    public static float runN() {

        return RANDOM.nextFloat() * 2000;

    }
    public static float jumpN() {

        return RANDOM.nextFloat() * 11;

    }
    public static float swimN() {

        return RANDOM.nextFloat() * 150;

    }

}
