package pl.put.poznan.buildinginfo.logic;

import java.util.ArrayList;

public class Building implements Location{

    public Building() {}

    private int id;
    private String name;
    /**
     * Stores levels that the building is composed of.
     */
    private ArrayList<Level> childrenLevels = new ArrayList<>();

    /**
     * Sets Id for the building, used for automatic creation from JSON
     * @param id id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Sets name for the building, used for automatic creation from JSON
     * @param name name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns ID of the building
     * @return ID
     */
    @Override
    public int getId(){return this.id;}

    /**
     * Returns name of the given building
     * @return name
     */
    @Override
    public String getName(){return this.name;}

    /**
     * Sets for the building the levels that it is composed of
     * @param levels levels
     */
    public void setChildrenLevels(ArrayList<Level> levels){
        this.childrenLevels = levels;
    }

    /**
     * Allows for the building to accept the visitor, like inspection
     * @param v visitor
     */
    @Override
    public void accept(Visitor v) {
        for (Location c : childrenLevels) {
            c.accept(v);
        }
    }

    /**
     * Returns as a list all levels that the building is composed of
     * @return childrenLevels
     */
    public ArrayList<Level> getChildrenLevels(){
        return this.childrenLevels;
    }
}
