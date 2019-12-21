package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvoMapTest {

    @Test
    public void run() {
        EvoMap map = new EvoMap(100,30,10,1,5,0.5);

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2),10);
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(2,23),10);
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(0,0),10);

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        map.run();

        assertNull(map.animalsAt(new Vector2d(2,2)));
        assertNull(map.animalsAt(new Vector2d(2,23)));
        assertNull(map.animalsAt(new Vector2d(0,0)));
    }

    @Test
    public void reproduce() {
        EvoMap map = new EvoMap(100,30,10,1,5,0.5);

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2),10);
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(2,2),10);
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(0,0),10);

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        map.reproduce();

//        assertEquals("There should be 4 animals on the map",map.animals.);//tutaj tez nie da sie policzyc zwierzat!
    }

    @Test
    public void getFreeSpaces() {
        EvoMap map = new EvoMap(100,30,10,1,5,0.5);

        EvoAnimal animal1 = new EvoAnimal(map, new Vector2d(2,2),10);
        EvoAnimal animal2 = new EvoAnimal(map, new Vector2d(2,3),10);
        EvoAnimal animal3 = new EvoAnimal(map, new Vector2d(0,0),10);

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        assertEquals("There should be 8 free spaces",map.getFreeSpaces(new Vector2d(0,0)).size(),8);
        assertEquals("There should be 7 free spaces",map.getFreeSpaces(new Vector2d(2,2)).size(),7);
    }
}