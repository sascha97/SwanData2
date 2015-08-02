package com.lutzenberger.sascha.file;

import android.util.Log;

import com.lutzenberger.sascha.swan.CSVWritable;
import com.lutzenberger.sascha.swan.SwanCodes;
import com.lutzenberger.sascha.swan.SwanData;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents the DataFileWriter and writes the objects to the CSV file when they are changed.
 *
 * This object is used for the output of all SwanData and SwanCodes to the CSV file, make sure to
 * use the right writer, there will be only two available.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 01.08.2015
 *
 */
public class DataFileWriter {
    private DataFileWriter() {
    }

    //Minimalistic arguments just the fileName is needed from the Android Application
    public static void updateSwanDataFile(String fileName) throws IOException {
        updateCSVFile(fileName, SwanData.getCSVFileHeader(), DataFileReader.getSwanDataList());
    }

    //Minimalistic arguments just the file name is needed from the Android Application
    public static void updateSwanCodesFile(String fileName) throws IOException {
        updateCSVFile(fileName, SwanCodes.getCSVFileHeader(), DataFileReader.getSwanCodesList());
    }

    //Private method for writing makes it easier to change <? extends CSVWritable> makes sure
    //that all the elements of the list are objects with the data type of CSVWritable
    private static void updateCSVFile(String fileName, String[] header,
                                      List<? extends CSVWritable> list) throws IOException{
        //Try catch with resources is not possible
        FileWriter fileWriter = null;
        CSVWriter writer = null;
        try {
            fileWriter = new FileWriter(fileName);
            writer = new CSVWriter(fileWriter, CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER);

            List<String[]> resultList = new ArrayList<>(list.size() + 1); //all entries + header
            //Adding the HEADER first
            resultList.add(header);
            for (CSVWritable writable : list) {
                //Adding all the records
                resultList.add(writable.getDataRecord());
            }
            writer.writeAll(resultList);
        } catch (IOException e) {
            Log.d("ERROR", e.getMessage()); //Log the error on the debug console
            //Rethrows the exception. Resources should be freed
            throw e;
        } finally {
            //Needs to be called to free resources and to flush the FileWriter
            if(writer != null){
                writer.flush();
                writer.close();
            } else if(fileWriter != null){
                fileWriter.flush();
                fileWriter.close();
            }
        }
    }
}