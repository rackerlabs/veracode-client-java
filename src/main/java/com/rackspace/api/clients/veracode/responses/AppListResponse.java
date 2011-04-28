package com.rackspace.api.clients.veracode.responses;

import com.rackspace.api.clients.veracode.VeracodeApiException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/27/11
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppListResponse extends AbstractXmlResponse {

    public static final String XPATH_EXPRESSION = "//application";

    private Map<String, String> applications;

    public AppListResponse(HttpResponse response) throws VeracodeApiException {
        super(response);

        applications = buildAppMap();
    }


    public String getAppId(String appName) {
        return applications.get(appName);
    }

    private Map<String, String> buildAppMap() {
        Map<String, String> applications = new HashMap<String, String>();

        NodeList applicationNodes = null;

        try {
            XPathExpression expression = xpath.compile(XPATH_EXPRESSION);

            applicationNodes = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        if (applicationNodes != null) {
            for (int i = 0; i < applicationNodes.getLength(); i++) {
                Node currentNode = applicationNodes.item(i);

                applications.put(currentNode.getAttributes().getNamedItem("app_name").getNodeValue(),
                        currentNode.getAttributes().getNamedItem("app_id").getNodeValue());
            }
        }

        return applications;
    }
}
