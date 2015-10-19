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
import java.net.URL;

import org.gvsig.tools.junit.AbstractLibraryAutoInitTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API compatibility tests for {@link LandRegistryViewerManager}
 * implementations.
 * 
 * @author gvSIG Team
 * @version $Id$
 */
public abstract class LandRegistryViewerManagerTest extends
		AbstractLibraryAutoInitTestCase {

	private static final Logger LOG = LoggerFactory
			.getLogger(LandRegistryViewerManagerTest.class);

	protected LandRegistryViewerManager manager;

	private File getResource(String pathname) {
		URL res = this.getClass().getClassLoader().getResource(pathname);
		return new File(res.getPath());
	}

	@Override
	protected void doSetUp() throws Exception {
		manager = LandRegistryViewerLocator.getManager();
	}

	public void testManagerInitialize() throws Exception {
		try {
			manager.initialize(
					getResource("data/properties.shp"),
					getResource("data/blocks.shp")
			);
		} catch (Exception e) {
			LOG.error("Can't initialize manager", e);
			throw e;
		}
	}

	public void testGetBlock() throws Exception {
		// TODO
	}

	public void testGetProperties() throws Exception {
		// TODO
	}

}
