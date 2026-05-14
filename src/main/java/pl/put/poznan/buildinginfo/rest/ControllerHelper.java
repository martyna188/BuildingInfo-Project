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
import java.util.ArrayList;
import static pl.put.poznan.buildinginfo.rest.BuildingInfoController.logger;

/**
 * Service class that provides utility functions for processing building structures.
 * This helper acts as the bridge between the REST controller and the core logic,
 * managing location searching, JSON validation, and Visitor orchestration.
 */
@Service
public class ControllerHelper {
    /**
     * Calculating a specific metric for a target location.
     *
     * @param metric   The type of calculation (area, cubature, heating, light).
     * @param targetID The hyphen-separated ID path in format buildingID-levelID-roomID with level and room optional.
     * @param building The building object to search within.
     * @return The calculated value as a long.
     */
    public long calculate(String metric, String targetID, Building building) {
        verifyBuildingJSON(building);
        ArrayList<String> locationIDs = new ArrayList<>(Arrays.asList(targetID.split("-")));
        Location location = locationHandler(building, locationIDs);
        Visitor visitor = visitorHandler(metric, location);
        return visitor.getResult();
    }

    /**
     * Organize the location search and handles potential "Not Found" errors.
     *
     * @param building    The root Building object.
     * @param locationIDs A list of IDs representing the hierarchy path.
     * @return The found {@link Location}.
     * @throws ResponseStatusException if the location does not exist.
     */
    public Location locationHandler(Building building, ArrayList<String> locationIDs){
        Location location = findLocationExists(building, locationIDs, 0);
        if (location == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given Location ID Not Found");
        }
        return location;
    }

    /**
     * Recursively searches for a specific location within the building hierarchy.
     *
     * @param location  The current node being inspected.
     * @param targetIDs The sequence of IDs to match.
     * @param i         The current depth index in the targetIDs list.
     * @return The matched {@link Location}, or null if not found.
     */
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

    /**
     * Factory method that creates the appropriate {@link Visitor} for a metric
     * and triggers the visit operation on a location.
     *
     * @param metric   The string identifying the calculation type.
     * @param location The location to be visited.
     * @return The {@link Visitor} after it has completed its calculations.
     */
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

    /**
     * Generates and control a textual report of rooms where heating exceeds a certain limit.
     *
     * @param heatingThreshold The maximum allowed heating value.
     * @param building         The building to analyze.
     * @return A formatted string report.
     */
    public String getOverheatedRoomsReport(long heatingThreshold, Building building) {
        verifyBuildingJSON(building);
        logger.debug("searches through building");
        ArrayList<Room> overheatedRooms = findOverheatedRooms(heatingThreshold, building);
        logger.debug("formats list of rooms into a string");
        String overheatedRoomsReport = formatOverheatedRooms(overheatedRooms);
        return overheatedRoomsReport;
    }

    /**
     * Traverses the building to identify rooms exceeding the heating threshold.
     *
     * @param heatingThreshold The limit to check against.
     * @param building         The building containing levels and rooms.
     * @return A list of {@link Room} objects that are "overheated".
     */
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

    /**
     * Formats a list of rooms into a readable report string using {@link StringBuilder}.
     *
     * @param overheatedRooms List of rooms to report.
     * @return A formatted multiline string.
     */
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

    /**
     * Validates that the provided building object is properly formed.
     * Ensures that the building has levels and that each level contains rooms.
     *
     * @param building The building to verify.
     * @throws ResponseStatusException if the building structure is empty or invalid.
     */
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
