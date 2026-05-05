package pl.put.poznan.buildinginfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.AreaVisitor;
import pl.put.poznan.buildinginfo.logic.Building;
import pl.put.poznan.buildinginfo.logic.Level;

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

        logger.debug("Adding building to collection: " + building.getName()+ " with id "+ building.getId());
        buildingsCollection.add(building);
        logger.debug("Building send: " + building.getName());
    }


    /**
     * calculating area of the building from collection we already store.
     * @param targetId id of the building from which we want area
     * @return total area of the building
     */
    @RequestMapping(value = "/area/building/{targetId}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaBuilding(@PathVariable int targetId) {

        logger.debug("[getAreaBuilding] Looking for building with ID " + targetId);
        Building targetBuilding = null; //creating 'container' for targetBuilding
        for (Building building : buildingsCollection) {
            if (building.getId() == targetId) {
                targetBuilding = building;
                logger.debug("[getAreaBuilding] Building with id " + targetId + " found.");
                break;
            }
        }

        if (targetBuilding == null) {
            logger.debug("[getAreaBuilding] Building with id " + targetId + " not found.");
            return ResponseEntity.notFound().build().getStatusCodeValue();
        }

        logger.debug("[getAreaBuilding] Creating Area Visitor and calculating area for building");
        AreaVisitor areaVisitor = new AreaVisitor();
        targetBuilding.accept(areaVisitor);
        logger.debug("[getAreaBuilding] Area of: " + targetBuilding.getName() + ": " + areaVisitor.getResult());

        return areaVisitor.getResult();
    }

    /**
     * calculating area of the level from building collection we already store
     * @param targetBuildingId id of the building from which we want level
     * @param targetLevelId id of the level from which we want area
     * @return total area of the level
     */
    @RequestMapping(value = "/area/level/{targetBuildingId}/{targetLevelId}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaLevel(@PathVariable int targetBuildingId, @PathVariable int targetLevelId) {

        logger.debug("[getAreaLevel] Looking for building with ID " + targetBuildingId);
        Building targetBuilding = null; //creating 'container' for targetBuilding
        for (Building building : buildingsCollection) {
            if (building.getId() == targetBuildingId) {
                targetBuilding = building;
                logger.debug("Building with id " + targetBuildingId + " found.");
                break;
            }
        }
        if (targetBuilding == null) {
            logger.debug("[getAreaLevel] Building with id " + targetBuildingId + " not found.");
            return ResponseEntity.notFound().build().getStatusCodeValue();
        }

        logger.debug("[getAreaLevel] Looking for level with ID " + targetLevelId);
        Level targetLevel = null; //creating 'container' for targetLevel
        for (Level level : targetBuilding.getChildrenLevels()) {
            if (level.getId() == targetLevelId) {
                targetLevel = level;
                logger.debug("[getAreaLevel] Level with id " + targetLevelId + " found.");
                break;
            }
        }
        if (targetLevel == null) {
            logger.debug("[getAreaLevel] Building with id " + targetLevelId + " not found.");
            return ResponseEntity.notFound().build().getStatusCodeValue();
        }

        logger.debug("[getAreaLevel] Creating Area Visitor and calculating area for level");
        AreaVisitor areaVisitor = new AreaVisitor();
        targetLevel.accept(areaVisitor);
        logger.debug("[getAreaLevel] Area of: " + targetLevel.getName() + ": " + areaVisitor.getResult());

        return areaVisitor.getResult();
    }

}


