package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.ecosystem.Grass;
import main.projekt1.mechanics.Vector2d;

import java.util.LinkedList;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that agh.cs.po_laby.Vector2d and agh.cs.po_laby.MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     */
    void place(EvoAnimal animal);

    /**
     * Move the animal on the map.
     */
    void run();

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return a LinkedList of animals at a given position.
     *
     * @param position
     *            The position of the object.
     * @return LinkedList<EvoAnimal> or null if the position is not occupied.
     */
    LinkedList<EvoAnimal> animalsAt(Vector2d position);

    /**
     * Return grass at a given position.
     *
     * @param position
     *          The position of the object.
     * @return Grass or null if the position has no grass.
     */
    Grass grassAt(Vector2d position);
}
