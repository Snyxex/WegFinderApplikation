package control;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.tree.RowMapper;

public class adminCal extends JFrame{

  JButton UserManageBtn;
  JButton RoomManageBtn;

   public void adminPage(){
    this.setTitle("Admin-Dashboard");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setSize(420,420);
    this.setVisible(true);
    this.getContentPane().setBackground(new Color(255,255,255));

    UserManageBtn = new JButton();
    UserManageBtn.setBounds(0,0,100,50);
    UserManageBtn.setText("User Managment");
    RoomManageBtn = new JButton();
    RoomManageBtn.setBounds(100,0,100,50);
    RoomManageBtn.setText("Room Managment");


    this.add(UserManageBtn);
    this.add(RoomManageBtn);
    UserManageBtn.addActionListener(e -> userManagePage());
    RoomManageBtn.addActionListener(e -> roomManagePage());

    
   }

private void userManagePage(){


  this.setTitle("User-Managment");
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setResizable(false);
  this.setSize(420,420);
  this.setVisible(true);
  this.getContentPane().setBackground(new Color(255,255,255));

  UserManageBtn = new JButton();
  UserManageBtn.setBounds(0,0,100,50);
  UserManageBtn.setText("User Managment");
  RoomManageBtn = new JButton();
  RoomManageBtn.setBounds(100,0,100,50);
  RoomManageBtn.setText("Room Managment");


  this.add(UserManageBtn);
  this.add(RoomManageBtn);
  UserManageBtn.addActionListener(e -> userManagePage());
  RoomManageBtn.addActionListener(e -> roomManagePage());
}

private void roomManagePage(){

  this.setTitle("Room-Managment");
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setResizable(false);
  this.setSize(420,420);
  this.setVisible(true);
  this.getContentPane().setBackground(new Color(255,255,255));

  UserManageBtn = new JButton();
  UserManageBtn.setBounds(0,0,100,50);
  UserManageBtn.setText("User Managment");
  RoomManageBtn = new JButton();
  RoomManageBtn.setBounds(100,0,100,50);
  RoomManageBtn.setText("Room Managment");


  this.add(UserManageBtn);
  this.add(RoomManageBtn);
  UserManageBtn.addActionListener(e -> userManagePage());
  RoomManageBtn.addActionListener(e -> roomManagePage());
}
	
 private void addUser() {
	 
 }
 
 private void deleteUser() {
	 
 }
 
 private void updateUser() {
	 
 }
 
 private void addRoom() {
	 
 }
 
 private void deleteRoom() {
	 
 }
 
 private void lockRoom() {
	 
 }
 private void lockCoridoor() {
	 
 }
 
}
