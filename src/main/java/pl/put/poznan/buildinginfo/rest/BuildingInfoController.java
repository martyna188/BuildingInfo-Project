package pl.put.poznan.buildinginfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.AreaVisitor;
import pl.put.poznan.buildinginfo.logic.Building;

import java.awt.geom.Area;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@RestController
@RequestMapping("/buildings")
public class BuildingInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);
    private final List<Building> buildingsCollection = new CopyOnWriteArrayList<>();

    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = "application/json")
    public void sendBuilding(@RequestBody Building building) {

        logger.debug("Adding building to collection: " + building.getName()+ " with id "+ building.getId());
        buildingsCollection.add(building);
        logger.debug("Building send: " + building.getName());
    }

    @RequestMapping(value = "/area/{targetId}", method = RequestMethod.GET, produces = "application/json")
    public long getArea(@PathVariable int targetId) {

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

        logger.debug("Creating Area Visitor and calculating area for building");
        AreaVisitor areaVisitor = new AreaVisitor();
        targetBuilding.accept(areaVisitor);
        logger.debug("Area of: " + targetBuilding.getName() + ": " + areaVisitor.getResult());

        return areaVisitor.getResult();
    }

    }


