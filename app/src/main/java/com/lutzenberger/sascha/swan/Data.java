package com.lutzenberger.sascha.swan;

import android.support.annotation.NonNull;

/**
 * This class is the Base class of all Data load from data files.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 31.07.2015
 *
 */
public abstract class Data implements CSVWritable, DarvicSearchable {
    private int index;

    /**
     * Get the index of the item in the data list.
     *
     * @return The index of the item in the data list.
     */
    public int getIndex(){
        return index;
    }

    /**
     * This method is used to set the index from the search activity, so that the data knows at
     * which position of the list the data is.
     *
     * @param index The index at the list
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns the AttributeName at the given index.
     *
     * @param index The index where the name of the attribute is needed
     *
     * @return A string containing the name of the attribute
     */
    @NonNull
    public abstract  String getAttributeNameAt(int index);

    /**
     * Method to get the data at a specific listPosition.
     *
     * @param listPosition The listPosition of the item to get
     *
     * @return The data item at this listPosition
     */
    @NonNull
    public abstract String getDataAt(int listPosition);

    /**
     * Method to return the size of the array of the attributes.
     *
     * @return The number of attributes stored.
     */
    public abstract int getNumberOfAttributes();

    /**
     * This method returns a simple String telling the name of the class
     *
     * @return The name of the class
     */
    @NonNull
    public String getSimpleName(){
        return getClass().getSimpleName();
    }

    /**
     * This method allows to change data at a certain index.
     *
     * If index doesn't exist nothing will be changed
     *  @param index the index where data has to be changed.
     * @param newData the new data for the given index.
     */
    public abstract void setDataAtIndex(int index, @NonNull String newData);

}