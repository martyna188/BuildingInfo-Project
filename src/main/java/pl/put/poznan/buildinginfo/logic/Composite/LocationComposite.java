package pl.put.poznan.buildinginfo.logic.Composite;

import java.util.ArrayList;

public abstract class LocationComposite extends Location{

    public abstract ArrayList<? extends Location> getChildren();
}
