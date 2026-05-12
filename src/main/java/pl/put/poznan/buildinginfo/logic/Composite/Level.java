package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

import java.util.ArrayList;

/**
 * Represents Level as a CompositeLocation from Composite Design Pattern
 * Contains multiple Rooms as a children
 */
public class Level extends LocationComposite {
    /** Default constructor*/
    public Level() {}
    /** A list of rooms within this Level */
    public ArrayList<Room> childrenRooms = new ArrayList<>();;
    /**
     * A setter for Rooms in this Level, used by SpringAPI
     * @param rooms an ArrayList of Rooms to be assigned to this Level
     */
    public void setChildrenRooms(ArrayList<Room> rooms) { this.childrenRooms = rooms; }
    /**
     * @return the list of Rooms associated with this Level
     */
    @Override
    public ArrayList<Room> getChildren() { return childrenRooms; }
    /**
     * Allows for the building to accept the visitor, like inspection
     * @param v visitor that will perform operations e.g. calculating area
     */
    @Override
    public void accept(Visitor v) {
        for (Location c : childrenRooms) {
            c.accept(v);
        }
    }

}
