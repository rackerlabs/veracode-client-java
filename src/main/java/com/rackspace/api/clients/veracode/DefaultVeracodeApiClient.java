package com.rackspace.api.clients.veracode;

import com.rackspace.api.clients.veracode.responses.AppListResponse;
import com.rackspace.api.clients.veracode.responses.UploadResponse;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/26/11
 * Time: 8:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultVeracodeApiClient implements VeracodeApiClient {

    public static final String UPLOAD = "uploadbuild.do";
    public static final String PRESCAN = "uploadprescan.do";
    public static final String APP_LIST = "getappbuilds.do";

    private URI baseUri;

    private HttpClient client;
    private PrintStream logger;

    public DefaultVeracodeApiClient(String baseUri, String username, String password, PrintStream logger) {

        try {
            this.baseUri = new URI(baseUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Veracode Base URI was not correctly entered.", e);
        }


        this.logger = logger;
        this.client = initiateClient(new UsernamePasswordCredentials(username, password));

    }

    public String scanArtifacts(List<File> artifacts, int buildVersion, String appName, String platform) throws VeracodeApiException {
        String appId = getAppId(appName);

        String buildId = null;

        for (File artifact : artifacts) {
            buildId = uploadFile(artifact, buildVersion, appId, platform);
        }

        if (buildId != null) {
            prescanBuild(buildId);
        }

        return buildId;
    }

    private String uploadFile(File file, int buildVersion, String appId, String platfrom) throws VeracodeApiException {
        HttpPost post = new HttpPost(baseUri.resolve(UPLOAD));

        MultipartEntity entity = new MultipartEntity();

        try {
            entity.addPart(new FormBodyPart("app_id", new StringBody(appId)));
            entity.addPart(new FormBodyPart("version", new StringBody(String.valueOf(buildVersion))));
            entity.addPart(new FormBodyPart("platform", new StringBody(platfrom)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("The Request could not be made due to an encoding issue", e);

        }

        entity.addPart("file", new FileBody(file, "text/plain"));


        post.setEntity(entity);

        logger.println("Executing Request: " + post.getRequestLine());

        UploadResponse uploadResponse = null;

        try {
            uploadResponse = new UploadResponse(client.execute(post));
        } catch (IOException e) {
            throw new VeracodeApiException("The call to Veracode failed.", e);
        }

        return uploadResponse.getBuildId(buildVersion);
    }

    private void prescanBuild(String buildId) {
        HttpGet get = new HttpGet(baseUri.resolve(PRESCAN + "?build_id=" + buildId));

        logger.println("Executing Request: " + get.getRequestLine());

        try {
            HttpResponse response = client.execute(get);
        } catch (IOException e) {
            throw new RuntimeException("The call to Veracode failed.", e);
        }
    }

    public void shutdown() {
        client.getConnectionManager().shutdown();
    }

    public String getAppId(String appName) throws VeracodeApiException {
        HttpGet get = new HttpGet(baseUri.resolve(APP_LIST));

        logger.println("Executing Request: " + get.getRequestLine());


        AppListResponse applist = null;

        try {
            applist = new AppListResponse(client.execute(get));
        } catch (IOException e) {
            throw new VeracodeApiException("The call to Veracode failed.", e);
        }

        return applist.getAppId(appName);
    }


    //sets up the client to auth against the veracode api.
    private HttpClient initiateClient(UsernamePasswordCredentials credentials) {
        DefaultHttpClient client = new DefaultHttpClient();

        AuthScope scope = new AuthScope(baseUri.getHost(), baseUri.getPort());

        client.getCredentialsProvider().setCredentials(scope, credentials);

        return client;
    }


}
