package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

import java.util.ArrayList;

public class Building extends Location {

    public Building() {}

    /**
     * Stores levels that the building is composed of.
     */
    private ArrayList<Level> childrenLevels = new ArrayList<>();

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
