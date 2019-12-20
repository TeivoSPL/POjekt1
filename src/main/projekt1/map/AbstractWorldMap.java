package main.projekt1.map;
import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected HashMap<Vector2d,LinkedList<EvoAnimal>> animalsOnPosition;
    protected LinkedList<EvoAnimal> animalsOnMap;

    @Override
    public void place(EvoAnimal animal) {
        if(!this.animalsOnPosition.containsKey(animal.getPlacement())){
            this.animalsOnPosition.put(animal.getPlacement(),new LinkedList<>());
        }

        this.animalsOnPosition.get(animal.getPlacement()).add(animal);
        this.animalsOnPosition.get(animal.getPlacement()).sort(Comparator.comparing(EvoAnimal::getEnergy));

        this.animalsOnMap.add(animal);

        animal.addObserver(this);
    }

    public void run() {

        //death round
        for(LinkedList<EvoAnimal> animalsOnPosition: this.animalsOnPosition.values()){

            for(EvoAnimal a : animalsOnPosition)
            {
                if(a.getEnergy()<=0){
                    this.animalsOnPosition.get(a.getPlacement()).remove(a);
                    this.animalsOnMap.remove(a);

                    if(this.objectAt(a.getPlacement()).size()==0){
                        this.animalsOnPosition.remove(a.getPlacement());
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
        return this.animalsOnPosition.get(position);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, EvoAnimal animal) {
        this.animalsOnPosition.get(oldPosition).remove(animal);

        if(this.animalsOnPosition.get(oldPosition).size()==0){
            this.animalsOnPosition.remove(oldPosition);
        }

        if(!this.animalsOnPosition.containsKey(newPosition)){
            this.animalsOnPosition.put(newPosition,new LinkedList<>());
        }

        this.animalsOnPosition.get(newPosition).add(animal);
        this.animalsOnPosition.get(newPosition).sort(Comparator.comparing(EvoAnimal::getEnergy));
    }
}

