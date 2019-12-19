package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractWorldMapTest {

    @Test
    public void place() {
        EvoMap map = new EvoMap();

        assertEquals("Initial animals size should be 0",0,map.animals.size());

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2));
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(200,200));
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(-1,-1));

        map.place(animal1);
        assertEquals("Animals size should be 1",1,map.animals.size());

        map.place(animal2);
        assertEquals("Animals size should be 2",2,map.animals.size());

        map.place(animal3);
        assertEquals("Animals size should be 3",3,map.animals.size());
    }

    @Test
    public void run() {
        EvoMap map = new EvoMap();

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2));
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(2,23));
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(0,0));

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        map.run();

        assertNull(map.objectAt(new Vector2d(2,2)));
        assertNull(map.objectAt(new Vector2d(2,23)));
        assertNull(map.objectAt(new Vector2d(0,0)));
    }

    @Test
    public void isOccupied() {
    }

    @Test
    public void objectAt() {
    }
}