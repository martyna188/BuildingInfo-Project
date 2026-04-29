package pl.put.poznan.buildinginfo.logic;

public interface Visitor {
    public void visitRoom(Room r);
    public long getResult();
}
