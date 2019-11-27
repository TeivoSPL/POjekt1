package main.projekt1.map;

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

    public void generatePlants(){
        //generate coordinates for plant in jungle
        Vector2d jpos = new Vector2d((int)(Math.random()*10+44),(int)(Math.random()*10+9));
        Grass jg = new Grass(jpos);
        grass.put(jpos,jg);

        //generate coordinates for plant in steppe
        Vector2d spos;
        do {
            spos = new Vector2d((int)(Math.random()*100),(int)(Math.random()*30));
        }while(spos.precedes(new Vector2d(54,19)) && spos.follows(new Vector2d(44,9)));
    }

}
