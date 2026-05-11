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
        verifyBuildingJSON(building);
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
            case "heating":
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

    public String getOverheatedRoomsReport(long heatingThreshold, Building building) {
        verifyBuildingJSON(building);
        logger.debug("searches through building");
        ArrayList<Room> overheatedRooms = findOverheatedRooms(heatingThreshold, building);
        logger.debug("formats list of rooms into a string");
        String overheatedRoomsReport = formatOverheatedRooms(overheatedRooms);
        return overheatedRoomsReport;
    }

    public ArrayList<Room> findOverheatedRooms(long heatingThreshold, Building building) {
        ArrayList<Room> overheatedRooms = new ArrayList<Room>();
        logger.debug("[findOverheatedRooms] loops through building");
        for (Level level : building.getChildren()) {
            for (Room room : level.getChildren()) {
                Visitor visitor = visitorHandler("heating", room);
                long roomHeating = visitor.getResult();
                if (roomHeating > heatingThreshold) {
                    overheatedRooms.add(room);
                }
            }
        }
        logger.debug("[findOverheatedRooms] returns found overheated rooms");
        return overheatedRooms;
    }

    public String formatOverheatedRooms(ArrayList<Room> overheatedRooms) {
        //String is immutable, adding text would create every time new object,
        //StringBuilder is mutable with .append()
        StringBuilder exceedingRoomsInfo = new StringBuilder("Rooms with heating exceeding the threshold:\n");
        logger.debug("[formatOverheatedRooms] loops through each overheated room");
        for (Room overheatedRoom : overheatedRooms) {
            String id = String.valueOf(overheatedRoom.getId());
            String name = overheatedRoom.getName();
            exceedingRoomsInfo
                    .append("ID: ")
                    .append(id)
                    .append(", Name: ")
                    .append(name)
                    .append("\n");
        }

        logger.debug("[formatOverheatedRooms] returns report about overheated rooms");
        return exceedingRoomsInfo.toString();
    }

    public void verifyBuildingJSON(Building building) {
        logger.debug("[verifyBuildingJSON] Building Name: {}", building.getName());
        logger.debug("[verifyBuildingJSON] Building ID: {}", building.getId());
        if (building.getChildren() == null){
            logger.debug("[verifyBuildingJSON] Building doesn't have any level!");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Provided Building from JSON doesn't have any level!"
            );
        }
        for (LocationComposite level : building.getChildren()) {
            if (level.getChildren() == null) {
                logger.debug("[verifyBuildingJSON] Level {} doesn't have any room!", level.getId());
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Level " + level.getId() + " doesn't have any room!"
                );
            }
        }
        logger.debug("[verifyBuildingJSON] Building JSON is correct, " +
                     "at least one level exists and each level has at least one room.");
    }
}
