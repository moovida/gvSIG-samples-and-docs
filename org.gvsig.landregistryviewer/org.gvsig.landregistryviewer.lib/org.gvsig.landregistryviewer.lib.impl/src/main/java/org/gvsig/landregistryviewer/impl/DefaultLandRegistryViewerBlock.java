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
package org.gvsig.landregistryviewer.impl;

import java.util.ArrayList;
import java.util.List;

import org.gvsig.fmap.dal.exception.DataException;
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
import org.gvsig.landregistryviewer.LandRegistryViewerParcel;
import org.gvsig.tools.dispose.DisposableIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default {@link LandRegistryViewerBlock} implementation.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class DefaultLandRegistryViewerBlock implements LandRegistryViewerBlock {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLandRegistryViewerBlock.class);

    private static final String PARCEL_CODE = "PARCELA";
    private static final String PARCEL_CREATIONDATE = "FECHAALTA";
    private static final String PARCEL_MUNICODE = "MUNICIPIO";

    private DefaultLandRegistryViewerManager manager;
    private Geometry shape;

    /**
     * {@link DefaultLandRegistryViewerBlock} constructor with a
     * {@link LandRegistryViewerManager}.
     * 
     * @param manager
     *            to use in the service
     */
    public DefaultLandRegistryViewerBlock( DefaultLandRegistryViewerManager manager, Geometry shape ) {
        this.manager = manager;
        this.shape = shape;
    }

    public LandRegistryViewerManager getManager() {
        return this.manager;
    }

    public Geometry getShape() {
        return this.shape;
    }

    public List<LandRegistryViewerParcel> getParcels() throws LandRegistryViewerException {

        FeatureSet set = null;
        DisposableIterator it = null;
        List<LandRegistryViewerParcel> intersectingParcelsList = new ArrayList<LandRegistryViewerParcel>();

        try {
            FeatureStore parcelsStore = this.manager.getParcels();
            String attrGeomName = parcelsStore.getDefaultFeatureType().getDefaultGeometryAttributeName();
            FeatureQuery query = parcelsStore.createFeatureQuery();
            query.setFilter(new IntersectsEvaluator(attrGeomName, this.shape));
            set = this.manager.getParcels().getFeatureSet(query);
            if (set.isEmpty()) {
                return null;
            }
            it = set.fastIterator();
            while( it.hasNext() ) {
                Feature f = (Feature) it.next();
                LandRegistryViewerParcel parcel = new DefaultLandRegistryViewerParcel(this.manager,
                        f.getString(PARCEL_CODE), f.getGeometry(attrGeomName), f.getInt(PARCEL_CREATIONDATE),
                        f.getInt(PARCEL_MUNICODE));
                intersectingParcelsList.add(parcel);
            }
            return intersectingParcelsList;

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

}
