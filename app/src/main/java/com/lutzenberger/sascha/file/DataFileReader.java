package com.lutzenberger.sascha.file;

import android.util.Log;

import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swan.SwanCodes;
import com.lutzenberger.sascha.swan.SwanData;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
public final class DataFileReader {
    private static List<Data> swanDataList;
    private static List<Data> swanCodesList;

    //Private constructor
    private DataFileReader(){
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
        if(swanCodesList == null)
            swanCodesList = new ArrayList<>();

        return swanCodesList;
    }

    //Reads in the CSV Swan Data file and returns the list of the records without the headings
    public static void getSwanDataList(String filePath) throws IOException {
        File file = new File(filePath);

        //Overrides old swanDataList if there is one
        swanDataList = new ArrayList<>();

        FileReader fileReader = null;
        CSVReader reader = null;
        try {
            fileReader = new FileReader(filePath);
            reader = new CSVReader(fileReader);

            //Get all the records in the CSV file
            List<String[]> contentList = reader.readAll();

            // 1 is used here to get rid of the headings, headings are not required for data handling
            for (int i = 1; i < contentList.size(); i++) {
                String[] record = contentList.get(i); //Get the record from the CSV file.

                //Constructs the data item
                SwanData data = new SwanData(record, i-1); //i-1 because of zero index list

                swanDataList.add(data);
            }
        } catch (IOException e){
            Log.d("ERROR", e.getMessage()); //Log the error on the debug console
            //Rethrows the exception. Resources should be freed
            throw e;
        } finally {
            //Close the file parser to free resources
            if(reader != null)
                reader.close();
            else if(fileReader != null)
                fileReader.close();
        }
    }

    //Reads in the CSV Swan Codes data file and returns the list of the records without the headings
    public static void getSwanCodesList(String filePath) throws IOException {
        File file = new File(filePath);

        //Overrides old swanDataList if there is one
        swanCodesList = new ArrayList<>();

        FileReader fileReader = null;
        CSVReader reader = null;
        try {
            fileReader = new FileReader(filePath);
            reader = new CSVReader(fileReader);

            //Get all teh records from the CSV file
            List<String[]> contentList = reader.readAll();

            //1 is used here to get rid of the headings, headings are not required for data handling later
            for (int i = 1; i < contentList.size(); i++) {
                String[] record = contentList.get(i); //Get the record from the CSV file.

                //Constructs the data item
                SwanCodes code = new SwanCodes(record, i-1); //i-1 because of zero index list

                swanCodesList.add(code);
            }
        } catch (IOException e){
            Log.d("ERROR", e.getMessage()); //Log the error on the debug console
            //Rethrows the exception. Resources should be freed
            throw e;
        } finally {
            //Close the file parser to free resources
            if(reader != null)
                reader.close();
            else if(fileReader != null)
                fileReader.close();
        }
    }
}
