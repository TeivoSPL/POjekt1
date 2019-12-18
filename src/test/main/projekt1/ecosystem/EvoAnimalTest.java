package main.projekt1.ecosystem;

import main.projekt1.map.EvoMap;
import main.projekt1.mechanics.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvoAnimalTest {

    @Test
    public void reproduce() {
        EvoMap map = new EvoMap();

        EvoAnimal mother = new EvoAnimal(map, new Vector2d(2,2));
        EvoAnimal father1 = new EvoAnimal(map, new Vector2d(2,2));
        EvoAnimal father2 = new EvoAnimal(map, new Vector2d(2,1));

        map.place(mother);
        map.place(father1);
        map.place(father2);

        assertNotNull(mother.reproduce(father1));
        assertNull(mother.reproduce(father2));
    }

    @Test
    public void move() {
        EvoMap map = new EvoMap();

        EvoAnimal animal1 = new EvoAnimal(map,new Vector2d(2,2));
        EvoAnimal animal2 = new EvoAnimal(map,new Vector2d(2,2));
        EvoAnimal animal3 = new EvoAnimal(map,new Vector2d(0,0));
        EvoAnimal animal4 = new EvoAnimal(map,new Vector2d(0,29));

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
        map.place(animal4);

        animal1.move();
        animal2.move();
        animal3.move();
        animal4.move();

        assertNotEquals("Position should be other than 2,2!",new Vector2d(2,2),animal1.getPlacement());
        assertNotEquals("Position should be other than 2,2!",new Vector2d(2,2),animal2.getPlacement());
        assertNotEquals("Position should be other than 2,2!",new Vector2d(0,0),animal3.getPlacement());
        assertNotEquals("Position should be other than 2,2!",new Vector2d(0,29),animal4.getPlacement());

    }
}