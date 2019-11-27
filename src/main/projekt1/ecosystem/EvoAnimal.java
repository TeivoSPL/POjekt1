package main.projekt1.ecosystem;

import main.projekt1.map.IWorldMap;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.MapDirection;
import main.projekt1.mechanics.MoveDirection;
import main.projekt1.mechanics.Vector2d;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EvoAnimal {

    private MapDirection orientation;
    private Vector2d placement;
    private IWorldMap map;
    private LinkedList<IPositionChangeObserver> observers;
    private Map<String,Double> genes;                           //mapa dla genow, nazwa genu -> wartość
    private int energy;

    public EvoAnimal(IWorldMap map, Vector2d initialPosition){
        this.orientation = MapDirection.NORTH;
        this.observers = new LinkedList<>();
        this.map = map;
        this.placement = initialPosition;
        this.genes = new HashMap<>();
        this.energy = 10;
    }

    @Override
    public String toString() {
        switch (this.orientation) {
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case WEST:
                return "W";
            default:
                return null;
        }
    }

    public void move(MoveDirection dir) {
        Vector2d unitVec = orientation.toUnitVector();
        Vector2d res = new Vector2d(0,0);            //inicjallizuję, żeby intellij nie krzyczał pod switchem, i tak res będzie zdefiniowane
        switch (dir) {
            case FORWARD:
                res = this.placement.add(unitVec);
                break;
            case BACKWARD:
                res = this.placement.subtract(unitVec);
                break;
            case LEFT:
                this.orientation = this.orientation.previous();
                break;
            case RIGHT:
                this.orientation = this.orientation.next();
                break;
        }

        if(dir == MoveDirection.FORWARD || dir == MoveDirection.BACKWARD && this.map!=null && map.canMoveTo(res)){
            this.placement = res;
        }

        this.energy -= 1;
    }

    public void eat(){
        this.energy+=3;
    }

    public int getEnergy() {
        return energy;
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
        for(IPositionChangeObserver o : this.observers){
            o.positionChanged(oldPosition,this.placement);
        }
    }

}
