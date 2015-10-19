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
import org.gvsig.tools.swing.api.Component;

/**
 * A panel to show a {@link LandRegistryViewerBlock}.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public interface JLandRegistryViewerBlockPanel extends Component {

	/**
     * Returns the {@link LandRegistryViewerBlock} associated with the panel.
     * 
     * @return the {@link LandRegistryViewerBlock}
     */
    public LandRegistryViewerBlock getLandRegistryViewerBlock();

    /**
     * Sets the {@link LandRegistryViewerBlock} associated with the panel.
     * 
     * @param block
     * @throws LandRegistryViewerException
     */
    public void setLandRegistryViewerBlock(LandRegistryViewerBlock block) throws LandRegistryViewerException;
	
    /**
     * Set visible or not visible the close button of the panel
     * 
     * @param visible
     */
	public void setCloseButtonVisible(boolean visible);
	
	/**
	 * Get the state of visible for the close button of the panel.
	 * 
	 * @return true if close button is visible, otherwise false.
	 */
	public boolean isCloseButtonVisible();
	

}
