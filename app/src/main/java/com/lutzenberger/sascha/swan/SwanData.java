package com.lutzenberger.sascha.swan;

import com.lutzenberger.sascha.swandata.R;

import static com.lutzenberger.sascha.swandata.Constants.context;


/**
 * This class represents the SwanCodes and gives the ability of updating the file.
 *
 * This object is used for the swan codes to be updated, has to be stored in memory during runtime,
 * this could cause a problem because many objects will be stored in memory if this gets bigger,
 * therefore more efficient method will be needed, however if the datafile has to be updated
 * everything has to be read and written to the file again, so therefore this is required.
 *
 * @author Sascha Lutzenberger
 * @version 1.01 - 31.07.2015
 *
 */
public class SwanData extends Data {
    //String array to hold the android name of the attributes
    private static final String[] ATTRIBUTES;
    //String array to hold the CSV header names
    private static final String[] HEADER_NAMES;
    //Integer array to hold the position of the items in the data file
    private static final int[] ATTRIBUTES_DATA_FILE_POSITION;

    static {
        //Load the resources from the context
        ATTRIBUTES = context.getResources().getStringArray(R.array.swan_data_attributes);
        HEADER_NAMES = context.getResources().getStringArray(R.array.swan_data_header);
        ATTRIBUTES_DATA_FILE_POSITION = context.getResources().getIntArray(
                R.array.swan_data_data_file_position);
    }

    /**
     * Returns the header for the CSV data file.
     *
     * @return A String array containing the CSV data file header.
     */
    public static String[] getCSVFileHeader(){
        return getCSVHeaderOrder(HEADER_NAMES);
    }

    private static String[] getCSVHeaderOrder(final String[] source){
        int length = ATTRIBUTES.length;
        String[] dataRecord = new String[length];

        //This loop sorts the attribute values in that order they are in the data file
        for(int i=0;i<length;i++){
            int index = ATTRIBUTES_DATA_FILE_POSITION[i];
            dataRecord[index] = source[i];
        }

        return dataRecord;
    }

    public static SwanData getEmptyData(){
        return new SwanData();
    }

    //The array of the attribute_values has to be the same as the length as the array attributes
    private final String[] attribute_values = new String[ATTRIBUTES.length];

    public SwanData(String[] record) {
        for(int i=0;i<ATTRIBUTES.length;i++) {
            //This loads the index of the attribute in the data file
            int index = ATTRIBUTES_DATA_FILE_POSITION[i];
            //Saves the value of the attribute at the given index.
            attribute_values[i] = record[index];
        }
    }

    private SwanData(){
        for(int i=0;i<ATTRIBUTES.length;i++) {
            attribute_values[i] = "";
        }
    }

    /**
     * Returns the AttributeName at the given index.
     *
     * @param index The index where the name of the attribute is needed
     *
     * @return A string containing the name of the attribute
     */
    @Override
    public String getAttributeNameAt(int index){
        //if index is not in list return an empty String
        if(index < 0 || index >= ATTRIBUTES.length)
            return "";

        //return the attribute name
        return ATTRIBUTES[index];
    }

    /**
     * Method to get the data at a specific listPosition.
     *
     * @param listPosition The listPosition of the item to get
     *
     * @return The data item at this listPosition
     */
    @Override
    public String getDataAt(int listPosition) {
        //if index is not in list return an empty String
        if(listPosition < 0 || listPosition >= ATTRIBUTES.length)
            return "";

        //return the data at this attribute
        return attribute_values[listPosition];
    }

    /**
     * Method to return the size of the array of the attributes.
     *
     * @return The number of attributes stored.
     */
    @Override
    public int getNumberOfAttributes(){
        return ATTRIBUTES.length;
    }

    /**
     * Returns the position of the column in the data list.
     * Returns -1 if the column name was not found in the list.
     *
     * @param columnName The column name
     * @return The index of the data in the list.
     */
    private static int getListPosition(String columnName) {
        for(int i=0;i<ATTRIBUTES.length;i++) {
            if(columnName.equals(ATTRIBUTES[i]))
                return i;
        }

        return -1;
    }

    @Override
    public String getDarvic() {
        //the index of the the column representing the darvic code
        int darvicIndex = getListPosition("darvic");

        return attribute_values[darvicIndex];
    }

    @Override
    public String[] getDataRecord() {
        //gets all attributes in the order they have to be written to the data file
        return getCSVHeaderOrder(attribute_values);
    }

    @Override
    public void setDataAtIndex(int index, String newData) {
        if(index < 0 || index >= getNumberOfAttributes())
            return;
        //sets the data at the given index to the new data
        attribute_values[index] = newData;
    }
}
