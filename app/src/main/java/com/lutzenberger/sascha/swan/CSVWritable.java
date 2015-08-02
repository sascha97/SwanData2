package com.lutzenberger.sascha.swan;

/**
 * This interfaces must be implemented by all classes which want to support the DataFileWriter
 * operation.
 *
 * This is used to get a String array of all the attributes which need to be written to the CSV file
 * from the class.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 31.07.2015
 *
 */
public interface CSVWritable {
    /**
     * This method returns all the attributes which need to be written to a CSV file.
     * The length has to match the length of the CSV Header row. And the order has to match
     * the CSV Header as well.
     *
     * @return A string array with the data record.
     */
    String[] getDataRecord();
}