package main.projekt1.ecosystem;

import main.projekt1.map.IWorldMap;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.MapDirection;
import main.projekt1.mechanics.MoveDirection;
import main.projekt1.mechanics.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EvoAnimal {

    private MapDirection orientation;
    private Vector2d placement;
    private IWorldMap map;
    private LinkedList<IPositionChangeObserver> observers;
    private int energy;
    private ArrayList<Integer> genome;

    public EvoAnimal(IWorldMap map, Vector2d initialPosition){
        this.orientation = MapDirection.NORTH;
        this.observers = new LinkedList<>();
        this.map = map;
        this.placement = initialPosition;
        this.energy = 10;
        this.genome = new ArrayList<>();
        for(int i=0;i<32;i++){
            genome.add((int)(Math.random()*8));
        }
        this.genome.sort(Integer::compareTo);
    }

    @Override
    public String toString() {
        return "m";
    }

    public void move() {
        int rotation = this.genome.get((int)(Math.random()*32));
        for(int i=0;i<=rotation;i++){
            this.orientation = this.orientation.next();
        }


        Vector2d unitVec = this.orientation.toUnitVector();
        this.placement = this.placement.add(unitVec);

        this.energy -= 1;
    }

    public void eat(){
        this.energy+=3;
    }

    public int getEnergy() {
        return energy;
    }

    public void live() {
        this.energy -= 1;
    }

    public Vector2d getPlacement(){
        return this.placement;
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    private void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition){
        if (!oldPosition.equals(this.placement)){
            for(IPositionChangeObserver o : this.observers){
                o.positionChanged(oldPosition,this.placement);
            }
        }
    }

}
