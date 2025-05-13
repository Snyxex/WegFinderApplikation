package com.wegapplikation.model;

import java.io.*;
import java.util.HashMap;
import javax.swing.DefaultListModel;

public class RoomData {
    private HashMap<String[], String> room;
    private DefaultListModel<String> roomListModel;

    public RoomData(){
      room = new HashMap<>();
      roomListModel = new DefaultListModel<>();
      refreshUserList();
    }

    private void saveRoomToFile(String roomNumber, String text, String setter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("wegfinderProject/room.txt", true))) {
            writer.write(roomNumber + "," + text + "," + setter);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRoom(String roomNumber, String text, String setter){

        if(roomNumber.isEmpty() || text.isEmpty() || setter.isEmpty()){
            throw new IllegalArgumentException("Raum Nummer, Bezeichnung und Öffentlichkeit dürfen nicht leer sein!");
        }
       
        if(!room.containsKey(roomNumber)){
           room.put(new String[]{roomNumber,setter},text);
           saveRoomToFile(roomNumber, setter, text);
        }else{
            throw new IllegalArgumentException("Raum existiert bereits!");
        }

    }
    public void deleteRoom(String roomNumber) {
        File inputFile = new File("wegfinderProject/room.txt");
        File tempFile = new File("wegfinderProject/room_temp.txt");

        boolean roomDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String roomNumberValue = parts[0];
                    if (!roomNumberValue.equals(roomNumber)) {
                        writer.write(line);
                        writer.newLine();
                    } else {
                        room.remove(roomNumberValue);
                        roomDeleted = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen/Schreiben der Datei: " + e.getMessage());
        }

        if (!inputFile.delete()) {
            System.out.println("Fehler beim Löschen der Originaldatei!");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Fehler beim Umbenennen der temporären Datei!");
        }

        if (roomDeleted) {
            refreshUserList();
        }
    }




    private void refreshUserList() {
        roomListModel.removeAllElements();
        room.forEach((value, key) -> roomListModel.addElement(value[0] + " (" + value[1] + ")" + key));
        
       
    }

    public DefaultListModel<String> getRoomListModel() {
        return roomListModel;
    }

    public HashMap<String[], String> getRoom() {
        return room;
    }
}
