package pl.put.poznan.buildinginfo.logic.Visitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.buildinginfo.logic.Composite.Building;
import pl.put.poznan.buildinginfo.logic.Composite.Level;
import pl.put.poznan.buildinginfo.logic.Composite.Room;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LightingVisitorTest {

    private Building building;
    private Level level1;
    private Room room1;
    private LightingVisitor lightingVisitor;

    @BeforeEach
    void setUp() {
        room1 = new Room(); room1.setLight(10); room1.setArea(5);
        Room r2 = new Room(); r2.setLight(20); r2.setArea(5);
        Room r3 = new Room(); r3.setLight(30); r3.setArea(5);
        Room r4 = new Room(); r4.setLight(40); r4.setArea(5);



        ArrayList<Room> roomsLevel1 = new ArrayList<>();
        roomsLevel1.add(room1);
        roomsLevel1.add(r2);
        ArrayList<Room> roomsLevel2 = new ArrayList<>();
        roomsLevel2.add(r3);
        roomsLevel2.add(r4);

        level1 = new Level();
        level1.setChildrenRooms(roomsLevel1);
        Level level2 = new Level();
        level2.setChildrenRooms(roomsLevel2);

        ArrayList<Level> levels = new ArrayList<>();
        levels.add(level1);
        levels.add(level2);

        building = new Building();
        building.setChildrenLevels(levels);

        lightingVisitor = new LightingVisitor();
    }


    @Test
    void testTotalHeatingBuildingCalculation() {
        building.accept(lightingVisitor);

        assertEquals(5, lightingVisitor.getResult(), "Lighting calculation failed for the building.");
    }

    @Test
    void testTotalHeatingLevelCalculation() {
        level1.accept(lightingVisitor);

        assertEquals(3, lightingVisitor.getResult(), "Lighting calculation failed for the level.");
    }

    @Test
    void testTotalHeatingRoomCalculation() {
        room1.accept(lightingVisitor);

        assertEquals(2, lightingVisitor.getResult(), "Lighting calculation failed for the room.");
    }
}