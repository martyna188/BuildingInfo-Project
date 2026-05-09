package pl.put.poznan.buildinginfo.logic.Visitor;

import pl.put.poznan.buildinginfo.logic.Composite.Room;

public class HeatingVisitor implements Visitor {

    /**
     * Variable used for summing the cubature.
     * Initially equals 0
     */
    private long total_cubature = 0;

    /**
     * Variable used for summing the heating energy consumption.
     * Initially equals 0
     */
    private long total_heat = 0;

    /**
     * Adds area of a given room to the total area
     * Adds heating level of a visited room to the total heating consumption
     * @param r room
     */
    @Override
    public void visitRoom(Room r) {
        total_cubature += r.checkArea();
        total_heat += r.checkHeating();
    }

    /**
     * Calculates heating energy consumption per unit of volume for a given location
     * @return heating energy consumption per unit of volume
     */
    @Override
    public long getResult() {
        return total_heat/total_cubature;
    }
}