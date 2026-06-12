package pl.put.poznan.buildinginfo.rest;

import org.junit.jupiter.api.Test;
import pl.put.poznan.buildinginfo.logic.Composite.Building;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class BuildingInfoControllerTest {

    @Test
    public void testGetMetric() {
        ControllerHelper mockHelper = mock(ControllerHelper.class);

        Building fakeBuilding = new Building();
        String fakeMetric = "area";
        String fakeId = "1-10-100";
        long fakeResult = 10;

        when(mockHelper.calculate(fakeMetric, fakeId, fakeBuilding)).thenReturn(fakeResult);

        BuildingInfoController controller = new BuildingInfoController(mockHelper);
        controller.sendBuilding(fakeBuilding);

        long actualResult = controller.getMetric(fakeMetric, fakeId);

        assertEquals(fakeResult, actualResult);

        verify(mockHelper, times(1)).calculate(fakeMetric, fakeId, fakeBuilding);
    }
}