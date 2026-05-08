package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

import java.util.ArrayList;

public class Level extends LocationComposite {
    public Level() {}

    public ArrayList<Room> childrenRooms = new ArrayList<>();;

    public void setChildrenRooms(ArrayList<Room> rooms) { this.childrenRooms = rooms; }

    @Override
    public ArrayList<Room> getChildren() { return childrenRooms; }
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

}
