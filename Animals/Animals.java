package lesson5.online.Animals;

public abstract class Animals {

    protected String name;
    protected float run;
    protected float jump;


    protected Animals(String name, float run, float jump){
        this.name = name;
        this.run = run;
        this.jump = jump;

    }

   public void run(float task){
        if(task < run){
            System.out.println(name + " добежала");
        }else{ System.out.println(name + " не добежала");}
   }
    public void jump(float task){
        if(task < jump){
            System.out.println(name + " допрыгнула");
        }else{ System.out.println(name + " не допрыгнула");}
    }

    abstract public void swim(float task);

}
