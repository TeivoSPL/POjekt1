package main.projekt1.ecosystem;

import main.projekt1.mechanics.Vector2d;

public class Grass {

    private Vector2d placement;

    public Grass(Vector2d position){
        this.placement = position;
    }

    public Vector2d getPlacement() {
        return placement;
    }

    @Override
    public String toString() {
        return "*";
    }
}
