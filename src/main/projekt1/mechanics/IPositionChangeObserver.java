package main.projekt1.mechanics;

import main.projekt1.ecosystem.EvoAnimal;

public interface IPositionChangeObserver {

    void positionChanged(Vector2d oldPosition, Vector2d newPosition, EvoAnimal animal);

}
