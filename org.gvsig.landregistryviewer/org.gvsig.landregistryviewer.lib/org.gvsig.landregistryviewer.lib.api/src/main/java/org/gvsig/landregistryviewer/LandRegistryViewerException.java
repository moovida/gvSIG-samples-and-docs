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

import org.gvsig.tools.exception.BaseException;

/**
 * Generic exception thrown in the LandRegistryViewer API when the exception or error
 * may be dealt by the program or the user of the program which is a client of
 * the LandRegistryViewer API.
 * 
 * @see {@link LandRegistryViewerBlock}
 * @see {@link LandRegistryViewerManager}
 * @author gvSIG team.
 * @version $Id$
 */
public class LandRegistryViewerException extends BaseException {

    private static final long serialVersionUID = 6756475060924237176L;

    private static final String MESSAGE =
        "An error has been produced in the LandRegistryViewer library";

    private static final String KEY = "_LandRegistryViewerException";

    /**
     * Constructor to be used in rare cases, usually you must create a new child
     * exception class for each case.
     * <strong>Don't use this constructor in child classes.</strong>
     */
    public LandRegistryViewerException() {
        super(MESSAGE, KEY, serialVersionUID);
    }

    /**
     * Constructor to be used in rare cases, usually you must create a new child
     * exception class for each case.
     * <p>
     * <strong>Don't use this constructor in child classes.</strong>
     * </p>
     * 
     * @param cause
     *            the original cause of the exception
     */
    public LandRegistryViewerException(Exception cause) {
        super(MESSAGE, cause, KEY, serialVersionUID);
    }

    /**
     * @see BaseException#BaseException(String, String, long).
     * @param message
     *            the default messageFormat to describe the exception
     * @param key
     *            the key to use to search a localized messageFormnata
     * @param code
     *            the unique code to identify the exception
     */
    protected LandRegistryViewerException(String message, String key, long code) {
        super(message, key, code);
    }

    /**
     * @see BaseException#BaseException(String, Throwable, String, long).
     * @param message
     *            the default messageFormat to describe the exception
     * @param cause
     *            the original cause of the exception
     * @param key
     *            the key to use to search a localized messageFormnata
     * @param code
     *            the unique code to identify the exception
     */
    protected LandRegistryViewerException(String message, Throwable cause,
        String key, long code) {
        super(message, cause, key, code);
    }
}
