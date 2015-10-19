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
package org.gvsig.landregistryviewer.app.mainplugin;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.gvsig.andami.ui.mdiManager.MDIManager;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.fmap.mapcontrol.tools.BehaviorException;
import org.gvsig.fmap.mapcontrol.tools.Events.PointEvent;
import org.gvsig.fmap.mapcontrol.tools.Listeners.AbstractPointListener;
import org.gvsig.landregistryviewer.LandRegistryViewerBlock;
import org.gvsig.landregistryviewer.LandRegistryViewerException;
import org.gvsig.landregistryviewer.LandRegistryViewerLocator;
import org.gvsig.landregistryviewer.LandRegistryViewerManager;
import org.gvsig.landregistryviewer.swing.JLandRegistryViewerBlockPanel;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerSwingLocator;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerSwingManager;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.i18n.I18nManager;
import org.gvsig.tools.swing.api.ToolsSwingLocator;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesOfBlockListener extends AbstractPointListener {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesOfBlockListener.class);
	
	private JLandRegistryViewerBlockPanel blockPanel = null;
	
    public void point(PointEvent event) throws BehaviorException {
        ApplicationManager application = ApplicationLocator.getManager();
        I18nManager i18nManager = ToolsLocator.getI18nManager();
        LandRegistryViewerManager logicManager = LandRegistryViewerLocator.getManager();
        LandRegistryViewerSwingManager guiManager = LandRegistryViewerSwingLocator.getSwingManager();

        LandRegistryViewerBlock block;
        try {
            block = logicManager.getBlock(event.getMapPoint());
            if (block == null) {
                return;
            }
            if( this.blockPanel == null ) {
            	this.blockPanel = guiManager.createJLandRegistryViewerBlockPanel(block);
            	//this.blockPanel.setCloseButtonVisible(false);
            	JComponent panel = blockPanel.asJComponent();
	            ToolsSwingLocator.getWindowManager().showWindow(
	            		panel, 
		                i18nManager.getTranslation("_Block_registry_information"),
		                WindowManager.MODE.TOOL
		            );
	            panel.addAncestorListener(new AncestorListener() {
					public void ancestorRemoved(AncestorEvent arg0) {
						blockPanel = null; 
					}
					public void ancestorMoved(AncestorEvent arg0) {
					}
					public void ancestorAdded(AncestorEvent arg0) {
					}
				});
            } else {
            	this.blockPanel.setLandRegistryViewerBlock(block);
            }
        } catch (LandRegistryViewerException e) {
        	logger.warn("Can't show block registry information",e);
        	application.message(
        			i18nManager.getTranslation("_Cant_show_block_registry_information"), 
        			JOptionPane.WARNING_MESSAGE
        	);
        }
    }

}
