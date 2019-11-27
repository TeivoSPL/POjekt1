package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.mechanics.IPositionChangeObserver;
import main.projekt1.mechanics.Vector2d;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

    TreeSet<EvoAnimal> xAxis;
    TreeSet<EvoAnimal> yAxis;
    private Comparator xCompare;
    private Comparator yCompare;

    public MapBoundary(){
        Comparator<EvoAnimal> xCompare = (o1, o2) -> {
            if(o1 != null && o2 != null){
                Vector2d v1 = o1.getPlacement();
                Vector2d v2 = o2.getPlacement();
                if(v1.x == v2.x){
                    return (v1.y - v2.y);
                }
                return v1.x - v2.x;
            }
            return 0;
        };
        Comparator<EvoAnimal> yCompare = (o1, o2) -> {
            if(o1 != null && o2 != null){
                Vector2d v1 = o1.getPlacement();
                Vector2d v2 = o2.getPlacement();
                if(v1.y == v2.y){
                    return (v1.x - v2.x);
                }
                return v1.y - v2.y;
            }
            return 0;
        };
        xAxis = new TreeSet<>(xCompare);
        yAxis = new TreeSet<>(yCompare);
    }

    public boolean addObject(Object o){
        if(o instanceof EvoAnimal){
            return xAxis.add((EvoAnimal)o) && yAxis.add((EvoAnimal)o);
        }
        return false;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

}
