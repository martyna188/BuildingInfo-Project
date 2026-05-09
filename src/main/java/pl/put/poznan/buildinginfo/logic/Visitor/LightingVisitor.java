package pl.put.poznan.buildinginfo.logic.Visitor;

import pl.put.poznan.buildinginfo.logic.Composite.Room;

public class LightingVisitor implements Visitor {

    /**
     * Variable used for summing the area.
     * Initially equals 0
     */
    private long total_area = 0;

    /**
     * Variable used for summing the total lighting power.
     * Initially equals 0
     */
    private long total_light = 0;

    /**
     * Adds area of a given room to the total area
     * Adds lighting power of a given room to the total lighting power
     * @param r room
     */
    @Override
    public void visitRoom(Room r) {
        total_area += r.checkArea();
        total_light += r.checkLight();
    }

    /**
     * Calculates lighting power per unit area for a given location
     * @return lighting power per unit area
     */
    @Override
    public long getResult() {
        return total_light/total_area;
    }
}

