package org.example;

import org.example.DescriptionLibrary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DescriptionsLibraryTest {
    private DescriptionLibrary descriptionLibrary;
    private Map<Integer, ArrayList<String>> descriptions;

    @BeforeEach
    void setUp() {
        descriptionLibrary = new DescriptionLibrary();
        descriptions = descriptionLibrary.getDescriptionsCollection();
    }

    @Test
    void mapIsNotEmpty() {
        assertFalse(descriptions.isEmpty());
    }

    @Test
    void allEightDifficultiesExist() {
        int count = 0;
        for (Integer key : descriptions.keySet()) {
            assertEquals(count + 1, key);
            count++;
        }
        assertEquals(8, count, String.format("Missing difficulties, only %d difficulties found", count));
    }

    @Test
    void difficultyIsOutOfRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DescriptionLibrary.getRandomDescription(10);
        });
    }

    @Test
    void getARandomDescription() {
        assertNotNull(DescriptionLibrary.getRandomDescription(5));
    }

    @Test
    void mapHoldCorrectValue() {
        assertEquals("ett madrasserat rum utan fönster.", descriptions.get(1).get(1));
    }

    @Test
    void getARandomDescriptionFromAllDifficulties() {
        for (int i = 1; i <= 8; i++) {
            Object result = DescriptionLibrary.getRandomDescription(i);
            assertTrue(result instanceof String);
        }
    }

    @Test
    void outOfBoundsDescriptionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DescriptionLibrary.getRandomDescription(-1);
        });
    }

    @Test
    void variationOfDescriptionsExist() {
        Map<String, Integer> count = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String description = DescriptionLibrary.getRandomDescription(1);
            count.put(description, count.getOrDefault(description, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            int value = entry.getValue();
            assertTrue(value > 0 && value < 10, String.format("Description %s has %d appearances, expected value between 1-9", entry.getKey(), value));
        }
    }

    @Test
    void getBookDescriptionReturnCorrectString() {
        assertEquals("ett rum som känns bekant, det ligger något som liknar en bok på golvet.", DescriptionLibrary.getBookDescription());
    }


}