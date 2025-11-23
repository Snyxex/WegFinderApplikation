package com.wegapplikation.config.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FloorData} class manages the persistence and manipulation of
 * floor information within the application. It loads floor data from a file,
 * provides methods for adding, updating, and deleting floors, and saves changes
 * back to the file.
 */
public class FloorData {
    private List<Floor> floors;
    private final String floorFilePath = "files/floor.txt"; 

    /**
     * Constructs a {@code FloorData} object and initializes the list of floors
     * by loading existing data from the file.
     */
    public FloorData() {
        floors = loadFloors();
    }

    /**
     * Loads the list of floors from the dedicated file path.
     * Each line in the file is expected to contain "id,locked" format.
     *
     * @return A list of {@link Floor} objects loaded from the file.
     */
    private List<Floor> loadFloors() {
        List<Floor> floorList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(floorFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Ensure there are enough parts before parsing
                if (parts.length >= 2) { 
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        boolean locked = Boolean.parseBoolean(parts[1].trim());
                        floorList.add(new Floor(id, locked));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing floor data: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return floorList;
    }

    /**
     * Adds a new floor to the system and persists the change to the file.
     *
     * @param id The unique identifier for the new floor.
     * @param locked The initial lock status of the new floor.
     */
    public void addFloor(int id, boolean locked) {
        Floor newFloor = new Floor(id, locked);
        floors.add(newFloor);
        saveFloors();
    }

    /**
     * Updates the lock status of an existing floor identified by its ID.
     * Note: This implementation only updates the lock status; the floor ID remains unchanged.
     *
     * @param oldId The ID of the floor to be updated.
     * @param newId The new ID (currently not used for update logic but maintained in the signature).
     * @param locked The new lock status to set for the floor.
     */
    public void updateFloor(int oldId, int newId, boolean locked) {
        for (Floor floor : floors) {
            if (floor.getId() == oldId) {
                floor.setLocked(locked);
                // The original code commented on potential ID update here, 
                // but currently only 'locked' status is changed.
                saveFloors();
                return;
            }
        }
    }

    /**
     * Deletes a floor from the system based on its unique ID and persists the change.
     *
     * @param id The ID of the floor to delete.
     */
    public void deleteFloor(int id) {
        floors.removeIf(floor -> floor.getId() == id);
        saveFloors();
    }

    /**
     * Retrieves a copy of the list of all floors currently loaded in the system.
     *
     * @return A new {@code List<Floor>} containing all floor data.
     */
    public List<Floor> getAllFloors() {
        return new ArrayList<>(floors); // Return a copy of the list
    }

    /**
     * Writes the current list of floors back to the file path, overwriting the
     * existing content.
     */
    private void saveFloors() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(floorFilePath))) {
            for (Floor floor : floors) {
                bw.write(floor.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Represents a single floor entity with an ID and a lock status.
     */
    public static class Floor {
        private int id;
        private boolean locked;

        /**
         * Creates a new Floor instance.
         *
         * @param id The unique identifier of the floor.
         * @param locked The lock status of the floor.
         */
        public Floor(int id, boolean locked) {
            this.id = id;
            this.locked = locked;
        }

        /**
         * Gets the unique identifier of the floor.
         *
         * @return The floor ID.
         */
        public int getId() {
            return id;
        }

        /**
         * Checks the lock status of the floor.
         *
         * @return {@code true} if the floor is locked, {@code false} otherwise.
         */
        public boolean isLocked() {
            return locked;
        }

        /**
         * Sets the lock status of the floor.
         *
         * @param locked The new lock status.
         */
        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        /**
         * Returns a string representation of the floor suitable for file saving.
         * Format: "id,locked"
         *
         * @return A comma-separated string containing the floor ID and lock status.
         */
        @Override
        public String toString() {
            return id + "," + locked; // Only store ID and lock status
        }
    }
}