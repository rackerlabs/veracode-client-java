/*
 * Copyright (c) 2011 Rackspace Hosting
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    public static final String XPATH_EXPRESSION = "//application/build[@version='%s']/@build_id";

    private XPathExpression expression;

    public UploadResponse(HttpResponse response) throws VeracodeApiException {
        super(response);
    }

    public String getBuildId(String buildVersion) {
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
