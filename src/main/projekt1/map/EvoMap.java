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
        Vector2d spos; // zmienic nazwe zmiennej
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
                LinkedList<EvoAnimal> contenders = getStrongest();

                if(contenders.size()>1){
                    for(EvoAnimal c : contenders){
                        c.eat(5/contenders.size());
                    }
                }
                else {
                    contenders.get(0).eat(5);
                }

                grass.remove((a.getPlacement()));
            }
        }

        //reproducing round
        for(EvoAnimal a : this.animals){
            LinkedList<EvoAnimal> partners = objectAt(a.getPlacement());
            if(partners.size()>1 && a.getEnergy()>5){ //dać zmienną na minimalną energię rozmnazania
                //jakiś warunek na wybór partnera??
                for(EvoAnimal partner : partners){
                    if(!partner.equals(a) && partner.getEnergy()>5 && getFreeSpaces()){
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
            animal.eat(5);
        }
    }

    private LinkedList<Vector2d> getFreeSpaces(Vector2d position) {
        LinkedList<Vector2d> result = new LinkedList<>();
        int x = position.getX();
        int y = position.getY();

        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                if(objectAt(new Vector2d(x+i,y+j)).size()==0){
                    result.add(new Vector2d(x+i,y+j));
                }
            }
        }

        return result;
    }

    private LinkedList<EvoAnimal> getStrongest(Vector2d position){
        LinkedList<EvoAnimal> result = new LinkedList<>();
        int maxEnergy = 0;

        for(EvoAnimal a : animals){
            if(a.getPlacement().equals(position) && a.getEnergy()>maxEnergy){
                maxEnergy=a.getEnergy();
            }
        }

        for(EvoAnimal a : animals){
            if(a.getPlacement().equals(position) && a.getEnergy() == maxEnergy){
                result.add(a);
            }
        }

        return result;
    }
}
