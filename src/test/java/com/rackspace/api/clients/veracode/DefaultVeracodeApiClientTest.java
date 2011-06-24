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
        client = new DefaultVeracodeApiClient("https://analysiscenter.veracode.com/api/", "monitoring-api", "YOUR_PASSWORD_HERE", System.out);
    }

    @Test
    @Ignore
    public void testGetAppId() throws Exception {
        String appId = client.getAppId("RADAR");

        System.out.println(appId);
    }
}
