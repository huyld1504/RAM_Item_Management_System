/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 *
 * @author Asus
 */
public class RAMItem implements Serializable {

    private String code;
    private String type;
    private int bus;
    private String brand;
    private int quantity;
    private Date productionMonthYear;
    private boolean active;

//    public RAMItem(String code, String type, int bus, String brand, int quantity) {
//        this.code = code;
//        this.type = type;
//        this.bus = bus;
//        this.brand = brand;
//        this.quantity = quantity;
//        this.productionMonthYear = new Date();
//        this.active = true;
//    }
    public RAMItem(String code, String type, int bus, String brand, int quantity, String productionMonthYear) throws Exception {
        try {
            this.code = code;
            this.type = type;
            this.bus = bus;
            this.brand = brand;
            this.quantity = quantity;
            this.productionMonthYear = new SimpleDateFormat("MM/yyyy").parse(productionMonthYear);
            this.active = true;
        } catch (ParseException e) {
            System.out.println("Error in RAMItem constructor: " + e.getMessage());
            throw e;
        }
    }

    public static Comparator<RAMItem> sortByCode = new Comparator<RAMItem>() {
        @Override
        public int compare(RAMItem o1, RAMItem o2) {
            int compareWithCode = o1.getCode().compareTo(o2.getCode());
            if (compareWithCode < 0) {
                return 1;
            } else if (compareWithCode > 0) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    public static Comparator<RAMItem> sortByType = new Comparator<RAMItem>() {
        @Override
        public int compare(RAMItem o1, RAMItem o2) {
            int compareWithType = o1.getType().compareTo(o2.getType());
            if (compareWithType > 0) {
                return 1;
            } else if (compareWithType < 0) {
                return -1;
            } else {
                int compareWithBrand = o1.getBrand().compareTo(o2.getBrand());
                if (compareWithBrand > 0) {
                    return 1;
                } else if (compareWithBrand < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    };

    public static Comparator<RAMItem> sortByBus = new Comparator<RAMItem>() {
        @Override
        public int compare(RAMItem o1, RAMItem o2) {
            int compareWithBus = o1.getBus() - o2.getBus();
            if (compareWithBus > 0) {
                return 1;
            } else if (compareWithBus < 0) {
                return -1;
            } else {
                int compareWithBrand = o1.getBrand().compareTo(o2.getBrand());
                if (compareWithBrand > 0) {
                    return 1;
                } else if (compareWithBrand < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    };

    public static Comparator<RAMItem> sortByBrand = new Comparator<RAMItem>() {
        @Override
        public int compare(RAMItem o1, RAMItem o2) {
            int compareWithBrand = o1.getBrand().compareTo(o2.getBrand());
            if (compareWithBrand > 0) {
                return 1;
            } else if (compareWithBrand < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getProductionMonthYear() {
        return productionMonthYear;
    }

    public void setProductionMonthYear(Date productionMonthYear) {
        this.productionMonthYear = productionMonthYear;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
