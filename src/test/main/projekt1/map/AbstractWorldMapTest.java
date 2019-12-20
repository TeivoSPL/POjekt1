package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractWorldMapTest {

    @Test
    public void place() {
        EvoMap map = new EvoMap();

        assertEquals("Initial animals size should be 0",0,map.animalsOnPosition.size());

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2));
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(200,200));
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(-1,-1));

        map.place(animal1);
        assertEquals("Animals size should be 1",1,map.animalsOnPosition.size());

        map.place(animal2);
        assertEquals("Animals size should be 2",2,map.animalsOnPosition.size());

        map.place(animal3);
        assertEquals("Animals size should be 3",3,map.animalsOnPosition.size());
    }

    @Test
    public void isOccupied() {
        EvoMap map = new EvoMap();

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2));
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(2,23));
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(0,0));

        map.place(animal1);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertTrue(map.isOccupied(new Vector2d(2,23)));
        assertTrue(map.isOccupied(new Vector2d(0,0)));

        assertFalse(map.isOccupied(new Vector2d(0,1)));
        assertFalse(map.isOccupied(new Vector2d(1,0)));
    }

    @Test
    public void objectAt() {
        EvoMap map = new EvoMap();

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2));
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(2,23));
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(0,0));

        map.place(animal1);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        assertNotNull(map.objectAt(new Vector2d(2,2)));
        assertNotNull(map.objectAt(new Vector2d(2,23)));
        assertNotNull(map.objectAt(new Vector2d(0,0)));

        assertNull(map.objectAt(new Vector2d(0,1)));
        assertNull(map.objectAt(new Vector2d(1,0)));
    }


}