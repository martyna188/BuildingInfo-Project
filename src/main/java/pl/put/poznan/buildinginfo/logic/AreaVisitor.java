package pl.put.poznan.buildinginfo.logic;

public class AreaVisitor implements Visitor{
    /**
     * Variable used for summing the area.
     * Initially equals 0
     */
    private long total_area = 0;

    /**
     * Adds area of a given room to the total area
     * @param r room
     */
    @Override
    public void visitRoom(Room r) {
        total_area += r.checkArea();
    }

    /**
     * Returns total area of the visited rooms
     * @return total area
     */
    @Override
    public long getResult() {
        return total_area;
    }
}
