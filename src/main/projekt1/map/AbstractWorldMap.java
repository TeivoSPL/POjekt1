package main.projekt1.map;
import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.MoveDirection;
import main.projekt1.mechanics.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected LinkedList<EvoAnimal> animals;
    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    protected MapBoundary boundary;

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position).size()>1);
    }

    @Override
    public boolean place(EvoAnimal animal) {
        if(canMoveTo(animal.getPlacement())){
            this.animals.add(animal);
            this.boundary.addObject(animal);
            animal.addObserver(this);

            if(!animal.getPlacement().follows(this.lowerLeft)){
                lowerLeft = new Vector2d(
                        this.boundary.xAxis.first().getPlacement().x,
                        this.boundary.yAxis.first().getPlacement().y)
                ;
            }
            if(!animal.getPlacement().precedes(this.upperRight)){
                upperRight = new Vector2d(
                        this.boundary.xAxis.last().getPlacement().x,
                        this.boundary.yAxis.last().getPlacement().y
                );
            }

            return true;
        }
        throw new IllegalArgumentException(animal.getPlacement().toString() + " Failed to place animal");
    }

    public void run() {

        //death round
        for(EvoAnimal a : animals){

            if(a.getEnergy()<=0){
                animals.remove(a.getPlacement());
                animals.remove(a);
            }

        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

    @Override
    public LinkedList<EvoAnimal> objectAt(Vector2d position) {
        LinkedList<EvoAnimal> result = new LinkedList<>();
        for(EvoAnimal a : this.animals){
            if(a.getPlacement().equals(position)){
                result.add(a);
            }
        }
        return result;
    }
}

