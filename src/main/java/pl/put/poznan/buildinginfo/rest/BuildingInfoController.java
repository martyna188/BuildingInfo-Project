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

    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);
    private final List<Building> buildingsCollection = new CopyOnWriteArrayList<>();

    @RequestMapping(value = "/building/post", method = RequestMethod.POST, produces = "application/json")
    public void sendBuilding(@RequestBody Building building) {

        logger.debug("Adding building to collection: " + building.getName()+ " with id "+ building.getId());
        buildingsCollection.add(building);
        logger.debug("Building send: " + building.getName());
    }

    @RequestMapping(value = "/area/building/{targetId}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaBuilding(@PathVariable int targetId) {

        logger.debug("Looking for building with ID " + targetId);
        Building targetBuilding = null;
        for (Building building : buildingsCollection) {
            if (building.getId() == targetId) {
                targetBuilding = building;
                logger.debug("Building with id " + targetId + " found.");
                break;
            }
        }
        if (targetBuilding == null) {
            logger.debug("Building with id " + targetId + " not found.");
            return ResponseEntity.notFound().build().getStatusCodeValue();
        }

        logger.debug("Creating Area Building Visitor and calculating area for building");
        AreaVisitor areaVisitor = new AreaVisitor();
        targetBuilding.accept(areaVisitor);
        logger.debug("Area of: " + targetBuilding.getName() + ": " + areaVisitor.getResult());

        return areaVisitor.getResult();
    }

    @RequestMapping(value = "/area/level/{targetBuildingId}/{targetLevelId}", method = RequestMethod.GET, produces = "application/json")
    public long getAreaLevel(@PathVariable int targetBuildingId, @PathVariable int targetLevelId) {

        logger.debug("Looking for building with ID " + targetBuildingId);
        Building targetBuilding = null;
        for (Building building : buildingsCollection) {
            if (building.getId() == targetBuildingId) {
                targetBuilding = building;
                logger.debug("Building with id " + targetBuildingId + " found.");
                break;
            }
        }
        if (targetBuilding == null) {
            logger.debug("Building with id " + targetBuildingId + " not found.");
            return ResponseEntity.notFound().build().getStatusCodeValue();
        }

        logger.debug("Looking for level with ID " + targetLevelId);
        Level targetLevel = null;
        for (Level level : targetBuilding.getChildrenLevels()) {
            if (level.getId() == targetLevelId) {
                targetLevel = level;
                logger.debug("Level with id " + targetLevelId + " found.");
                break;
            }
        }
        if (targetLevel == null) {
            logger.debug("Building with id " + targetLevelId + " not found.");
            return ResponseEntity.notFound().build().getStatusCodeValue();
        }

        logger.debug("Creating Area Level Visitor and calculating area for level");
        AreaVisitor areaVisitor = new AreaVisitor();
        targetLevel.accept(areaVisitor);
        logger.debug("Area of: " + targetLevel.getName() + ": " + areaVisitor.getResult());

        return areaVisitor.getResult();
    }

}


