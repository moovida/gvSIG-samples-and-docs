/**
 * gvSIG. Desktop Geographic Information System.
 *
 * Copyright (C) 2007-2012 gvSIG Association.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * For any additional information, do not hesitate to contact us
 * at info AT gvsig.com, or visit our website www.gvsig.com.
 */
package org.gvsig.landregistryviewer.swing;

import org.gvsig.tools.locator.BaseLocator;

/**
 * This locator is the entry point for the LandRegistryViewer swing library,
 * providing access to all LandRegistryViewer swing services through the
 * {@link LandRegistryViewerSwingManager} .
 * 
 * @author gvSIG team
 * @version $Id$
 */
public class LandRegistryViewerSwingLocator extends BaseLocator {

    /**
     * LandRegistryViewer swing manager name.
     */
    public static final String SWING_MANAGER_NAME = "LandRegistryViewer.swing.manager";

    /**
     * LandRegistryViewer swing manager description.
     */
    public static final String SWING_MANAGER_DESCRIPTION = "LandRegistryViewer UIManager";

    private static final String LOCATOR_NAME = "LandRegistryViewer.swing.locator";

    /**
     * Unique instance.
     */
    private static final LandRegistryViewerSwingLocator INSTANCE = new LandRegistryViewerSwingLocator();

    /**
     * Return the singleton instance.
     * 
     * @return the singleton instance
     */
    public static LandRegistryViewerSwingLocator getInstance() {
        return INSTANCE;
    }

    /**
     * Return the Locator's name
     * 
     * @return a String with the Locator's name
     */
    public final String getLocatorName() {
        return LOCATOR_NAME;
    }

    /**
     * Registers the Class implementing the PersistenceManager interface.
     * 
     * @param clazz
     *            implementing the PersistenceManager interface
     */
    public static void registerSwingManager( Class< ? extends LandRegistryViewerSwingManager> clazz ) {
        getInstance().register(SWING_MANAGER_NAME, SWING_MANAGER_DESCRIPTION, clazz);
    }

    /**
     * Gets the instance of the manager registered.
     * 
     * @return the manager.
     */
    public static LandRegistryViewerSwingManager getSwingManager() {
        return (LandRegistryViewerSwingManager) getInstance().get(SWING_MANAGER_NAME);
    }

}
