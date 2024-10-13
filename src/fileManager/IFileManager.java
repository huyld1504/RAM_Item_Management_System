/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManager;

import java.util.List;

/**
 *
 * @author Asus
 * @param <E>
 */
public interface IFileManager<E> {

    List<E> loadFile() throws Exception;

    boolean saveFile(List<E> list) throws Exception;
}
