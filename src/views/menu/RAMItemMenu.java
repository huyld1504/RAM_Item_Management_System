/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.menu;

import controllers.IItemController;
import controllers.RAMItemController;
import java.util.Date;
import models.RAMItem;
import tool.utils.Input;
import tool.utils.Validation;
import tool.utils.FormatData;

/**
 *
 * @author Asus
 */
public class RAMItemMenu {

    private static final String FORMAT_DATE = "^([1-9]|1[0-2])/(\\d{4})$";

    private IItemController<RAMItem> ramItemDAO;

    public RAMItemMenu() {
        this.ramItemDAO = new RAMItemController();
    }

    private int getChoice() throws Exception {
        int choice = 0;
        do {
            choice = Input.getInt("Select choice: ");
        } while (choice < 0);
        return choice;
    }

    private boolean isValidStringFormat(String type, String format) {
        return Validation.isMatchWithFormat(type, format);
    }

    private boolean isValidBus(int bus) {
        return Validation.isPositiveNumber(bus);
    }

    private boolean isPositiveNumber(int number) {
        return Validation.isPositiveNumber(number);
    }

    private boolean isValidProductionDate(String date) {
        int year = Integer.parseInt(date.split("/")[1]);
        int month = Integer.parseInt(date.split("/")[0]);

        String formatToday = FormatData.formatDate(new Date());
        int todayYear = Integer.parseInt(formatToday.split("/")[1]);
        int todayMonth = Integer.parseInt(formatToday.split("/")[0]);

        if (Validation.isValidModelYear(year)) {
            if (month <= 12) {
                return true;
            }
        } else if (year == todayYear) {
            if (month <= todayMonth) {
                return true;
            }
        }

        return false;
    }

    private String autoGenerateNewCode(String type) {
        String code = this.ramItemDAO.generateCodeItem(type);
        return code;
    }

    private boolean returnMenu(String message) throws Exception {
        return Input.confirmYesOrNo(message);
    }

    private void addItem() throws Exception {
        try {
            boolean isReturnMainMenu;
            do {
                System.out.println("ADD ITEM");

                /*Type*/
                String type = Input.getString("Enter RAM Item Type: ").toUpperCase();
                while (!RAMItemController.checkRAMType(type)) {
                    System.out.println("RAM Type is invalid (e.g., LPDDR5, DDR5, LPDDR4, DDR4...)");
                    type = Input.getString("Enter again: ").toUpperCase();
                }

                /*Bus*/
                int bus = Input.getInt("Enter Bus Speed (MHz): ");
                while (!isValidBus(bus)) {
                    System.out.println("Bus speed cannot be a negative number");
                    bus = Input.getInt("Enter again: ");
                }

                /*Brand*/
                String brand = Input.getString("Enter RAM Brand: ").toUpperCase().trim();
                while (!RAMItemController.checkRAMBrand(brand)) {
                    System.out.println("List RAM brand: Samsung, Corsair, Kingston");
                    brand = Input.getString("Enter again: ").toUpperCase().trim();
                }

                /*Quantity*/
                int quantity = Input.getInt("Enter quantity: ");
                while (!isPositiveNumber(quantity)) {
                    System.out.println("Quantity cannot be negative number");
                    quantity = Input.getInt("Enter again: ");
                }

                /*Production date*/
                //Return the month and year today
                String production_date = FormatData.formatDate(new Date());
                System.out.println("If you don't enter the production month year, my system will set " + production_date);
                String productionMonthYear = Input.getString("Enter the production date (MM/yyyy): ", production_date);
                while (!isValidStringFormat(productionMonthYear, FORMAT_DATE) || !isValidProductionDate(productionMonthYear)) {
                    System.out.println("Invalid format date (MM/yyyy) or The production date is " + production_date);
                    productionMonthYear = Input.getString("Enter again: ");
                }

                /*Generate new code*/
                String code = this.autoGenerateNewCode(type);
                RAMItem item = new RAMItem(code, type, bus, brand, quantity, productionMonthYear);
                boolean isCreated = this.ramItemDAO.addItem(item);

                if (isCreated) {
                    System.out.println("Created Item Successfully.");
                } else {
                    System.out.println("Failed To Create Item.");
                }

                /*Confirm yes no to return main menu*/
                isReturnMainMenu = this.returnMenu("=> Back to main menu (y/n): ");
            } while (!isReturnMainMenu);
        } catch (Exception e) {
            System.err.println("Error Add Item: " + e.getMessage());
        }
    }

    private void searchItem() throws Exception {
        try {
            boolean isReturnMainMenu;
            do {
                boolean flag = true;
                System.out.println("SEARCH MENU");
                do {
                    System.out.println("1. Search by Type");
                    System.out.println("2. Search by Bus");
                    System.out.println("3. Search by Brand");
                    System.out.println("4. Back main menu");
                    int choice = this.getChoice();

                    switch (choice) {
                        case 1:
                            String type = Input.getString("Enter type to search: ");
                            this.ramItemDAO.showItemBySearchType(type);
                            break;
                        case 2:
                            int bus = Input.getInt("Enter bus to search: ");
                            this.ramItemDAO.showItemBySearchBus(bus);
                            break;
                        case 3:
                            String brand = Input.getString("Enter brand to search: ");
                            this.ramItemDAO.showItemBySearchBrand(brand);
                            break;
                        default:
                            flag = false;
                            break;
                    }
                } while (flag);
                isReturnMainMenu = this.returnMenu("=> Back to main menu (y/n): ");
            } while (!isReturnMainMenu);
        } catch (Exception e) {
            System.err.println("Error Search Item: " + e.getMessage());
        }
    }

    private void updateItem() throws Exception {
        try {
            boolean isReturnMainMenu;
            do {
                System.out.println("UPDATE ITEM INFORMATION");
                String codeItemSearch = Input.getString("Enter item code to update: ");
                if (this.ramItemDAO.checkExistCode(codeItemSearch)) {
                    RAMItem oldItem = this.ramItemDAO.getItem(codeItemSearch);
                    //Save old code before generate code
                    String oldCode = oldItem.getCode();

                    System.out.println("The type before updating is: " + oldItem.getType());
                    String type = Input.getString("Enter new type: ", oldItem.getType()).toUpperCase();
                    while (!RAMItemController.checkRAMType(type)) {
                        System.out.println("RAM Type is invalid (e.g., LPDDR5, DDR5, LPDDR4, DDR4...)");
                        type = Input.getString("Enter again: ", oldItem.getType()).toUpperCase();
                    }
                    System.out.println("The new type is: " + type);

                    System.out.println("The bus before updating is: " + oldItem.getBus() + "MHz");
                    int bus = Input.getInt("Enter new bus speed: ", oldItem.getBus());
                    while (!isPositiveNumber(bus)) {
                        System.out.println("The bus speed cannot be negative number.");
                        bus = Input.getInt("Enter again: ", oldItem.getBus());
                    }
                    System.out.println("The new bus speed is: " + bus + "MHz");

                    System.out.println("The brand before updating is: " + oldItem.getBrand());
                    String brand = Input.getString("Enter new brand: ", oldItem.getBrand()).toUpperCase();
                    while (!RAMItemController.checkRAMBrand(brand)) {
                        System.out.println("List RAM brand: Samsung, Corsair, Kingston");
                        brand = Input.getString("Enter new brand again: ", oldItem.getBrand()).toUpperCase();
                    }
                    System.out.println("The new brand is: " + brand);

                    System.out.println("The quantity before updating is: " + oldItem.getQuantity() + (oldItem.getQuantity() > 1 ? " items" : " item"));
                    int quantity = Input.getInt("Enter new quantity: ", oldItem.getQuantity());
                    while (!isPositiveNumber(quantity)) {
                        System.out.println("Quantity cannot be negative number");
                        quantity = Input.getInt("Enter again: ", oldItem.getQuantity());
                    }
                    System.out.println("The new quantity is: " + quantity + (quantity > 1 ? " items" : " item"));

                    /*Set data that user enter to update to oldItem*/
                    String newCode;
                    RAMItem newItem;
                    if (!type.equalsIgnoreCase(oldItem.getType())) {
                        newCode = this.autoGenerateNewCode(type);
                        newItem =new RAMItem(newCode, type, bus, brand, quantity, FormatData.formatDate(oldItem.getProductionMonthYear()));
                    } else {
                        newItem = new RAMItem(oldCode, type, bus, brand, quantity, FormatData.formatDate(oldItem.getProductionMonthYear()));
                    }
                    

                    boolean isUpdated = this.ramItemDAO.updateItem(oldCode, newItem);
                    if (isUpdated) {
                        System.out.println("Updated successfully.");
                    } else {
                        System.out.println("Failed to update.");
                    }

                } else {
                    System.out.println("This item that you find does not exists.");
                }
                isReturnMainMenu = this.returnMenu("=> Back to main menu (y/n): ");
            } while (!isReturnMainMenu);
        } catch (Exception e) {
            System.err.println("Error Update Item: " + e.getMessage());
        }
    }

    private void deleleItem() throws Exception {
        try {
            boolean isReturnMainMenu;
            do {
                System.out.println("DELETE ITEM");
                String code = Input.getString("Enter code to delete: ");
                if (!this.ramItemDAO.checkExistCode(code)) {
                    System.out.println("Item doesnot exist, cannot delete item by this code");
                    return;
                }

                boolean isConfirmDelete = Input.confirmYesOrNo("Do you want to delete this item (y/n)? ");
                if (isConfirmDelete) {
                    boolean isDeleted = this.ramItemDAO.deleteItem(code);
                    if (isDeleted) {
                        System.out.println("Deleted Successfully.");
                    } else {
                        System.out.println("Failed To Delete.");
                    }
                }
                isReturnMainMenu = this.returnMenu("=> Back to main menu (y/n): ");
            } while (!isReturnMainMenu);
        } catch (Exception e) {
            System.err.println("Error Delete Item: " + e.getMessage());
        }
    }

    private void showAll() {
        try {
            this.ramItemDAO.showAll();
        } catch (Exception e) {
            System.err.println("Error Show All: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            boolean isSaved = this.ramItemDAO.saveDataToFile();
            if (isSaved) {
                System.out.println("Save Data Successfully.");
            } else {
                System.out.println("Failed To Save Data.");
            }
        } catch (Exception e) {
            System.err.println("Error in save data to file: " + e.getMessage());
        }
    }

    public void runProgram() throws Exception {
        try {
            boolean flag = true;
            do {
                System.out.println("=== RAM ITEM MANAGEMENT SYSTEM ===");
//                System.out.println("Check existed CODE: " + this.ramItemDAO.checkExistCode("RAMLPDDR5_001"));
                System.out.print("1. Add Item\n2. Search\n3. Update Item Information\n4. Delete Item\n5. Show All Items\n6. Store Data to File\n7. Quit Menu\n");
                int choice = this.getChoice();

                switch (choice) {
                    case 1:
                        this.addItem();
                        break;
                    case 2:
                        this.searchItem();
                        break;
                    case 3:
                        this.updateItem();
                        break;
                    case 4:
                        this.deleleItem();
                        break;
                    case 5:
                        this.showAll();
                        break;
                    case 6:
                        this.saveData();
                        break;
                    default:
                        boolean isQuitProgram = Input.confirmYesOrNo("Do you want to exit program (y/n)? ");
                        if (isQuitProgram) {
                            System.out.println("Exit....");
                            System.out.println("===> Goodbye");
                            flag = !flag;
                        }
                }
            } while (flag);
        } catch (Exception e) {
            throw e;
        }
    }
}
