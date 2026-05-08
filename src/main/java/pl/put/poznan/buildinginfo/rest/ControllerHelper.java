package pl.put.poznan.buildinginfo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.put.poznan.buildinginfo.logic.Composite.Building;
import pl.put.poznan.buildinginfo.logic.Composite.Level;
import pl.put.poznan.buildinginfo.logic.Composite.Location;
import pl.put.poznan.buildinginfo.logic.Composite.Room;
import pl.put.poznan.buildinginfo.logic.Visitor.AreaVisitor;
import pl.put.poznan.buildinginfo.logic.Visitor.CubatureVisitor;
import java.util.List;
import static pl.put.poznan.buildinginfo.rest.BuildingInfoController.logger;

@Service
public class ControllerHelper {

    public long calculateArea(String targetID) {
        ArrayList<String> locationIDs = new ArrayList<>(Array.asList(targetID.split("-")));
        Location location = locationHandler(building, locationIDs);

    }

    public Location locationHandler(Building building, ArrayList<String> locationIDs){
        Location location = findLocationExists(building, locationIDs);
        if (location == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given Location ID Not Found");
        }
        return location;
    }

    public Location findLocationExists(Location location, ArrayList<String> targetIDs) {
        if (targetIDs.isEmpty()) {
            throw new RuntimeException("Method has a bug or user didn't specify any location ID");
        }

        int targetID = Integer.parseInt(targetIDs.remove(0));

        if (location.getId() == targetID) {
            if (targetIDs.isEmpty()) {
                return location;
            }
            if (location instanceof CompositeLocation) {
                CompositeLocation composite = (CompositeLocation) location;
                for (Location child : composite.getChildren()) {
                    Location result = findLocationExists(child, targetIDs);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public AreaVisitor creatingAreaVisitor(Location targetLocation) {
        logger.debug("[creatingAreaVisitor] Creating Area Visitor and calculating area for location");
        AreaVisitor areaVisitor = new AreaVisitor();
        targetLocation.accept(areaVisitor);
        logger.debug("[creatingAreaVisitor] Area of: " + targetLocation.getName() + ": " + areaVisitor.getResult());
        return areaVisitor;
    }

    public CubatureVisitor creatingCubatureVisitor(Location targetLocation) {
        logger.debug("[creatingCubatureVisitor] Creating Cubature Visitor and calculating Cubature for location");
        CubatureVisitor cubatureVisitor = new CubatureVisitor();
        targetLocation.accept(cubatureVisitor);
        logger.debug("[creatingCubatureVisitor] Cubature of: " + targetLocation.getName() + ": " + cubatureVisitor.getResult());
        return cubatureVisitor;
    }
}
