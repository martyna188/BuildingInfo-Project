package pl.put.poznan.buildinginfo.logic.Composite;

import java.util.ArrayList;

/**
 * An abstract class for specific locations that contain children e.g. Levels
 * Serves as common interface for managing child components
 */
public abstract class LocationComposite extends Location{

    /**
     * Retrieves the collection of Locations contained within this composite.
     * The use of {@code ? extends Location} allows subclasses to return specific type of Children
     * e.g. Levels, Rooms
     * @return an ArrayList of objects extending Location
     */
    public abstract ArrayList<? extends Location> getChildren();
}
