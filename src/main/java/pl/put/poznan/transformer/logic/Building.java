package pl.put.poznan.transformer.logic;

import java.util.ArrayList;

public class Building implements Location{
    public Building() {}

    private int id;
    private String name;
    private ArrayList<Level> childrenLevels = new ArrayList<>();

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    @Override
    public int getId(){return this.id;}
    @Override
    public String getName(){return this.name;}

    public void setChildrenLevels(ArrayList<Level> levels){
        this.childrenLevels = levels;
    }

    @Override
    public void accept(Visitor v) {
        for (Location c : childrenLevels) {
            c.accept(v);
        }
    }
}
