package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;
import java.util.ArrayList;

/**
 * Represents Building as a CompositeLocation from Composite Design Pattern
 * Contains multiple Levels as a children
 */
public class Building extends LocationComposite {
    /** Default constructor*/
    public Building() {}
    /** A list of levels within this Building */
    private ArrayList<Level> childrenLevels = new ArrayList<>();

    /**
     * A setter for Levels in this Building, used by SpringAPI
     * @param levels an ArrayList of Levels to be assigned to this Building
     */
    public void setChildrenLevels(ArrayList<Level> levels) { this.childrenLevels = levels; }

    /**
     * @return the list of Levels associated with this Building
     */
    @Override
    public ArrayList<Level> getChildren() { return childrenLevels; }
    /**
     * Allows for the building to accept the visitor, like inspection
     * @param v visitor that will perform operations e.g. calculating area
     */
    @Override
    public void accept(Visitor v) {
        for (Location c : childrenLevels) {
            c.accept(v);
        }
    }
}
