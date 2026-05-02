package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DescriptionLibrary {
    private static Map<Integer, ArrayList<String>> descriptions = new HashMap<>();

    public DescriptionLibrary() {
        loadDescriptions();
    }

    /**
     * loadDescriptions() must be run before calling this method.
     *
     * @param difficulty 1-8.
     * @return a random cell description.
     */
    public static String getRandomDescription(int difficulty) {
        if (difficulty < 1 || difficulty > 8) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 8.");
        }
        ArrayList<String> fetchedDescriptions = descriptions.get(difficulty);
        return fetchedDescriptions.get(RandomNumberGenerator.nextInt(0, fetchedDescriptions.size() - 1));
    }

    public static String getBookDescription() {
        return "ett rum som känns bekant, det ligger något som liknar en bok på golvet.";
    }

    //for test
    public Map<Integer, ArrayList<String>> getDescriptionsCollection() {
        return descriptions;
    }

    private void loadDescriptions() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Descriptions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length < 2) continue;

                int difficulty = Integer.parseInt(parts[0].trim());
                String text = parts[1].trim().toLowerCase();
                descriptions.computeIfAbsent(difficulty, k -> new ArrayList<>()).add(text);

            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }
}