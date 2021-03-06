/*
 * Copyright (c) 2011 Rackspace Hosting
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.rackspace.api.clients.veracode;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 6/23/11
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultVeracodeApiClientTest {
    DefaultVeracodeApiClient client;

    @Before
    public void setUp() throws Exception {
        client = new DefaultVeracodeApiClient("https://analysiscenter.veracode.com/api/", "YOUR_APP_HERE", "YOUR_PASSWORD_HERE", System.out);
    }

    @Test
    @Ignore
    public void testGetAppId() throws Exception {
        String appId = client.getAppId("YOUR_APP_NAME_HERE");

        System.out.println(appId);
    }
}
