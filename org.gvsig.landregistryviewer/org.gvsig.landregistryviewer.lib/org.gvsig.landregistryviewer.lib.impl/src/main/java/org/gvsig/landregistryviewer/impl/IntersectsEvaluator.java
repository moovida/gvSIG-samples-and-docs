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

import java.text.MessageFormat;

import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.operation.GeometryOperationException;
import org.gvsig.fmap.geom.operation.GeometryOperationNotSupportedException;
import org.gvsig.tools.evaluator.AbstractEvaluator;
import org.gvsig.tools.evaluator.EvaluatorData;
import org.gvsig.tools.evaluator.EvaluatorException;

public class IntersectsEvaluator extends AbstractEvaluator {

    private Geometry op1geom;
    private String op2attrname;
    private String where;

    public IntersectsEvaluator( String op1attrname, Geometry op2geom )
            throws GeometryOperationNotSupportedException, GeometryOperationException {
        this.op1geom = op2geom;
        this.op2attrname = op1attrname;
        this.where = MessageFormat.format(" intersects(GeomFromText('{1}','{2}'),{0}) ",
                new Object[]{this.op1geom.convertToWKT(), "", // this.op2geom.getCRS()
                        this.op2attrname});
    }

    public String getName() {
        return "intersets";
    }

    public Object evaluate( EvaluatorData data ) throws EvaluatorException {
        Geometry op1geom = (Geometry) data.getDataValue(this.op2attrname);
        try {
            return new Boolean(this.op1geom.intersects(op1geom));
        } catch (Exception e) {
            throw new EvaluatorException(e);
        }
    }

    public String getCQL() {
        return this.where;
    }

}
