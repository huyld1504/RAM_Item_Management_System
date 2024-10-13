/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;

/**
 *
 * @author Asus
 * @param <E>
 */
public interface IItemController<E> {

    boolean addItem(E item);

    boolean deleteItem(String code);

    boolean checkExistCode(String code);

    E getItem(String code);

    List<E> searchByType(String type);

    List<E> searchByBus(int bus);

    List<E> searchByBrand(String brand);

    void showAll();

    void showItemBySearchType(String type);

    void showItemBySearchBus(int bus);

    void showItemBySearchBrand(String brand);

    boolean updateItem(String code, E newData);

    String generateCodeItem(String type);

    List<E> loadDataFromFile();

    boolean saveDataToFile();
}
