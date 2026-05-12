package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

/**
 * Represents a specific room in a building.
 * This class acts as a Leaf in the Composite Design Pattern. It contains
 * the actual physical data (area, cubature, etc.) that the {@link Visitor} will use for calculations.
 */
public class Room extends Location {

    /** The floor area of the room in square meters. */
    private long area;
    /** The volume (cubature) of the room in cubic meters. */
    private long cubature;
    /** The lighting power in the room. */
    private long light;
    /** The energy consumption for heating. */
    private long heating;

    /**
     * Implements the Visitor Design Pattern.
     * This method allows the visitor to perform a concrete operation specifically defined for a {@code Room}.
     *
     * @param v The {@link Visitor} performing an operation on this room.
     */
    @Override
    public void accept(Visitor v) {
        v.visitRoom(this);
    }

    /** @return The area of this room. */
    public long checkArea(){
        return this.area;
    }
    /** @return The cubature of this room. */
    public long checkCubature(){
        return this.cubature;
    }
    /** @return The lighting power in this room. */
    public long checkLight(){
        return this.light;
    }
    /** @return The heating energy consumption of this room. */
    public long checkHeating(){
        return this.heating;
    }
    /**
     * A setter used by SpringAPI
     * @param area The area value to set. */
    public void setArea(long area) { this.area = area; }
    /**
     * A setter used by SpringAPI
     * @param cubature The cubature value to set. */
    public void setCubature(long cubature) { this.cubature = cubature; }
    /**
     * A setter used by SpringAPI
     * @param light The light value to set. */
    public void setLight(long light) { this.light = light; }
    /**
     * A setter used by SpringAPI
     * @param heating The heating value to set. */
    public void setHeating(long heating) { this.heating = heating; }
}
