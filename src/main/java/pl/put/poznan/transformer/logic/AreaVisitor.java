package pl.put.poznan.transformer.logic;

public class AreaVisitor implements Visitor{
    private long total_area = 0;

    @Override
    public void visitRoom(Room r) {
        total_area += r.checkArea();
    }

    @Override
    public long getResult() {
        return total_area;
    }
}
