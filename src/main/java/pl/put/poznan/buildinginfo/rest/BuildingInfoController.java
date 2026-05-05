package pl.put.poznan.buildinginfo.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.AreaVisitor;
import pl.put.poznan.buildinginfo.logic.Building;


@RestController
@RequestMapping("/buildings")
public class BuildingInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);
    private AreaVisitor areaVisitor;

    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = "application/json")
    public void sendArea(@RequestBody Building building) {

        // log the parameters
        logger.debug("Sending building: " + building.getName());

        // perform the transformation, you should run your logic here, below is just a silly example
        this.areaVisitor = new AreaVisitor();
        building.accept(areaVisitor);

        logger.debug("Building send: " + building.getName());
    }

    @RequestMapping(value = "/area", method = RequestMethod.GET, produces = "application/json")
    public long getArea(@RequestBody Building building) {

        // log the parameters
        logger.debug("Calculating Area for: " + building.getName());
        logger.debug("Area of: " + building.getName() + ": " + areaVisitor.getResult());

        return areaVisitor.getResult();
    }

    }


