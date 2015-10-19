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
package org.gvsig.landregistryviewer.app.mainplugin;

import java.awt.GridBagConstraints;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.URL;
import org.apache.commons.lang3.BooleanUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.gvsig.andami.IconThemeHelper;
import org.gvsig.andami.PluginsLocator;
import org.gvsig.andami.PluginsManager;
import org.gvsig.andami.plugins.Extension;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.app.project.ProjectManager;
import org.gvsig.app.project.documents.view.ViewDocument;
import org.gvsig.app.project.documents.view.ViewManager;
import org.gvsig.app.project.documents.view.gui.IView;
import org.gvsig.fmap.crs.CRSFactory;
import org.gvsig.fmap.mapcontext.exceptions.LoadLayerException;
import org.gvsig.fmap.mapcontext.layers.FLayer;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;
import org.gvsig.fmap.mapcontrol.tools.Behavior.PointBehavior;
import org.gvsig.landregistryviewer.LandRegistryViewerLocator;
import org.gvsig.landregistryviewer.LandRegistryViewerManager;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.i18n.I18nManager;

/**
 * Andami extension to show LandRegistryViewer in the application.
 *
 * @author gvSIG Team
 * @version $Id$
 */
public class LandRegistryViewerExtension extends Extension {

    private static final Logger logger = LoggerFactory.getLogger(LandRegistryViewerExtension.class);

    private static final String MY_VIEW_NAME = "_Land_registry_viewer";

    private static final String TOOL_NAME = "LandRegistryViewer.infotool";
    private static final String ACTION_SETINFOTOOL = "view-show-land-regisytry-information";

    private LandRegistryViewerManager manager;

    private IView viewWindow;

    public void initialize() {
        // PluginsManager manager = PluginsLocator.getManager();
        IconThemeHelper.registerIcon("action", "view-show-land-regisytry-information", this);
    }

    public void postInitialize() {
        try {
            manager = LandRegistryViewerLocator.getManager();
            this.initializeStores();
            viewWindow = this.createViewWindow();

        } catch (LoadLayerException e) {

        }
    }

    /**
     * Execute the actions associated to this extension.
     */
    public void execute( String actionCommand ) {
        if (ACTION_SETINFOTOOL.equalsIgnoreCase(actionCommand)) {
            // Set the tool in the mapcontrol of the active view.

            ApplicationManager application = ApplicationLocator.getManager();

            if (application.getActiveWindow() != viewWindow) {
                return;
            }
            viewWindow.getMapControl().setTool(TOOL_NAME);
        }
    }

    /**
     * Check if tools of this extension are enabled.
     */
    public boolean isEnabled() {
        //
        // By default the tool is always enabled
        //
        return true;
    }

    /**
     * Check if tools of this extension are visible.
     */
    public boolean isVisible() {
        //
        // The tool is visible only when our view is active
        //

        ApplicationManager application = ApplicationLocator.getManager();

        return application.getActiveWindow() == viewWindow;
    }

    /**
     * Create the view in the project. Add the blocks layer to the view, and
     * register the tool for get info of the blocks.
     */
    private IView createViewWindow() throws LoadLayerException {
        I18nManager i18nManager = ToolsLocator.getI18nManager();
        ApplicationManager application = ApplicationLocator.getManager();

        ProjectManager projectManager = application.getProjectManager();

        // 1. Create a new view and set the name.
        ViewManager viewManager = (ViewManager) projectManager.getDocumentManager(ViewManager.TYPENAME);
        ViewDocument view = (ViewDocument) viewManager.createDocument();

        view.setName(i18nManager.getTranslation(MY_VIEW_NAME));
        // Setting view's projection to shapefile's known CRS
        view.getMapContext().setProjection(CRSFactory.getCRS("EPSG:23030"));

        // 2. Create a new layer with the blocks
        FLyrVect layer = (FLyrVect) application.getMapContextManager().createLayer(i18nManager.getTranslation("_Blocks"),
                this.manager.getBlocks());

        // Add a new property to the layer to identify.
        layer.setProperty("ViewerLayer", Boolean.TRUE);

        // 3. Add this layer to the mapcontext of the new view.
        view.getMapContext().getLayers().addLayer(layer);

        // 4. Add the view to the current project.
        projectManager.getCurrentProject().add(view);

        // 5. Force to show the view's window.
        IView viewWindow = (IView) viewManager.getMainWindow(view);

        application.getUIManager().addWindow(viewWindow, GridBagConstraints.CENTER);
        try {
            application.getUIManager().setMaximum((IWindow) viewWindow, true);
        } catch (PropertyVetoException e) {
            logger.info("Can't maximize view.", e);
        }

        // 6. Register my tool in the mapcontrol of the view.
        PropertiesOfBlockListener listener = new PropertiesOfBlockListener();
        viewWindow.getMapControl().addBehavior(TOOL_NAME, new PointBehavior(listener));

        return viewWindow;
    }

    /**
     * Open the stores and initialize the logic manager whit this stores.
     */
    private void initializeStores() {
        manager.initialize(getResource("data/properties.shp"), getResource("data/blocks.shp"));
    }

    /**
     * Get a resource as a File from a path name in the class path.
     *
     * @param pathname
     *
     * @return resource as a File
     */
    private File getResource( String pathname ) {
        URL res = this.getClass().getClassLoader().getResource(pathname);
        return new File(res.getPath());
    }

    public boolean isMyLayerActive() {
        ApplicationManager application = ApplicationLocator.getManager();

        if (application.getActiveWindow() != viewWindow) {
            return false;
        }
        ViewDocument viewDoc = this.viewWindow.getViewDocument();
        FLayer[] activeLayers = viewDoc.getMapContext().getLayers().getActives();
        for( int i = 0; i < activeLayers.length; i++ ) {
            boolean viewerLayer = BooleanUtils.isTrue((Boolean) activeLayers[i].getProperty("ViewerLayer"));
            if (viewerLayer) {
                return true;
            }
        }
        return false;
    }

}
