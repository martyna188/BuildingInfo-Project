package pl.put.poznan.buildinginfo.logic.Visitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.buildinginfo.logic.Composite.Building;
import pl.put.poznan.buildinginfo.logic.Composite.Level;
import pl.put.poznan.buildinginfo.logic.Composite.Room;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HeatingVisitorTest {

    private Building building;
    private Level level1;
    private Room room1;
    private HeatingVisitor heatingVisitor;

    @BeforeEach
    void setUp() {
        room1 = new Room(); room1.setHeating(10); room1.setCubature(5);
        Room r2 = new Room(); r2.setHeating(20); r2.setCubature(5);
        Room r3 = new Room(); r3.setHeating(30); r3.setCubature(5);
        Room r4 = new Room(); r4.setHeating(40); r4.setCubature(5);



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

        heatingVisitor = new HeatingVisitor();
    }


    @Test
    void testTotalHeatingBuildingCalculation() {
        building.accept(heatingVisitor);

        assertEquals(5, heatingVisitor.getResult(), "Heating calculation failed for the building.");
    }

    @Test
    void testTotalHeatingLevelCalculation() {
        level1.accept(heatingVisitor);

        assertEquals(3, heatingVisitor.getResult(), "Heating calculation failed for the level.");
    }

    @Test
    void testTotalHeatingRoomCalculation() {
        room1.accept(heatingVisitor);

        assertEquals(2, heatingVisitor.getResult(), "Heating calculation failed for the room.");
    }
}