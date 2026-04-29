package pl.put.poznan.transformer.logic;

public interface Visitor {
    public void visitRoom(Room r);
    public long getResult();
}
