package pl.put.poznan.buildinginfo.logic.Composite;

import pl.put.poznan.buildinginfo.logic.Visitor.Visitor;

public class Room extends Location {

    private long area;
    private long cubature;
    private long light;
    private long heating;

    @Override
    public void accept(Visitor v) {
        v.visitRoom(this);
    }

    public long checkArea(){
        return this.area;
    }
    public long checkCubature(){
        return this.cubature;
    }
    public long checkLight(){
        return this.light;
    }
    public long checkHeating(){
        return this.heating;
    }

    public void setArea(long area) { this.area = area; }
    public void setCubature(long cubature) { this.cubature = cubature; }
    public void setLight(long light) { this.light = light; }
    public void setHeating(long heating) { this.heating = heating; }
}
