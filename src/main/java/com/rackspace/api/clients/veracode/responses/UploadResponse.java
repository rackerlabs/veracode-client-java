package com.rackspace.api.clients.veracode.responses;

import com.rackspace.api.clients.veracode.VeracodeApiException;
import org.apache.http.HttpResponse;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.Formatter;


/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/27/11
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadResponse extends AbstractXmlResponse {
    public static final String XPATH_EXPRESSION = "//application/build[@version='%d']/@build_id";

    private XPathExpression expression;

    public UploadResponse(HttpResponse response) throws VeracodeApiException {
        super(response);
    }

    public String getBuildId(int buildVersion) {
        Formatter formatter = new Formatter();
        String buildId = null;

        try {
            buildId = (String) xpath.evaluate(formatter.format(XPATH_EXPRESSION, buildVersion).toString(), doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        return buildId;
    }
}
