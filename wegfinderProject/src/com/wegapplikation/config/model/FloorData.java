package com.wegapplikation.config.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FloorData {
    private List<Floor> floors;
    private final String floorFilePath = "wegfinderProject/src/files/floor.txt"; 

    public FloorData() {
        floors = loadFloors();
    }

    private List<Floor> loadFloors() {
        List<Floor> floorList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(floorFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                boolean locked = Boolean.parseBoolean(parts[1]);
                floorList.add(new Floor(id, locked));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return floorList;
    }

    public void addFloor(int id, boolean locked) {
        Floor newFloor = new Floor(id, locked);
        floors.add(newFloor);
        saveFloors();
    }

    public void updateFloor(int oldId, int newId, boolean locked) {
        for (Floor floor : floors) {
            if (floor.getId() == oldId) {
                floor.setLocked(locked);
                // Hier könnte man auch die ID aktualisieren, falls nötig
                saveFloors();
                return;
            }
        }
    }

    public void deleteFloor(int id) {
        floors.removeIf(floor -> floor.getId() == id);
        saveFloors();
    }

    public List<Floor> getAllFloors() {
        return new ArrayList<>(floors); // Rückgabe einer Kopie der Liste
    }

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

    // Innere Klasse für die Flur-Daten
    public static class Floor {
        private int id;
        private boolean locked;

        public Floor(int id, boolean locked) {
            this.id = id;
            this.locked = locked;
        }

        public int getId() {
            return id;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        @Override
        public String toString() {
            return id + "," + locked; // Nur ID und Sperrstatus speichern
        }
    }
}