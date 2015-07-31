package com.lutzenberger.sascha.swan;

/**
 * This class is the Base class of all Data load from data files.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 31.07.2015
 *
 */
public abstract class Data implements DarvicSearchable {
    /**
     * Returns the AttributeName at the given index.
     *
     * @param index The index where the name of the attribute is needed
     *
     * @return A string containing the name of the attribute
     */
    public abstract  String getAttributeNameAt(int index);

    /**
     * Method to get the data at a specific listPosition.
     *
     * @param listPosition The listPosition of the item to get
     *
     * @return The data item at this listPostion
     */
    public abstract String getDataAt(int listPosition);

    /**
     * Method to return the size of the array of the attribues.
     *
     * @return The number of attributes stored.
     */
    public abstract int getNumberOfAttribues();

}