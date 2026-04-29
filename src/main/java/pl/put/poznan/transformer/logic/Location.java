package pl.put.poznan.transformer.logic;

public interface Location {
    public int getId();
    public String getName();
    public void accept(Visitor v);
}
