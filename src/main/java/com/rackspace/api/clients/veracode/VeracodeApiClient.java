package com.rackspace.api.clients.veracode;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/27/11
 * Time: 8:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface VeracodeApiClient {

    public String scanArtifacts(List<File> artifacts, int buildVersion, String appName, String platform) throws VeracodeApiException;

    public String getAppId(String appName) throws VeracodeApiException;

    public void shutdown();
}
