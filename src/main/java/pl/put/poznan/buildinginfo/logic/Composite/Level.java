package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

import java.util.ArrayList;

public class Level extends Location {
    public Level() { }

    /**
     * List of all rooms that the level is composed of
     */
    private ArrayList<Room> childrenRooms = new ArrayList<>();

    /**
     * Sets for the level all rooms that it is composed of
     * @param rooms rooms
     */
    public void setChildrenRooms(ArrayList<Room> rooms){
        this.childrenRooms = rooms;
    }

    /**
     * Allows for the level to accept the visitor, like inspection
     * @param v visitor
     */
    @Override
    public void accept(Visitor v) {
        for (Location c : childrenRooms) {
            c.accept(v);
        }
    }

    public ArrayList<Room> getChildrenRooms(){
        return this.childrenRooms;
    }
}
