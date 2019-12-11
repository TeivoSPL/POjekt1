package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.ecosystem.Grass;
import main.projekt1.mechanics.Vector2d;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
        //safecounter is crucial if there is not a lot of space to grow a new plant
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

        //movement round
        for(EvoAnimal a : this.animals){
            a.move();
        }

        //eating round
        for(EvoAnimal a : this.animals){
            if(grass.get(a.getPlacement()) instanceof Grass){
                LinkedList<EvoAnimal> contenders = objectAt(a.getPlacement());
                EvoAnimal winner = a;
                for(EvoAnimal c : contenders){
                    if(c.getEnergy()>winner.getEnergy()){
                        winner = c;
                    }
                }
                winner.eat();
                grass.remove((a.getPlacement()));
            }
        }

        //reproducing round
        for(EvoAnimal a : this.animals){
            LinkedList<EvoAnimal> partners = objectAt(a.getPlacement());
            if(partners.size()>1 && a.getEnergy()>5){ //dać zmienną na minimalną energię rozmnazania
                //jakiś warunek na wybór partnera??
                for(EvoAnimal partner : partners){
                    if(!partner.equals(a) && partner.getEnergy()>5 && !isCrouded(a.getPlacement())){
                        place(a.reproduce(partner));
                    }
                }
            }
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

    private boolean isCrouded(Vector2d position) {
        //tu warunek na brak miejsca wokol pozycji
        return false;
    }
}
