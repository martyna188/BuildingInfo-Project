package pl.put.poznan.buildinginfo.logic;

/**
 * Defines an operation to be performed on the elements of Room.
 * <p>
 * The Visitor pattern allows for adding new operations on Room without modifying it
 */
public interface Visitor {

    /**
     * Performs the visitor's specific logic on Room instance
     * @param r the Room object (must not be null)
     */

    public void visitRoom(Room r);

    /**
     * Retrieves final calculated result across all triggered objects
     * @return a long representing calculated metrics
     */
    public long getResult();
}
