package com.rackspace.api.clients.veracode;

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

/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/26/11
 * Time: 8:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class VeracodeClientImpl implements VeracodeClient {

    public static final String UPLOAD = "uploadbuild.do";
    public static final String PRESCAN = "uploadprescan.do";
    public static final String APP_LIST = "getappbuilds.do";

    private URI baseUri;
    private final String appName;

    private HttpClient client;
    private PrintStream logger;

    public VeracodeClientImpl(String baseUri, String username, String password, String appName, PrintStream logger) {

        try {
            this.baseUri = new URI(baseUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Veracode Base URI was not correctly entered.", e);
        }


        this.logger = logger;
        this.appName = appName;
        this.client = initiateClient(new UsernamePasswordCredentials(username, password));
    }

    public String uploadFile(File file, String buildVersion, String appName) {
        HttpPost post = new HttpPost(baseUri.resolve(UPLOAD));

        String appId = getAppId(appName);

        MultipartEntity entity = new MultipartEntity();

        try {
            entity.addPart(new FormBodyPart("app_id", new StringBody(appId)));
            entity.addPart(new FormBodyPart("version", new StringBody(buildVersion)));
            entity.addPart(new FormBodyPart("platform", new StringBody("Java")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("The Request could not be made due to an encoding issue", e);

        }

        entity.addPart("file", new FileBody(file, "text/plain"));


        post.setEntity(entity);

        logger.println("Executing Request: " + post.getRequestLine());

        try {
            HttpResponse response = client.execute(post);
        } catch (IOException e) {
            throw new RuntimeException("The call to Veracode failed.", e);
        }

        return null;
    }

    public void prescanBuild(String buildId) {
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

    private String getAppId(String appName) {
        return null;
    }


    //sets up the client to auth against the veracode api.
    private HttpClient initiateClient(UsernamePasswordCredentials credentials) {
        DefaultHttpClient client = new DefaultHttpClient();

        AuthScope scope = new AuthScope(baseUri.getHost(), baseUri.getPort());

        client.getCredentialsProvider().setCredentials(scope, credentials);

        return client;
    }
}
