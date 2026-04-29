package pl.put.poznan.buildinginfo.logic;

import java.util.ArrayList;

public class Level implements Location{
    public Level() { }
    private int id;
    private String name;
    private ArrayList<Room> childrenRooms = new ArrayList<>();


    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}


    @Override
    public int getId(){return this.id;}
    @Override
    public String getName(){return this.name;}

    public void setChildrenRooms(ArrayList<Room> rooms){
        this.childrenRooms = rooms;
    }

    @Override
    public void accept(Visitor v) {
        for (Location c : childrenRooms) {
            c.accept(v);
        }
    }
}
