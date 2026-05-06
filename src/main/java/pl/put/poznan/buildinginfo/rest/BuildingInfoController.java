package pl.put.poznan.buildinginfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.Composite.Building;
import pl.put.poznan.buildinginfo.logic.Composite.Level;
import pl.put.poznan.buildinginfo.logic.Composite.Room;
import pl.put.poznan.buildinginfo.logic.Visitor.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@RestController
public class BuildingInfoController {

    /** logger for sending debugging information */
    public static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);
    /** List  of buildings we store for later access */
    private final List<Building> buildingsCollection = new CopyOnWriteArrayList<>();
    /** Helper that store all needed functions for computations*/
    private final ControllerHelper helper;

    /**
     * Constructor for helper which store all needed functions for computations
     * This Constructor is used by Spring and is triggered when applications starts
     * @param helper helper utility for storing all needed functions for calculation
     */
    public BuildingInfoController(ControllerHelper helper) {this.helper = helper;}
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
        Building targetBuilding = helper.findBuildingExists(buildingsCollection, targetBuildingID);
        AreaVisitor areaVisitor = helper.creatingAreaVisitor(targetBuilding);
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
        Building targetBuilding = helper.findBuildingExists(buildingsCollection, targetBuildingID);
        Level targetLevel = helper.findLevelExists(targetBuilding, targetLevelID);
        AreaVisitor areaVisitor = helper.creatingAreaVisitor(targetLevel);
        return areaVisitor.getResult();
    }

    /**
     * calculating area of the room from provided level from building collection we already store
     * @param targetBuildingID id of the building from which we want level
     * @param targetLevelID id of the level from which we want room
     * @param targetRoomID id of the room from which we want area
     * @return area of the room
     */
    @RequestMapping(value = "/area/room/{targetBuildingID}/{targetLevelID}/{targetRoomID}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaRoom(@PathVariable int targetBuildingID, @PathVariable int targetLevelID, @PathVariable int targetRoomID) {
        Building targetBuilding = helper.findBuildingExists(buildingsCollection, targetBuildingID);
        Level targetLevel = helper.findLevelExists(targetBuilding, targetLevelID);
        Room targetRoom = helper.findRoomExists(targetLevel, targetRoomID);
        AreaVisitor areaVisitor = helper.creatingAreaVisitor(targetRoom);
        return areaVisitor.getResult();
    }

    /**
     * calculating cubature of the level from building collection we already store
     * @param targetBuildingID id of the building from which we want cubature
     * @return total cubature of the building
     */
    @RequestMapping(value = "/cubature/building/{targetBuildingID}", method = RequestMethod.GET, produces = "application/json")
    public long getCubatureBuilding(@PathVariable int targetBuildingID) {
        Building targetBuilding = helper.findBuildingExists(buildingsCollection, targetBuildingID);
        CubatureVisitor cubatureVisitor = helper.creatingCubatureVisitor(targetBuilding);
        return cubatureVisitor.getResult();
    }

    /**
     * calculating cubature of the level from building collection we already store
     * @param targetBuildingID id of the building from which we want level
     * @param targetLevelID id of the level from which we want cubature
     * @return total cubature of the level
     */
    @RequestMapping(value = "/cubature/level/{targetBuildingID}/{targetLevelID}", method = RequestMethod.GET, produces = "application/json")
    public long getCubatureLevel(@PathVariable int targetBuildingID, @PathVariable int targetLevelID) {
        Building targetBuilding = helper.findBuildingExists(buildingsCollection, targetBuildingID);
        Level targetLevel = helper.findLevelExists(targetBuilding, targetLevelID);
        CubatureVisitor cubatureVisitor = helper.creatingCubatureVisitor(targetLevel);
        return cubatureVisitor.getResult();
    }

    /**
     * calculating cubature of the room from provided level from building collection we already store
     * @param targetBuildingID id of the building from which we want level
     * @param targetLevelID id of the level from which we want room
     * @param targetRoomID  id of the room from which we want cubature
     * @return cubature of the room
     */
    @RequestMapping(value = "/cubature/room/{targetBuildingID}/{targetLevelID}/{targetRoomID}", method = RequestMethod.GET, produces = "application/json")
    public long getCubatureRoom(@PathVariable int targetBuildingID, @PathVariable int targetLevelID, @PathVariable int targetRoomID) {
        Building targetBuilding = helper.findBuildingExists(buildingsCollection, targetBuildingID);
        Level targetLevel = helper.findLevelExists(targetBuilding, targetLevelID);
        Room targetRoom = helper.findRoomExists(targetLevel, targetRoomID);
        CubatureVisitor cubatureVisitor = helper.creatingCubatureVisitor(targetRoom);
        return cubatureVisitor.getResult();
    }
}


