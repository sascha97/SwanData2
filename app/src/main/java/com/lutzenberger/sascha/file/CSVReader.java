package com.lutzenberger.sascha.file;

import android.util.Log;

import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swan.SwanCodes;
import com.lutzenberger.sascha.swan.SwanData;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class reads in the CSV files.
 *
 * And returns a list for each list. Lists are required because they will be updated.
 * And lists make it easier to write it into a file again.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 01.08.2015
 *
 */
public final class CSVReader {
    private static List<Data> swanDataList;
    private static List<Data> swanCodesList;

    //Private constructor
    private CSVReader(){
    }

    /**
     * Returns the SwanDataList when it is already read in in the background
     *
     * @return the read in SwanDataList
     */
    public static List<Data> getSwanDataList() {
        if(swanDataList == null)
            swanDataList = new ArrayList<>();

        return swanDataList;
    }

    /**
     * Returns the SwanDataList when it is already read in in the background
     *
     * @return the read in SwanDataList
     */
    public static List<Data> getSwanCodesList() {
        if(swanDataList == null)
            swanDataList = new ArrayList<>();

        return swanDataList;
    }

    //Reads in the CSV Swan Data file and returns the list of the records without the headings
    public static void getSwanDataList(String filePath) throws IOException {
        File file = new File(filePath);

        //Overrides old swanDataList if there is one
        swanDataList = new ArrayList<>();

        CSVParser parser = null;
        try {
            parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT);
            List<CSVRecord> recordList = parser.getRecords(); //Get all the records in the CSV file

            // 1 is used here to get rid of the headings, headings are not required for data handling
            for (int i = 1; i < recordList.size(); i++) {
                CSVRecord r = recordList.get(i); //Get the record from the CSV file.

                //Constructs the data item
                SwanData data = new SwanData(r, i-1); //i-1 because of zero index list

                swanDataList.add(data);
            }
        } catch (IOException e){
            Log.d("ERROR", e.getMessage()); //Log the error on the debug console
            //Rethrows the exception. Resources should be freed
            throw e;
        } finally {
            //Close the file parser to free resources
            if(parser != null)
                parser.close();
        }
    }

    //Reads in the CSV Swan Codes data file and returns the list of the records without the headings
    public static void getSwanCodesList(String filePath) throws IOException {
        File file = new File(filePath);

        //Overrides old swanDataList if there is one
        swanCodesList = new ArrayList<>();
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT);
            List<CSVRecord> recordList = parser.getRecords(); //Get all the records in the CSV file

            //1 is used here to get rid of the headings, headings are not required for data handling later
            for (int i = 1; i < recordList.size(); i++) {
                CSVRecord r = recordList.get(i); //Get the record from the CSV file.

                //Constructs the data item
                SwanCodes code = new SwanCodes(r, i-1); //i-1 because of zero index list

                swanCodesList.add(code);
            }
        } catch (IOException e){
            Log.d("ERROR", e.getMessage()); //Log the error on the debug console
            //Rethrows the exception. Resources should be freed
            throw e;
        } finally {
            //Close the file parser to free resources
            if(parser != null)
                parser.close();
        }
    }
}
