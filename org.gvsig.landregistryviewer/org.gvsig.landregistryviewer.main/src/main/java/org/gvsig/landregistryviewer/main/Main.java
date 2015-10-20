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
package org.gvsig.landregistryviewer.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.gvsig.fmap.crs.CRSFactory;
import org.gvsig.fmap.geom.primitive.Envelope;
import org.gvsig.fmap.mapcontext.MapContext;
import org.gvsig.fmap.mapcontext.MapContextLocator;
import org.gvsig.fmap.mapcontext.layers.FLayer;
import org.gvsig.fmap.mapcontrol.MapControl;
import org.gvsig.fmap.mapcontrol.MapControlLocator;
import org.gvsig.fmap.mapcontrol.MapControlManager;
import org.gvsig.fmap.mapcontrol.tools.BehaviorException;
import org.gvsig.fmap.mapcontrol.tools.PanListenerImpl;
import org.gvsig.fmap.mapcontrol.tools.ZoomInListenerImpl;
import org.gvsig.fmap.mapcontrol.tools.ZoomOutRightButtonListener;
import org.gvsig.fmap.mapcontrol.tools.Behavior.Behavior;
import org.gvsig.fmap.mapcontrol.tools.Behavior.MoveBehavior;
import org.gvsig.fmap.mapcontrol.tools.Behavior.PointBehavior;
import org.gvsig.fmap.mapcontrol.tools.Behavior.RectangleBehavior;
import org.gvsig.fmap.mapcontrol.tools.Events.PointEvent;
import org.gvsig.fmap.mapcontrol.tools.Listeners.AbstractPointListener;
import org.gvsig.landregistryviewer.LandRegistryViewerBlock;
import org.gvsig.landregistryviewer.LandRegistryViewerException;
import org.gvsig.landregistryviewer.LandRegistryViewerLocator;
import org.gvsig.landregistryviewer.LandRegistryViewerManager;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerBlockJPanel;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerSwingLocator;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerSwingManager;
import org.gvsig.tools.library.impl.DefaultLibrariesInitializer;
import org.gvsig.tools.swing.api.ToolsSwingLocator;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main executable class for testing the LandRegistryViewer library.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String SHOWINFO_TOOL_NAME = "LandRegistryViewer.infotool";

    private LandRegistryViewerManager manager;
    private MapControlManager mapControlManager;
    private MapControl mapControl;
    private JFrame frame = null;
    private File baseFolder = null;

    public static void main( String args[] ) throws Exception {
        new DefaultLibrariesInitializer().fullInitialize();
        Main main = new Main();
        main.doMain();
    }

    public class PropertiesOfBlockListener extends AbstractPointListener {
        LandRegistryViewerBlockJPanel panel = null;

        public void point( PointEvent event ) throws BehaviorException {
            LandRegistryViewerSwingManager swingManager = LandRegistryViewerSwingLocator.getSwingManager();
            LandRegistryViewerManager manager = LandRegistryViewerLocator.getManager();
            try {
                LandRegistryViewerBlock block = manager.getBlock(event.getMapPoint());
                if (block == null) {
                    return;
                }
                if (panel == null) {
                    panel = swingManager.createLandRegistryViewerBlockJPanel(block);
                    panel.asJComponent().addAncestorListener(new AncestorListener(){
                        public void ancestorRemoved( AncestorEvent arg0 ) {
                            panel = null;
                        }
                        public void ancestorMoved( AncestorEvent arg0 ) {
                        }
                        public void ancestorAdded( AncestorEvent arg0 ) {
                        }
                    });
                    ToolsSwingLocator.getWindowManager().showWindow(panel.asJComponent(), "Block information",
                            WindowManager.MODE.TOOL);
                } else {
                    panel.setLandRegistryViewerBlock(block);
                }
            } catch (LandRegistryViewerException e) {
                logger.warn("Can't process point event.", e);
                throw new RuntimeException("Can't show properties of selected block.", e);
            }
        }

    }

    private void initBaseFolder() {
        File folder = null;
        File curdir = new File(System.getProperty("user.dir"));
        folder = new File(curdir, "data");
        if (!folder.exists()) {
            // Are we developing?
            folder = new File(curdir, "src/main/resources/data");
            if (!folder.exists()) {
                String msg = "Could not locate the cartography.\ncurdir='" + curdir.getAbsolutePath() + "'.";
                JOptionPane.showMessageDialog(null, msg);
                System.err.println(msg);
                System.exit(-1);
            }
        }
        baseFolder = folder.getParentFile();
    }

    private void initLandRegistryViewer() {
        manager = LandRegistryViewerLocator.getManager();
        manager.initialize(new File(baseFolder, "data/properties.shp"), new File(baseFolder, "data/blocks.shp"));
    }

    private void initMapControl() throws Exception {
        mapControlManager = MapControlLocator.getMapControlManager();

        mapControl = mapControlManager.createJMapControlPanel(null);
        mapControl.addBehavior("zoom", new Behavior[]{new RectangleBehavior(new ZoomInListenerImpl(mapControl)),
                new PointBehavior(new ZoomOutRightButtonListener(mapControl))});
        mapControl.addBehavior("pan", new MoveBehavior(new PanListenerImpl(mapControl)));
        mapControl.addBehavior(SHOWINFO_TOOL_NAME, new PointBehavior(new PropertiesOfBlockListener()));

        mapControl.setTool("pan");
        mapControl.getMapContext().setProjection(CRSFactory.getCRS("EPSG:23030"));
        FLayer layer = MapContextLocator.getMapContextManager().createLayer("Blocks", manager.getBlocks());
        mapControl.getMapContext().getLayers().addLayer(layer);
    }

    @SuppressWarnings("serial")
    private void initGUI() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton(new AbstractAction("Pan"){
            public void actionPerformed( ActionEvent e ) {
                mapControl.setTool("pan");
            }
        }));
        toolBar.add(new JButton(new AbstractAction("Zoom"){
            public void actionPerformed( ActionEvent e ) {
                mapControl.setTool("zoom");
            }
        }));
        toolBar.add(new JButton(new AbstractAction("Zoom all"){
            public void actionPerformed( ActionEvent e ) {
                zoomAll();
            }
        }));

        toolBar.add(new JButton(new AbstractAction("Info"){
            public void actionPerformed( ActionEvent e ) {
                mapControl.setTool(SHOWINFO_TOOL_NAME);;
            }
        }));

        frame = new JFrame("LandRegistryViewer example app");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(mapControl, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void doMain() throws Exception {
        initBaseFolder();
        initLandRegistryViewer();
        initMapControl();
        initGUI();
        zoomAll();
    }

    private void zoomAll() {
        MapContext mapContext = mapControl.getMapContext();
        Envelope all = mapContext.getLayers().getFullEnvelope();
        mapContext.getViewPort().setEnvelope(all);
        mapContext.invalidate();
    }

}
