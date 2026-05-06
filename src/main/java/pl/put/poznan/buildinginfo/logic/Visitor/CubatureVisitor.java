package pl.put.poznan.buildinginfo.logic.Visitor;

import pl.put.poznan.buildinginfo.logic.Composite.Room;

public class CubatureVisitor implements Visitor {
    /**
     * Variable used for summing the cubatures.
     * Initially equals 0
     */
    private long total_cubature = 0;

    /**
     * Adds cubatures of a given room to the total cubature
     * @param r room
     */
    @Override
    public void visitRoom(Room r) {
        total_cubature += r.checkCubature();
    }

    /**
     * Returns total cubature of the visited rooms
     * @return total cubature
     */
    @Override
    public long getResult() {
        return total_cubature;
    }
}
