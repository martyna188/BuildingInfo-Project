package pl.put.poznan.buildinginfo.logic.Composite;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scheme {

    private List<Building> buildings = new CopyOnWriteArrayList<>();

    public void setBuildings(List<Building> buildings){this.buildings = buildings;}
    public List<Building> getBuildings(){return this.buildings;}

}
