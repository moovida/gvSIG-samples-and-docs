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
package org.gvsig.landregistryviewer;

import org.gvsig.tools.locator.BaseLocator;
import org.gvsig.tools.locator.Locator;
import org.gvsig.tools.locator.LocatorException;

/**
 * This locator is the entry point for the LandRegistryViewer library, providing
 * access to all LandRegistryViewer services through the {@link LandRegistryViewerManager}
 * .
 * 
 * @author gvSIG team
 * @version $Id$
 */
public class LandRegistryViewerLocator extends BaseLocator {

    /**
     * LandRegistryViewer manager name.
     */
    public static final String MANAGER_NAME = "LandRegistryViewer.manager";

    /**
     * LandRegistryViewer manager description.
     */
    public static final String MANAGER_DESCRIPTION = "LandRegistryViewer Manager";

    private static final String LOCATOR_NAME = "LandRegistryViewer.locator";

    /**
     * Unique instance.
     */
    private static final LandRegistryViewerLocator INSTANCE = new LandRegistryViewerLocator();

    /**
     * Return the singleton instance.
     * 
     * @return the singleton instance
     */
    public static LandRegistryViewerLocator getInstance() {
        return INSTANCE;
    }

    /**
     * Return the Locator's name.
     * 
     * @return a String with the Locator's name
     */
    public final String getLocatorName() {
        return LOCATOR_NAME;
    }

    /**
     * Return a reference to the LandRegistryViewerManager.
     * 
     * @return a reference to the LandRegistryViewerManager
     * @throws LocatorException
     *             if there is no access to the class or the class cannot be
     *             instantiated
     * @see Locator#get(String)
     */
    public static LandRegistryViewerManager getManager() throws LocatorException {
        return (LandRegistryViewerManager) getInstance().get(MANAGER_NAME);
    }

    /**
     * Registers the Class implementing the LandRegistryViewerManager interface.
     * 
     * @param clazz
     *            implementing the LandRegistryViewerManager interface
     */
    public static void registerManager( Class< ? extends LandRegistryViewerManager> clazz ) {
        getInstance().register(MANAGER_NAME, MANAGER_DESCRIPTION, clazz);
    }

}
