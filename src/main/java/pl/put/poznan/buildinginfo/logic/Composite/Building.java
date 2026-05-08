package pl.put.poznan.buildinginfo.logic.Composite;


import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Building extends LocationComposite {
    public Building() {}

    private ArrayList<Level> childrenLevels = new ArrayList<>();

    public void setChildrenLevels(ArrayList<Level> levels) { this.childrenLevels = levels; }

    @Override
    public ArrayList<Level> getChildren() { return childrenLevels; }
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

}
