package pl.put.poznan.buildinginfo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.put.poznan.buildinginfo.logic.Composite.LocationComposite;
import pl.put.poznan.buildinginfo.logic.Composite.Building;
import pl.put.poznan.buildinginfo.logic.Composite.Level;
import pl.put.poznan.buildinginfo.logic.Composite.Location;
import pl.put.poznan.buildinginfo.logic.Composite.Room;
import pl.put.poznan.buildinginfo.logic.Visitor.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static pl.put.poznan.buildinginfo.rest.BuildingInfoController.logger;

@Service
public class ControllerHelper {
    public long calculate(String metric, String targetID, Building building) {
        ArrayList<String> locationIDs = new ArrayList<>(Arrays.asList(targetID.split("-")));
        Location location = locationHandler(building, locationIDs);
        Visitor visitor = visitorHandler(metric, location);
        return visitor.getResult();
    }

    public Location locationHandler(Building building, ArrayList<String> locationIDs){
        Location location = findLocationExists(building, locationIDs, 0);
        if (location == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given Location ID Not Found");
        }
        return location;
    }

    public Location findLocationExists(Location location, ArrayList<String> targetIDs, int i) {
        if (targetIDs.isEmpty()) {
            throw new RuntimeException("Method has a bug or user didn't specify any location ID");
        }
        int targetID = Integer.parseInt(targetIDs.get(i));
        if (location.getId() == targetID) {
            if (i == targetIDs.size()-1) {
                return location;
            }
            if (location instanceof LocationComposite) {
                LocationComposite composite = (LocationComposite) location;
                for (Location child : composite.getChildren()) {
                    Location result = findLocationExists(child, targetIDs, i+1);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public Visitor visitorHandler(String metric, Location location) {
        Visitor visitor;

        switch (metric) {
            case "area":
                visitor = new AreaVisitor();
                break;
            case "cubature":
                visitor = new CubatureVisitor();
                break;
            case "heat":
                visitor = new HeatingVisitor();
                break;
            case "light":
                visitor = new LightingVisitor();
                break;
            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provided metric is not supported.");

        }
        logger.debug("[visitorHandler] Created Visitor for: {}", metric);

        location.accept(visitor);
        logger.debug("[visitorHandler] Calculated {} for: {}", metric, location.getName());

        return visitor;
    }
}
