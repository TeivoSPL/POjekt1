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
        for(LinkedList<EvoAnimal> animalsOnPosition: this.animals.values()){

            for(EvoAnimal a : animalsOnPosition)
            {
                if(a.getEnergy()<=0){
                    this.animals.get(a.getPlacement()).remove(a);

                    if(this.animals.get(a.getPlacement()).size()==0){
                        this.animals.remove(a.getPlacement());
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

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, EvoAnimal animal) {
        this.animals.get(oldPosition).remove(animal);

        if(this.animals.get(oldPosition).size()==0){
            this.animals.remove(oldPosition);
        }

        if(!this.animals.containsKey(newPosition)){
            this.animals.put(newPosition,new LinkedList<>());
        }

        this.animals.get(newPosition).add(animal);
        this.animals.get(newPosition).sort(Comparator.comparing(EvoAnimal::getEnergy));
    }
}

