package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.ecosystem.Grass;
import main.projekt1.mechanics.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class EvoMap extends AbstractWorldMap {

    private int width;
    private int height;
    private Map<Vector2d, Grass> grass;

    public EvoMap(){
        this.width = 100;
        this.height = 30;
        this.grass = new HashMap<>();
    }

    /**
        This function generates two plants every time it is called.
     */
    private void generatePlants(){
        //generate coordinates for plant in jungle
        int safeCounter = 0;
        Vector2d jpos;
        do{
            jpos = new Vector2d(
                    (int)(Math.random()*10+((this.width-1)/2-1)),
                    (int)(Math.random()*10+((this.height)/2-1))
            );
            safeCounter++;
        }while(
                this.grass.get(jpos)!=null &&
                safeCounter<100
        );
        if(safeCounter!=100){
            Grass jg = new Grass(jpos);
            grass.put(jpos,jg);
        }

        //generate coordinates for plant in steppe
        safeCounter = 0;
        Vector2d spos;
        do {
            spos = new Vector2d((int)(Math.random()*100),(int)(Math.random()*30));
            safeCounter++;
        }while(
                spos.precedes(new Vector2d(54,19)) &&
                spos.follows(new Vector2d(44,9)) &&
                this.grass.get(spos) != null &&
                safeCounter < this.height*this.width-100
        );
        if(safeCounter < this.height*this.width-100){
            Grass sg = new Grass(spos);
            grass.put(jpos,sg);
        }
    }

    @Override
    public void run() {
        //death round
        super.run();

        //life round
        for(EvoAnimal a : this.animals){
            //movement
            a.move();
        }

        //plant growth round
        this.generatePlants();
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, EvoAnimal animal) {
        if(this.grass.get(newPosition)!=null){
            this.grass.remove(newPosition);
            animal.eat();
        }
    }

    private void reproduce(EvoAnimal mother, EvoAnimal father){
        //...
    }
}
