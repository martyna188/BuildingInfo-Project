package pl.put.poznan.buildinginfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.Composite.Building;
import pl.put.poznan.buildinginfo.logic.Composite.LocationComposite;


@RestController
public class BuildingInfoController {

    /** logger for sending debugging information */
    public static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);
    /** Helper that store all needed functions for computations*/
    private final ControllerHelper helper;

    private Building building;

    /**
     * Constructor for helper which store all needed functions for computations
     * This Constructor is used by Spring and is triggered when applications starts
     * @param helper helper utility for storing all needed functions for calculation
     */
    public BuildingInfoController(ControllerHelper helper) {
        this.helper = helper;
    }

    /**
     * function for accepting building for later access
     * @param building input in JSON format of building with levels and rooms (each with id, name
     *                 and room with additional area, volume, light and heating
     */
    @RequestMapping(value = "/building/post", method = RequestMethod.POST, produces = "application/json")
    public void sendBuilding(@RequestBody Building building) {
        this.building = building;

        logger.debug("--- [DEBUG] Received Building Data ---");
        logger.debug("Building Name: {}", building.getName());
        logger.debug("Building ID: {}", building.getId());

        if (building.getChildren() != null) {
            logger.debug("Number of Levels: {}", building.getChildren().size());
            for (LocationComposite level : building.getChildren()){
                logger.debug("Number of Rooms on level {}: {}", level.getId() ,level.getChildren().size());}
        } else {
            logger.error("Building contains NO levels! Check your JSON structure.");
        }
        logger.debug("--- [DEBUG] End of Building Report ---");
    }

    @RequestMapping(value = "/{metric}/{targetID}", method = RequestMethod.GET, produces = "application/json")
    public long getArea(@PathVariable String metric, @PathVariable String targetID) {
        logger.debug("Calculating");
        return helper.calculate(metric, targetID, building);

    }
}


