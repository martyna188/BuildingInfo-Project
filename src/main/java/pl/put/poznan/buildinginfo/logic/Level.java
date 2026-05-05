package pl.put.poznan.buildinginfo.logic;

import java.util.ArrayList;

public class Level implements Location{
    public Level() { }
    private int id;
    private String name;
    /**
     * List of all rooms that the level is composed of
     */
    private ArrayList<Room> childrenRooms = new ArrayList<>();

    /**
     * Sets id for the level, used for automatic creation from JSON
     * @param id id
     */
    public void setId(int id) {this.id = id;}

    /**
     * Sets name for the level, used for automatic creation from JSON
     * @param name name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Returns id of the level
     * @return id
     */
    @Override
    public int getId(){return this.id;}

    /**
     * Return name of the level
     * @return level
     */
    @Override
    public String getName(){return this.name;}

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
}
