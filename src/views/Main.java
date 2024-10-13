/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import views.menu.RAMItemMenu;

/**
 *
 * @author Asus
 */
public class Main {

    public static void main(String[] args) {
        try {
            RAMItemMenu menu = new RAMItemMenu();
            menu.runProgram();
        } catch (Exception e) {
            System.out.println("Error in pogram: " + e.getMessage());
        }
    }
}
