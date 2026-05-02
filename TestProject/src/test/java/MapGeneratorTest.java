package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MapGeneratorTest {

    @Test
    public void testSmallMap() {
        MapGenerator generator = new MapGenerator();
        GridMap map = generator.generateMap(1);


        System.out.println(map);
    }

    @Test
    public void testMediumMap() {
        MapGenerator generator = new MapGenerator();
        GridMap map = generator.generateMap(4);


        System.out.println(map);
    }

    @Test
    public void testLargeMap() {
        MapGenerator generator = new MapGenerator();
        GridMap map = generator.generateMap(8);


        System.out.println(map.toStringFullyExplored());
    }

}

