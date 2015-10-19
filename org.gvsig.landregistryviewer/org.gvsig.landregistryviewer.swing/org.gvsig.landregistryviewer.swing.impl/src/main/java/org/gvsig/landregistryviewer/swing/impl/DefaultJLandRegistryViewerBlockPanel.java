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
package org.gvsig.landregistryviewer.swing.impl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;

import org.gvsig.landregistryviewer.LandRegistryViewerBlock;
import org.gvsig.landregistryviewer.LandRegistryViewerException;
import org.gvsig.landregistryviewer.LandRegistryViewerParcel;
import org.gvsig.landregistryviewer.swing.JLandRegistryViewerBlockPanel;
import org.gvsig.landregistryviewer.swing.LandRegistryViewerSwingManager;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.i18n.I18nManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultJLandRegistryViewerBlockPanel extends
		DefaultJLandRegistryViewerBlockPanelLayout implements
		JLandRegistryViewerBlockPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3638250585757641443L;

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultJLandRegistryViewerBlockPanel.class);

	private LandRegistryViewerBlock block;
	private LandRegistryViewerSwingManager uimanager;

	protected JButton accept = null;
	protected String columnNames[] = new String[] { 
		"Creation Date",
		"Municipality code", 
		"Piece of ground" 
	};

	public DefaultJLandRegistryViewerBlockPanel(
			DefaultLandRegistryViewerSwingManager uimanager,
			LandRegistryViewerBlock block) throws LandRegistryViewerException {

		this.uimanager = uimanager;

		initComponents();
		translateComponents();
		
		this.setLandRegistryViewerBlock(block);
	}

	public void setLandRegistryViewerBlock(LandRegistryViewerBlock block) throws LandRegistryViewerException {
		this.block = block;

		String[][] data = null;
		if( block == null ) {
			data = new String[1][3];
		} else {
			List<LandRegistryViewerParcel> properties;
			try {
				properties = this.block.getParcels();
				data = new String[properties.size()][3];
				for (int row = 0; row < properties.size(); row++) {
					LandRegistryViewerParcel property = properties.get(row);
					data[row][0] = String.valueOf(property.getCreationDate());
					data[row][1] = String.valueOf(property.getMunicipalityCode());
					data[row][2] = property.getCode();
				}
			} catch (LandRegistryViewerException e) {
				logger.warn("Can't fill block information.",e);
				throw e;
			}
		}
		DefaultTableModel model = (DefaultTableModel) propertiesTable.getModel();
		model.setDataVector(data, columnNames);
	}
	
	private void initComponents() throws LandRegistryViewerException {
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closePanel();
			}
		});
		String[][] data = new String[1][3];
		propertiesTable.setModel(new DefaultTableModel(data, columnNames));	
		this.setPreferredSize(new Dimension(400, 250));
	}

	private void translateComponents() {
		I18nManager i18nManager = ToolsLocator.getI18nManager();
		this.closeButton.setText(i18nManager.getTranslation("_Close"));
		this.captionLabel.setText(i18nManager.getTranslation("_Block_information"));
		columnNames = new String[] {
			i18nManager.getTranslation("_Creation_Date"),
			i18nManager.getTranslation("_Municipality_code"),
			i18nManager.getTranslation("_Piece_of_ground") 
		};

		DefaultTableModel model = (DefaultTableModel) propertiesTable.getModel();
		model.setColumnIdentifiers(columnNames);
	}

	public JComponent asJComponent() {
		return this;
	}

	public LandRegistryViewerBlock getLandRegistryViewerBlock() {
		return this.block;
	}

	private void closePanel() {
		this.setVisible(false);
	}

	public void setCloseButtonVisible(boolean visible) {
		closeButton.setVisible(visible);
	}

	public boolean isCloseButtonVisible() {
		return closeButton.isVisible();
	}

}
