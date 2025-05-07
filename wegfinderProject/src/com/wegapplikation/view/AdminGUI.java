package com.wegapplikation.view;

import com.wegapplikation.controller.AdminCal;
import com.wegapplikation.model.UserData;
public class AdminGUI extends AdminCal {
    public static void main(String[] args) {
        AdminGUI admin = new AdminGUI();
        UserData userData = new UserData();
        admin.adminPage();
    }
}