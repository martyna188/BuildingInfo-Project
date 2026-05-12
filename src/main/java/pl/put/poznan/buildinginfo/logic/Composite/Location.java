package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

/**
 * Represents a physical node that can be processed by the concrete Visitor
 * <p>
 * This interface defines Element role in the Visitor pattern, providing easy way for algorithms
 * to traverse data without modifying concrete object
 */
public abstract class Location {

    /** The unique identifier for this location.*/
    protected int id;
    /**
     * The human-readable name of this location.
     * Defaults to "not specified" if no name is provided.
     */
    protected String name = "not specified";

    /**
     * @return the unique ID characteristic for each Location
     */
    public int getId() {return id;}
    /**
     * @return human-readable name of the given Location
     */
    public String getName() {return name;}
    /** Setter for unique ID characteristic, used by SpringAPI*/
    public void setId(int id) { this.id = id; }
    /** Setter for human-readable name, used by SpringAPI*/
    public void setName(String name) { this.name = name; }
    /**
     * Accepts Visitor that perform specific operation on this Location
     * @param v the Visitor that will perform operation on this Location
     */
    public abstract void accept(Visitor v);
}
