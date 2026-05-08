package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

/**
 * Represents a physical node that can be processed by the concrete Visitor
 * <p>
 * This interface defines Element role in the Visitor pattern, providing easy way for algorithms
 * to traverse data without modifying concrete object
 */
public abstract class Location {

    protected int id;
    protected String name = "not specified";

    /**
     * @return the unique ID characteristic for each Location
     */
    public int getId() {return id;}
    /**
     * @return human-readable name of the given Location
     */
    public String getName() {return name;}

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    /**
     * Accepts Visitor that perform specific operation on this Location
     * @param v the Visitor that will perform operation on this Location
     */
    public abstract void accept(Visitor v);
}
