package com.rackspace.api.clients.veracode;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/27/11
 * Time: 8:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface VeracodeClient {

    public String uploadFile(File file, String buildVersion, String appName);

    public void prescanBuild(String buildId);

    public void shutdown();
}
