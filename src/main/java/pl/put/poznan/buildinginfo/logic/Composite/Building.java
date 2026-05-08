package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

import java.util.ArrayList;

public class Building extends LocationComposite {

    public Building() {}

    /**
     * Allows for the building to accept the visitor, like inspection
     * @param v visitor
     */
    @Override
    public void accept(Visitor v) {
        for (Location c : children) {
            c.accept(v);
        }
    }

}
