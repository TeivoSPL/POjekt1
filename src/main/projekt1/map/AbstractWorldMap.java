package main.projekt1.map;
import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected HashMap<Vector2d,LinkedList<EvoAnimal>> animals;

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position).size()>1);
    }

    @Override
    public boolean place(EvoAnimal animal) {
        if(canMoveTo(animal.getPlacement())){
            this.animals.get(animal.getPlacement()).add(animal);
            this.animals.get(animal.getPlacement()).sort(Comparator.comparing(EvoAnimal::getEnergy));
            animal.addObserver(this);

            return true;
        }
        throw new IllegalArgumentException(animal.getPlacement().toString() + " Failed to place animal");
    }

    public void run() {

        //death round
        for(LinkedList<EvoAnimal> animalsOnPosition: animals.values()){

            for(EvoAnimal a : animalsOnPosition)
            {
                if(a.getEnergy()<=0){
                    animals.remove(a);
                }
            }

        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

    public LinkedList<EvoAnimal> objectAt(Vector2d position) {
        return this.animals.get(position);
    }
}

