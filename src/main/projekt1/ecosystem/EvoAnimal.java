package main.projekt1.ecosystem;

import main.projekt1.map.EvoMap;
import main.projekt1.map.IWorldMap;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.MapDirection;
import main.projekt1.mechanics.Vector2d;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class EvoAnimal {

    private MapDirection orientation;
    private Vector2d placement;
    private EvoMap map;
    private LinkedList<IPositionChangeObserver> observers;
    private int energy;
    private ArrayList<Integer> genome;

    public EvoAnimal(EvoMap map, Vector2d initialPosition, int energy){
        this.orientation = MapDirection.NORTH;      //dodać randomowość
        this.observers = new LinkedList<>();
        this.map = map;
        this.placement = this.adjustPosition(initialPosition);
        this.energy = energy;
        this.genome = new ArrayList<>();
        for(int i=0;i<32;i++){
            genome.add((int)(Math.random()*8));
        }
        this.genome.sort(Integer::compareTo);
    }

    public EvoAnimal(EvoMap map, Vector2d initialPosition, ArrayList<Integer> genome, MapDirection orientation, int energy){
        this(map, initialPosition,energy);
        this.genome = genome;
        this.genome.sort(Integer::compareTo);
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "m";
    }

    public EvoAnimal reproduce(EvoAnimal partner){
        if(this.placement.equals(partner.getPlacement())){

            int energy=this.energy/4;   //sprawdzic to!!
            energy += partner.energy/4;

            this.energy -= energy-partner.energy/4;
            partner.energy -= energy-this.energy/3;

            ArrayList<Integer> genome = new ArrayList<>();
            int firstStep = (int) (Math.random() * 30);
            int secondStep = (int) (Math.random() * (32-firstStep))+firstStep+1;
            for(int i=0;i<32;i++){
                if(i<=firstStep){
                    genome.add(this.genome.get(i));
                }
                if(i>firstStep && i<=secondStep){
                    genome.add(partner.genome.get(i));
                }
                if(i>secondStep){
                    genome.add(this.genome.get(i));
                }
            }

            Object[] possiblePlacements = this.map.getFreeSpaces(this.placement).toArray();
            Vector2d newPlacement = (Vector2d)possiblePlacements[(int)(Math.random()*possiblePlacements.length)];

            return new EvoAnimal(this.map, newPlacement, genome, this.orientation, energy);
        }
        return null;
    }

    public void move(int moveEnergy) {
        int rotation = this.genome.get((int)(Math.random()*32));
        for(int i=0;i<rotation;i++){
            this.orientation = this.orientation.next();
        }

        Vector2d newPosition = this.adjustPosition(this.placement.add(this.orientation.toUnitVector()));
        Vector2d oldPosition = this.placement;

        this.placement = newPosition;
        this.positionChanged(oldPosition);

        this.live(moveEnergy);
    }

    public void eat(int energyGain){
        this.energy+=energyGain;
    }

    private void live(int energySpent) {
        this.energy -= energySpent;
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

    private void positionChanged(Vector2d oldPosition){
        for(IPositionChangeObserver o : this.observers){
            o.positionChanged(oldPosition,this.placement, this);
        }
    }

    private Vector2d adjustPosition(Vector2d position){
        return new Vector2d(
                position.getX()%this.map.getWidth(),
                position.getY()%this.map.getHeight()
        );
    }

}
