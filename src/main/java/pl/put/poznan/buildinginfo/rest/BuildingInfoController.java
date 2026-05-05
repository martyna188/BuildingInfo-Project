package pl.put.poznan.buildinginfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.put.poznan.buildinginfo.logic.*;

import java.awt.geom.Area;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@RestController
public class BuildingInfoController {

    /** logger for sending debugging information */
    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);
    /** List  of buildings we store for later access */
    private final List<Building> buildingsCollection = new CopyOnWriteArrayList<>();

    /**
     * function for accepting building for later access
     * @param building input in JSON format with building, levels and rooms (each with id, name
     *                 and room with additional area, volume, light and heating
     */
    @RequestMapping(value = "/building/post", method = RequestMethod.POST, produces = "application/json")
    public void sendBuilding(@RequestBody Building building) {

        logger.debug("Adding building to collection: " + building.getName()+ " with ID "+ building.getId());
        buildingsCollection.add(building);
        logger.debug("Building send: " + building.getName());
    }


    /**
     * calculating area of the building from collection we already store.
     * @param targetBuildingID id of the building from which we want area
     * @return total area of the building
     */
    @RequestMapping(value = "/area/building/{targetBuildingID}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaBuilding(@PathVariable int targetBuildingID) {
        Building targetBuilding = findBuildingExists(targetBuildingID);
        AreaVisitor areaVisitor = creatingAreaVisitor(targetBuilding);
        return areaVisitor.getResult();
    }

    /**
     * calculating area of the level from building collection we already store
     * @param targetBuildingID id of the building from which we want level
     * @param targetLevelID id of the level from which we want area
     * @return total area of the level
     */
    @RequestMapping(value = "/area/level/{targetBuildingID}/{targetLevelID}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaLevel(@PathVariable int targetBuildingID, @PathVariable int targetLevelID) {
        Building targetBuilding = findBuildingExists(targetBuildingID);
        Level targetLevel = findLevelExists(targetBuilding, targetLevelID);
        AreaVisitor areaVisitor = creatingAreaVisitor(targetLevel);
        return areaVisitor.getResult();
    }

    /**
     * calculating area of the room from provided level from building collection we already store
     * @param targetBuildingID id of the building from which we want level
     * @param targetLevelID id of the level from which we want area
     * @param targetRoomID id of the room from which we want area
     * @return area of the room
     */
    @RequestMapping(value = "/area/room/{targetBuildingID}/{targetLevelID}/{targetRoomID}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaRoom(@PathVariable int targetBuildingID, @PathVariable int targetLevelID, @PathVariable int targetRoomID) {
        Building targetBuilding = findBuildingExists(targetBuildingID);
        Level targetLevel = findLevelExists(targetBuilding, targetLevelID);
        Room targetRoom = findRoomExists(targetLevel, targetRoomID);
        AreaVisitor areaVisitor = creatingAreaVisitor(targetRoom);
        return areaVisitor.getResult();
    }

    @RequestMapping(value = "/cubature/building/{targetBuildingID}", method = RequestMethod.GET, produces = "application/json")
    public long getCubatureBuilding(@PathVariable int targetBuildingID) {
        Building targetBuilding = findBuildingExists(targetBuildingID);
        CubatureVisitor cubatureVisitor = creatingCubatureVisitor(targetBuilding);
        return cubatureVisitor.getResult();
    }

    @RequestMapping(value = "/cubature/level/{targetBuildingID}/{targetLevelID}", method = RequestMethod.GET, produces = "application/json")
    public long getCubatureLevel(@PathVariable int targetBuildingID, @PathVariable int targetLevelID) {
        Building targetBuilding = findBuildingExists(targetBuildingID);
        Level targetLevel = findLevelExists(targetBuilding, targetLevelID);
        CubatureVisitor cubatureVisitor = creatingCubatureVisitor(targetLevel);
        return cubatureVisitor.getResult();
    }

    @RequestMapping(value = "/cubature/room/{targetBuildingID}/{targetLevelID}/{targetRoomID}", method = RequestMethod.GET, produces = "application/json")
    public long getCubatureRoom(@PathVariable int targetBuildingID, @PathVariable int targetLevelID, @PathVariable int targetRoomID) {
        Building targetBuilding = findBuildingExists(targetBuildingID);
        Level targetLevel = findLevelExists(targetBuilding, targetLevelID);
        Room targetRoom = findRoomExists(targetLevel, targetRoomID);
        CubatureVisitor cubatureVisitor = creatingCubatureVisitor(targetRoom);
        return cubatureVisitor.getResult();
    }

    private Building findBuildingExists (int targetBuildingId) {
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

    private Level findLevelExists (Building targetBuilding, int targetLevelId) {
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

    private Room findRoomExists (Level targetLevel, int targetRoomId) {
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

    private AreaVisitor creatingAreaVisitor(Location targetLocation) {
        logger.debug("[creatingAreaVisitor] Creating Area Visitor and calculating area for location");
        AreaVisitor areaVisitor = new AreaVisitor();
        targetLocation.accept(areaVisitor);
        logger.debug("[creatingAreaVisitor] Area of: " + targetLocation.getName() + ": " + areaVisitor.getResult());
        return areaVisitor;
    }

    private CubatureVisitor creatingCubatureVisitor(Location targetLocation) {
        logger.debug("[creatingCubatureVisitor] Creating Cubature Visitor and calculating Cubature for location");
        CubatureVisitor cubatureVisitor = new CubatureVisitor();
        targetLocation.accept(cubatureVisitor);
        logger.debug("[creatingCubatureVisitor] Cubature of: " + targetLocation.getName() + ": " + cubatureVisitor.getResult());
        return cubatureVisitor;
    }
}


