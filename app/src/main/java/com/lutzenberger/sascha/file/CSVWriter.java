package com.lutzenberger.sascha.file;

import android.util.Log;

import com.lutzenberger.sascha.swan.CSVWritable;
import com.lutzenberger.sascha.swan.SwanCodes;
import com.lutzenberger.sascha.swan.SwanData;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * This class represents the CSVWriter and writes the objects to the CSV file when they are changed.
 *
 * This object is used for the output of all SwanData and SwanCodes to the CSV file, make sure to
 * use the right writer, there will be only two available.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 01.08.2015
 *
 */
public class CSVWriter {
    private CSVWriter() {
    }

    //Minimalistic arguments just the fileName is needed from the Android Application
    public static void updateSwanDataFile(String fileName) throws IOException {
        updateCSVFile(fileName, SwanData.getCSVFileHeader(), CSVReader.getSwanDataList());
    }

    //Minimalistic arguments just the file name is needed from the Android Application
    public static void updateSwanCodesFile(String fileName) throws IOException {
        updateCSVFile(fileName, SwanCodes.getCSVFileHeader(), CSVReader.getSwanCodesList());
    }

    //Private method for writing makes it easier to change <? extends CSVWritable> makes sure
    //that all the elements of the list are objects with the data type of CSVWritable
    private static void updateCSVFile(String fileName, String[] header,
                                      List<? extends CSVWritable> list) throws IOException{
        //Try catch with resources is not possible
        FileWriter writer = null;
        CSVPrinter printer = null;
        try {
            writer = new FileWriter(fileName);
            printer = new CSVPrinter(writer, CSVFormat.DEFAULT);

            //Writing the HEADER first
            printer.printRecord(header);
            for (CSVWritable writable : list) {
                //Writing all the records
                printer.printRecord(writable.getDataRecord());
            }
        } catch (IOException e) {
            Log.d("ERROR", e.getMessage()); //Log the error on the debug console
            //Rethrows the exception. Resources should be freed
            throw e;
        } finally {
            //Needs to be called to free resources and to flush the FileWriter
            if(printer != null)
                printer.flush();
            if(writer != null)
                writer.close();
            if(printer != null)
                printer.close();
        }
    }
}