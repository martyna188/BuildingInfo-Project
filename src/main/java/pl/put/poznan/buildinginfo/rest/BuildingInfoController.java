package pl.put.poznan.buildinginfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.Composite.Building;
import pl.put.poznan.buildinginfo.logic.Composite.LocationComposite;
import pl.put.poznan.buildinginfo.logic.Composite.Room;


/**
 * REST Controller that provides an interface for managing and querying building information.
 * It handles incoming HTTP requests to process building structures and
 * retrieves calculations from ControllerHelper
 */
@RestController
public class BuildingInfoController {

    /** logger for sending debugging information */
    public static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);
    /** Helper that store all needed functions for computations*/
    private final ControllerHelper helper;

    /**The current building state stored in memory.*/
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
        logger.debug("[sendBuilding] receives json");
        this.building = building;
    }

    /**
     * Calculates a specific metric for a given location within the building.
     *
     * @param metric   The type of calculation to perform e.g. "area", "volume", "light".
     * @param targetID The unique ID of the building, level, or room to analyze
     *                 in format {buildingID}-{levelID}-{roomID}, with level and room ID being optional
     * @return The calculated value as a long.
     */
    @RequestMapping(value = "/{metric}/{targetID}", method = RequestMethod.GET, produces = "application/json")
    public long getMetric(@PathVariable String metric, @PathVariable String targetID) {
        logger.debug("[getMetric] receives request");
        return helper.calculate(metric, targetID, building);
    }

    /**
     * Generates a report of rooms that exceed a specified heating threshold.
     *
     * @param parameter The heating limit used to identify overheated rooms(threshold).
     * @return A JSON-formatted string report of rooms exceeding the limit.
     */
    @RequestMapping(value = "/overheating/{parameter}", method = RequestMethod.GET, produces = "application/json")
    public String getExceedingHeating(@PathVariable long parameter) {
        logger.debug("[getExceedingHeating] receives request");
        return helper.getOverheatedRoomsReport(parameter, building);
    }
}


