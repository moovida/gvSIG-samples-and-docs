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

import java.util.Date;
import java.util.List;

import org.gvsig.fmap.geom.Geometry;

/**
 * Represents a LandRegistryViewer element.
 * 
 * @author gvSIG team
 * @version $Id$
 */
public interface LandRegistryViewerBlock {

    /**
     * Returns the {@link LandRegistryViewerManager}
     * 
     * @return {@link LandRegistryViewerManager}
     * @see {@link LandRegistryViewerManager}
     */
    public LandRegistryViewerManager getManager();
    
    /**
     * Returns the LandRegistryViewer's shape of the block.
     * 
     * @return the shape associated to a LandRegistryViewerBlock as a Geometry
     * @throws LandRegistryViewerMessageException
     *             if there is an error getting the LandRegistryViewer's message
     */
    public Geometry getShape();
    
    public List<LandRegistryViewerProperty> getProperties() throws LandRegistryViewerException;

}
