package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

import java.util.ArrayList;

public class Level extends LocationComposite {
    public Level() { }

    /**
     * Allows for the level to accept the visitor, like inspection
     * @param v visitor
     */
    @Override
    public void accept(Visitor v) {
        for (Location c : children) {
            c.accept(v);
        }
    }

}
