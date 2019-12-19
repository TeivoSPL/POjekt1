package main.projekt1.map;
import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected HashMap<Vector2d,LinkedList<EvoAnimal>> animals;

    @Override
    public void place(EvoAnimal animal) {
        if(!this.animals.containsKey(animal.getPlacement())){
            this.animals.put(animal.getPlacement(),new LinkedList<>());
        }

        this.animals.get(animal.getPlacement()).add(animal);
        this.animals.get(animal.getPlacement()).sort(Comparator.comparing(EvoAnimal::getEnergy));
        animal.addObserver(this);
    }

    public void run() {

        //death round
        for(LinkedList<EvoAnimal> animalsOnPosition: animals.values()){

            for(EvoAnimal a : animalsOnPosition)
            {
                if(a.getEnergy()<=0){
                    if(this.animals.get(a.getPlacement()).size()==1){
                        this.animals.remove(a.getPlacement());
                    }
                    else{
                        this.animals.get(a.getPlacement()).remove(a);
                    }
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

