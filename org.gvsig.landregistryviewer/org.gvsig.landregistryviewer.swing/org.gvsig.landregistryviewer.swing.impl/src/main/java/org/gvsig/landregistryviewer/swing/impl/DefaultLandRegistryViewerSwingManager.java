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
package org.gvsig.landregistryviewer.swing.impl;

import org.gvsig.landregistryviewer.LandRegistryViewerBlock;
import org.gvsig.landregistryviewer.LandRegistryViewerException;
import org.gvsig.landregistryviewer.LandRegistryViewerLocator;
import org.gvsig.landregistryviewer.LandRegistryViewerManager;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerBlockJPanel;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerSwingManager;

/**
 * Default implementation of the {@link LandRegistryViewerSwingManager}.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultLandRegistryViewerSwingManager implements LandRegistryViewerSwingManager {

    private LandRegistryViewerManager manager;

    public DefaultLandRegistryViewerSwingManager() {
        this.manager = LandRegistryViewerLocator.getManager();
    }

    public LandRegistryViewerBlockJPanel createLandRegistryViewerBlockJPanel( LandRegistryViewerBlock block )
            throws LandRegistryViewerException {
        LandRegistryViewerBlockJPanel panel = new DefaultLandRegistryViewerBlockJPanel(this, block);
        return panel;
    }

    public LandRegistryViewerManager getManager() {
        return this.manager;
    }

}
