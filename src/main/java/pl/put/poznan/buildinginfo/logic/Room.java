package pl.put.poznan.buildinginfo.logic;

public class Room implements Location{
    private int id;
    private String name;
    private long roomArea;
    private long roomVolume;
    private long roomLight;
    private long roomHeating;


    @Override
    public int getId() {return this.id;}

    @Override
    public String getName() {return this.name;}

    @Override
    public void accept(Visitor v) {
        v.visitRoom(this);
    }

    public long checkArea(){
        return this.roomArea;
    }
    public long checkVolume(){
        return this.roomVolume;
    }
    public long checkLight(){
        return this.roomLight;
    }
    public long checkHeating(){
        return this.roomHeating;
    }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRoomArea(long roomArea) { this.roomArea = roomArea; }
    public void setRoomVolume(long roomVolume) { this.roomVolume = roomVolume; }
    public void setRoomLight(long roomLight) { this.roomLight = roomLight; }
    public void setRoomHeating(long roomHeating) { this.roomHeating = roomHeating; }
}
