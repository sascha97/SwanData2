package com.lutzenberger.sascha.swan;

/**
 * This interfaces must be implemented by all classes which want to be searched by darvic code
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 31.07.2015
 *
 */
public interface DarvicSearchable {
    /**
     * Returns the darvic code of the DarvicSearchable class
     *
     * @return the darvic code
     */
    String getDarvic();
}
