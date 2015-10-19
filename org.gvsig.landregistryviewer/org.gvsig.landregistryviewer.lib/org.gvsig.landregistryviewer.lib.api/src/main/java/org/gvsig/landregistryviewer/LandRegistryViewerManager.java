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

import java.io.File;

import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.Geometry;

/**
 * This class is responsible of the management of the library's business logic.
 * It is the library's main entry point, and provides all the services to manage
 * {@link LandRegistryViewerBlock}s.
 * 
 * @see LandRegistryViewerBlock
 * @see LandRegistryViewerParcel
 * @author gvSIG team
 * @version $Id$
 */
public interface LandRegistryViewerManager {

    public void initialize( FeatureStore parcels, FeatureStore blocks );

    public void initialize( File parcelsFile, File blocksFile );

    public FeatureStore getParcels();

    public FeatureStore getBlocks();

    public LandRegistryViewerBlock getBlock( Geometry queryPoint ) throws LandRegistryViewerException;

}
