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

import org.gvsig.fmap.dal.DALLibrary;
import org.gvsig.fmap.geom.GeometryLibrary;
import org.gvsig.tools.library.AbstractLibrary;
import org.gvsig.tools.library.Library;
import org.gvsig.tools.library.LibraryException;
import org.gvsig.tools.locator.ReferenceNotRegisteredException;

/**
 * Library for API initialization and configuration.
 * 
 * @author gvSIG team
 * @version $Id$
 */
public class LandRegistryViewerLibrary extends AbstractLibrary {

	@Override
	public void doRegistration() {
	    this.registerAsAPI(LandRegistryViewerLibrary.class);
        this.require(DALLibrary.class);
        this.require(GeometryLibrary.class);
	}
	
    @Override
    protected void doInitialize() throws LibraryException {
        // Do nothing
    }

    @Override
    protected void doPostInitialize() throws LibraryException {
        // Validate there is any implementation registered.
        LandRegistryViewerManager manager = LandRegistryViewerLocator.getManager();
        if (manager == null) {
            throw new ReferenceNotRegisteredException(
                LandRegistryViewerLocator.MANAGER_NAME, LandRegistryViewerLocator
                    .getInstance());
        }
    }

}
