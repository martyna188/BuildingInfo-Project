package pl.put.poznan.buildinginfo.logic.Composite;

import java.util.ArrayList;

public abstract class LocationComposite extends Location{

    protected ArrayList<Location> children;

    public void setChildren(ArrayList<Location> children) {
        this.children = children;
    }

    public ArrayList<Location> getChildren() {
        return children;
    }
}
