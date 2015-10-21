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

import org.gvsig.landregistryviewer.LandRegistryViewerBlock;
import org.gvsig.landregistryviewer.LandRegistryViewerException;
import org.gvsig.landregistryviewer.LandRegistryViewerManager;

/**
 * This class is responsible of the management of the library's swing user
 * interface. It is the swing library's main entry point, and provides all the
 * services to manage library swing components.
 * 
 * @see LandRegistryViewerBlockJPanel
 * @author gvSIG team
 * @version $Id$
 */
public interface LandRegistryViewerSwingManager {

    /**
     * Returns the panel associated to a {@link LandRegistryViewerBlock}.
     * 
     * @param cookie
     *            {@link LandRegistryViewerBlock} contained on the panel
     * @return a {@link LandRegistryViewerBlockJPanel} with the panel of the
     *         {@link LandRegistryViewerBlock}
     * @throws LandRegistryViewerException 
     * @see LandRegistryViewerBlockJPanel
     * @see LandRegistryViewerBlock
     */
    public LandRegistryViewerBlockJPanel createLandRegistryViewerBlockJPanel( LandRegistryViewerBlock block )
            throws LandRegistryViewerException;

    /**
     * Returns the {@link LandRegistryViewerManager}.
     * 
     * @return {@link LandRegistryViewerManager}
     * @see LandRegistryViewerManager
     */
    public LandRegistryViewerManager getManager();

}
