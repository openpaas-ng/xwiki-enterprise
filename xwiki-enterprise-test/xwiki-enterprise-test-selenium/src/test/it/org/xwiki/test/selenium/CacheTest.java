/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.test.selenium;

import org.xwiki.test.selenium.framework.AbstractXWikiTestCase;
import org.xwiki.test.selenium.framework.FlamingoSkinExecutor;
import org.xwiki.test.selenium.framework.XWikiTestSuite;

import junit.framework.Test;

/**
 * Verify the caching features of XWiki.
 * 
 * @version $Id$
 */
public class CacheTest extends AbstractXWikiTestCase
{
    private static final String SYNTAX = "xwiki/2.1";

    public static Test suite()
    {
        XWikiTestSuite suite = new XWikiTestSuite("Verify the caching features of XWiki");
        suite.addTestSuite(CacheTest.class, FlamingoSkinExecutor.class);
        return suite;
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        loginAsAdmin();
    }

    /**
     * Tests that the document dates are always of the type java.util.Date, as hibernate returns
     * java.sql.Timestamp, which is not entirely compatible with java.util.Date. When the cache
     * storage is enabled, this problem isn't detected until the document is removed from the cache.
     */
    public void testDateClass()
    {
        createPage("Main", "TestDateClass",
            "{{velocity}}$xwiki.flushCache()\n$xwiki.getDocument('Main.WebHome').date.class{{/velocity}}", SYNTAX);
        waitForBodyContains("java.util.Date");
    }
}
