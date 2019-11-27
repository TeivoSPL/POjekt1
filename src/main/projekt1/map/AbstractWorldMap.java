package main.projekt1.map;
import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.MoveDirection;
import main.projekt1.mechanics.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected Map<Vector2d,EvoAnimal> animals;//lista zwierzat i hashcode nie wywolywac, klucz to Vector2d
    protected LinkedList<EvoAnimal> animalOrder;
    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    protected MapBoundary boundary;

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof EvoAnimal);
    }

    @Override
    public boolean place(EvoAnimal animal) {
        if(canMoveTo(animal.getPlacement())){
            this.animals.put(animal.getPlacement(),animal);
            this.animalOrder.add(animal);
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

    @Override
    public void run(MoveDirection[] directions) {

        for(int i=0;i<directions.length;i++){

            EvoAnimal a = animalOrder.get(i%(animalOrder.size()));
            Vector2d pos = a.getPlacement();
            a.move(directions[i]);

            if (!a.getPlacement().equals(pos)) {
                a.positionChanged(pos);
                boundary.xAxis.remove(a);   //nigdy nie wiadomo, kiedy brak aktualizacji wpłynie na wyznaczanie lowerL i upperR
                boundary.xAxis.add(a);
                boundary.yAxis.remove(a);
                boundary.yAxis.add(a);
            }

        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.animals.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(objectAt(oldPosition) instanceof EvoAnimal){
            EvoAnimal a = animals.get(oldPosition);
            animals.remove(oldPosition);
            animals.put(newPosition,a);

        }
    }
}

