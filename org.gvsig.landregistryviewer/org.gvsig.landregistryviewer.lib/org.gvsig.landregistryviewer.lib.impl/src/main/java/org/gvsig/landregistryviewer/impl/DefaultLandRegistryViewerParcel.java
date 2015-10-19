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

import org.gvsig.fmap.geom.Geometry;
import org.gvsig.landregistryviewer.LandRegistryViewerManager;
import org.gvsig.landregistryviewer.LandRegistryViewerParcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLandRegistryViewerParcel implements LandRegistryViewerParcel {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLandRegistryViewerParcel.class);

    private String code;
    private int municipioCode;
    private int creationDate;
    private Geometry shape;
    private DefaultLandRegistryViewerManager manager;

    public DefaultLandRegistryViewerParcel( DefaultLandRegistryViewerManager manager, String code, Geometry shape,
            int creationDate, int municipioCode ) {
        this.manager = manager;
        this.code = code;
        this.municipioCode = municipioCode;
        this.creationDate = creationDate;
        this.shape = shape;
    }

    public String getCode() {
        return this.code;
    }

    public int getCreationDate() {
        return this.creationDate;
    }

    public LandRegistryViewerManager getManager() {
        return this.manager;
    }

    public int getMunicipalityCode() {
        return this.municipioCode;
    }

    public Geometry getShape() {
        return this.shape;
    }

}
