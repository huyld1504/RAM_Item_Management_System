package controllers;

import fileManager.FileManager;
import fileManager.IFileManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.RAMItem;
import tool.utils.FormatData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Asus
 */
public class RAMItemController implements IItemController<RAMItem> {

    enum RAMType {
        LPDDR4,
        LPDDR5,
        DDR5,
        DDR4;
    };

    enum RAMBrand {
        SAMSUNG,
        CORSAIR,
        KINGSTON,
    };

    private List<RAMItem> list;

    /*
    Hàm này dùng để check xem type của RAM có hợp lệ trong enum của phần mềm hay không
    Trả về true nếu type có trong enum và ngược lại.
     */
    public static boolean checkRAMType(String type) {
        for (RAMType ramType : RAMType.values()) {
            if (ramType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkRAMBrand(String brand) {
        for (RAMBrand ramBrand : RAMBrand.values()) {
            if (ramBrand.name().equalsIgnoreCase(brand)) {
                return true;
            }
        }
        return false;
    }

    public RAMItemController() {
        List<RAMItem> data = this.loadDataFromFile();

        if (data.isEmpty() || data == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = data;
        }
    }

    @Override
    public boolean addItem(RAMItem item) {
        if (item == null) {
            return false;
        }
        return this.list.add(item);
    }

    @Override
    public boolean deleteItem(String code) {
        if (this.getItem(code) == null) {
            return false;
        }

        for (RAMItem item : this.list) {
            if (item.getCode().equalsIgnoreCase(code)) {
                item.setActive(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<RAMItem> searchByType(String type) {
        List<RAMItem> data = new ArrayList<>();
        Collections.sort(data, RAMItem.sortByType);
        for (RAMItem item : this.list) {
            if (item.getType().equalsIgnoreCase(type) && item.isActive()) {
                data.add(item);
            }
        }

        if (data.isEmpty() || data == null) {
            return Collections.EMPTY_LIST;
        } else {
            return data;
        }
    }

    @Override
    public List<RAMItem> searchByBus(int bus) {
        List<RAMItem> data = new ArrayList<>();
        Collections.sort(data, RAMItem.sortByBus);
        for (RAMItem item : this.list) {
            if (item.getBus() == bus && item.isActive()) {
                data.add(item);
            }
        }

        if (data.isEmpty() || data == null) {
            return Collections.EMPTY_LIST;
        } else {
            return data;
        }
    }

    @Override
    public List<RAMItem> searchByBrand(String brand) {
        List<RAMItem> data = new ArrayList<>();
        Collections.sort(data, RAMItem.sortByBrand);
        for (RAMItem item : this.list) {
            if (item.getBrand().equalsIgnoreCase(brand) && item.isActive()) {
                data.add(item);
            }
        }

        if (data.isEmpty() || data == null) {
            return Collections.EMPTY_LIST;
        } else {
            return data;
        }
    }

    @Override
    public void showAll() {
        if (this.list.isEmpty() || this.list == null) {
            System.out.println("Have no items");
            return;
        }
        Collections.sort(list, RAMItem.sortByCode);
        System.out.printf("%-15s %-10s %-10s %-10s %-10s %-15s\n", "Code", "Type", "Bus Speed", "Brand", "Quantity", "Production Date");
        for (RAMItem item : this.list) {
            if (item.isActive()) {
                String row = String.format("%-15s %-10s %-10s %-10s %-10d %-15s", item.getCode(), item.getType(), item.getBus() + "MHz", item.getBrand(), item.getQuantity(), FormatData.formatDate(item.getProductionMonthYear()));
                System.out.println(row);
            }
        }
    }

    @Override
    public boolean updateItem(String code, RAMItem newData) {
        if (!this.checkExistCode(code)) {
            return false;
        }

        for (RAMItem item : this.list) {
            if (item.getCode().equalsIgnoreCase(code)) {
                item.setCode(newData.getCode());
                item.setBrand(newData.getBrand());
                item.setBus(newData.getBus());
                item.setQuantity(newData.getQuantity());
                item.setProductionMonthYear(newData.getProductionMonthYear());
                item.setType(newData.getType());
                return true;
            }
        }
        return false;
    }

    @Override
    public String generateCodeItem(String type) {
//        String newCode;
//        int count = 1;
//
////        for (RAMItem item : this.list) {
////            if (item.getType().equalsIgnoreCase(type)) {
////                count++;
////            }
////        }
//        newCode = String.format("RAM%s_%03d", type.toUpperCase(), count);
//        while (this.checkExistCode(newCode)) {
//            count++;
//            newCode = String.format("RAM%s_%03d", type.toUpperCase(), count);
//        }
//        return newCode;

        String codePattern = "RAM" + type.toUpperCase();
        int count = 1;

        Collections.sort(list, RAMItem.sortByCode);

        for (RAMItem item : list) {
            String codeSplited = item.getCode().split("_")[0];
            String quantityOfCode = item.getCode().split("_")[1];
            if (codeSplited.equals(codePattern)) {
                count = Integer.parseInt(quantityOfCode) + 1;
                break;
            }
        }

        String newCode = String.format("RAM%s_%03d", type.toUpperCase(), count);

        while (this.checkExistCode(newCode)) {
            count++;
            newCode = String.format("RAM%s_%03d", type.toUpperCase(), count);
        }

        return newCode;
    }

    @Override
    public List<RAMItem> loadDataFromFile() {
        List<RAMItem> data;
        IFileManager<RAMItem> fileManager = new FileManager();
        try {
            data = fileManager.loadFile();

            if (data == null || data.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            System.err.println("Error load data: " + e.getMessage());
            return Collections.EMPTY_LIST;
        }
        return data;
    }

    @Override
    public boolean saveDataToFile() {
        IFileManager<RAMItem> fileManager = new FileManager();
        try {
            boolean isSuccess = fileManager.saveFile(this.list);
            return isSuccess;
        } catch (Exception e) {
            System.err.println("Error save data: " + e.getMessage());
            return false;
        }
    }

    @Override
    //return true if item exists or return false if item does not exist.
    public boolean checkExistCode(String code) {
        return this.getItem(code) != null;
    }

    @Override
    public RAMItem getItem(String code) {
        for (RAMItem item : this.list) {
            if (item.getCode().equalsIgnoreCase(code)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void showItemBySearchType(String type) {
        List<RAMItem> response = this.searchByType(type);

        if (response == null || response.isEmpty()) {
            System.out.println("Have no items");
            return;
        }
        String headers = String.format("%-15s %-10s %-15s %-10s", "Code", "Type", "Production Date", "Quantity");
        System.out.println(headers);
        for (RAMItem item : response) {
            String row = String.format("%-15s %-10s %-15s %-10s", item.getCode(), item.getType(), FormatData.formatDate(item.getProductionMonthYear()), item.getQuantity());
            System.out.println(row);
        }
    }

    @Override
    public void showItemBySearchBus(int bus) {
        List<RAMItem> response = this.searchByBus(bus);

        if (response == null || response.isEmpty()) {
            System.out.println("Have no items");
            return;
        }
        String headers = String.format("%-15s %-15s %-15s %-10s", "Code", "Bus speed", "Production Date", "Quantity");
        System.out.println(headers);
        for (RAMItem item : response) {
            String row = String.format("%-15s %-15s %-15s %-10s", item.getCode(), item.getBus() + "MHz", FormatData.formatDate(item.getProductionMonthYear()), item.getQuantity());
            System.out.println(row);
        }
    }

    @Override
    public void showItemBySearchBrand(String brand) {
        List<RAMItem> response = this.searchByBrand(brand);

        if (response == null || response.isEmpty()) {
            System.out.println("Have no items");
            return;
        }

        String headers = String.format("%-15s %-10s %-15s %-10s", "Code", "Brand", "Production Date", "Quantity");
        System.out.println(headers);
        for (RAMItem item : response) {
            String row = String.format("%-15s %-10s %-15s %-10s", item.getCode(), item.getBrand(), FormatData.formatDate(item.getProductionMonthYear()), item.getQuantity());
            System.out.println(row);
        }
    }
}
