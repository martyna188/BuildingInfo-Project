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
    public Building findBuildingExists (List<Building> buildingsCollection, int targetBuildingId) {
        logger.debug("[findBuildingExists] Looking for building with ID " + targetBuildingId);
        for (Building building : buildingsCollection) {
            if (building.getId() == targetBuildingId) {
                logger.debug("Building with id " + targetBuildingId + " found.");
                return building;
            }
        }
        logger.debug("[findBuildingExists] Building with id " + targetBuildingId + " not found.");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Building ID not found");
    }

    public Level findLevelExists (Building targetBuilding, int targetLevelId) {
        logger.debug("[findLevelExists] Looking for Level with ID " + targetLevelId);
        for (Level level : targetBuilding.getChildrenLevels()) {
            if (level.getId() == targetLevelId) {
                logger.debug("Level with id " + targetLevelId + " found.");
                return level;
            }
        }
        logger.debug("[findLevelExists] Level with id " + targetLevelId + " not found.");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Level ID not found");
    }

    public Room findRoomExists (Level targetLevel, int targetRoomId) {
        logger.debug("[findRoomExists] Looking for Room with ID " + targetRoomId);
        for (Room targetRoom : targetLevel.getChildrenRooms()) {
            if (targetRoom.getId() == targetRoomId) {
                logger.debug("Room with id " + targetRoomId + " found.");
                return targetRoom;
            }
        }
        logger.debug("[findRoomExists] Room with id " + targetRoomId + " not found.");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room ID not found");
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
