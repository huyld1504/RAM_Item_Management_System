/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import models.RAMItem;

/**
 *
 * @author Asus
 */
public class FileManager implements IFileManager<RAMItem> {

    private static final String FILE_NAME = "RAMModules.dat";

    @Override
    public List<RAMItem> loadFile() throws Exception {
        FileInputStream fInputStream = null;
        ObjectInputStream objInputStream = null;
        File file = new File(FILE_NAME);
        try {
            if (file.exists()) {
                fInputStream = new FileInputStream(file);
                objInputStream = new ObjectInputStream(fInputStream);
                return (List<RAMItem>) objInputStream.readObject();
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (fInputStream != null) {
                    fInputStream.close();
                }
                if (objInputStream != null) {
                    objInputStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    @Override
    public boolean saveFile(List<RAMItem> list) throws Exception {
        FileOutputStream fOutputStream = null;
        ObjectOutputStream objOutputStream = null;

        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                fOutputStream = new FileOutputStream(file);
                objOutputStream = new ObjectOutputStream(fOutputStream);

                objOutputStream.writeObject(list);
            }
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (fOutputStream != null) {
                    fOutputStream.close();
                }
                if (objOutputStream != null) {
                    objOutputStream.close();
                }
            } catch (IOException e) {
                return false;
            }
        }
    }
}
