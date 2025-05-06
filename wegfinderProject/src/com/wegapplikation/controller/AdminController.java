// controller/AdminController.java
package com.wegapplikation.controller;

import com.wegapplikation.model.*;
import com.wegapplikation.view.admin.*;

public class AdminController {
    private UserManager userManager;
    private AdminMainView mainView;
    
    public void handleUserAdd(String username, String password, String role) {
        User newUser = new User(username, password, role);
        userManager.addUser(newUser);
        mainView.refreshUserList();
    }
    
    // Weitere Controller-Methoden
}