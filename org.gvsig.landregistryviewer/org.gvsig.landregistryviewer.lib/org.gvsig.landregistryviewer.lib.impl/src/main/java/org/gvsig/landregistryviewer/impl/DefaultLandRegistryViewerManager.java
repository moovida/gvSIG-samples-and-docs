/**
 * gvSIG. Desktop Geographic Information System.
 *
 * Copyright (C) 2007-2012 gvSIG Association.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * For any additional information, do not hesitate to contact us at info AT
 * gvsig.com, or visit our website www.gvsig.com.
 */
package org.gvsig.landregistryviewer.impl;

import java.io.File;

import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataStoreParameters;
import org.gvsig.fmap.dal.exception.DataException;
import org.gvsig.fmap.dal.exception.InitializeException;
import org.gvsig.fmap.dal.exception.ProviderNotRegisteredException;
import org.gvsig.fmap.dal.exception.ValidateDataParametersException;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureQuery;
import org.gvsig.fmap.dal.feature.FeatureSet;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.operation.GeometryOperationException;
import org.gvsig.fmap.geom.operation.GeometryOperationNotSupportedException;
import org.gvsig.landregistryviewer.LandRegistryViewerBlock;
import org.gvsig.landregistryviewer.LandRegistryViewerException;
import org.gvsig.landregistryviewer.LandRegistryViewerManager;
import org.gvsig.tools.dispose.DisposableIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default {@link LandRegistryViewerManager} implementation.
 *
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultLandRegistryViewerManager implements LandRegistryViewerManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLandRegistryViewerManager.class);

    private FeatureStore properties;
    private FeatureStore blocks;

    public void initialize(FeatureStore properties, FeatureStore blocks) {
        this.properties = properties;
        this.blocks = blocks;
    }

    public void initialize(File properties, File blocks) {
        this.initialize(openShape(properties), openShape(blocks));
    }

    public LandRegistryViewerBlock getBlock(Geometry point)
            throws LandRegistryViewerException {

        FeatureSet set = null;
        DisposableIterator it = null;

        try {
            String attrGeomName = blocks.getDefaultFeatureType().getDefaultGeometryAttributeName();
            FeatureQuery query = blocks.createFeatureQuery();
            query.setFilter(new IntersectsEvaluator(attrGeomName, point));
            set = blocks.getFeatureSet(query);
            if (set.isEmpty()) {
                return null;
            }
            it = set.fastIterator();
            Feature f = (Feature) it.next();
            LandRegistryViewerBlock block = new DefaultLandRegistryViewerBlock(
                    this,
                    f.getGeometry(attrGeomName)
            );
            return block;

        } catch (DataException e) {
            throw new LandRegistryViewerException(e);
        } catch (GeometryOperationNotSupportedException e) {
            throw new LandRegistryViewerException(e);
        } catch (GeometryOperationException e) {
            throw new LandRegistryViewerException(e);
        } finally {
            if (it != null) {
                it.dispose();
            }
            if (set != null) {
                set.dispose();
            }
        }
    }

    public FeatureStore getProperties() {
        return this.properties;
    }

    public FeatureStore getBlocks() {
        return this.blocks;
    }

    /**
     * Open the file as a feature store of type shape.
     *
     * @param shape file to be opened
     *
     * @return the feature store
     */
    private FeatureStore openShape(File shape) {
        try {

            DataStoreParameters parameters;
            DataManager manager = DALLocator.getDataManager();

            parameters = manager.createStoreParameters("Shape");
            parameters.setDynValue("shpfile", shape);
            parameters.setDynValue("crs", "EPSG:23030");
            return (FeatureStore) manager.openStore("Shape", parameters);

        } catch (InitializeException e) {
            logger.error(e.getMessageStack());
            throw new RuntimeException(e);
        } catch (ProviderNotRegisteredException e) {
            logger.error(e.getMessageStack());
            throw new RuntimeException(e);
        } catch (ValidateDataParametersException e) {
            logger.error(e.getMessageStack());
            throw new RuntimeException(e);
        }
    }

}
