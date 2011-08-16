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

import java.io.IOException;

import com.rackspace.api.clients.veracode.VeracodeApiException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/27/11
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractXmlResponse {

    protected Document doc;
    protected XPath xpath;

    public AbstractXmlResponse(HttpResponse response) throws VeracodeApiException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = domFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Xml Configuration Issue parsing response", e);
        }

        HttpEntity responseEntity = extractResponse(response);

        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                this.doc = builder.parse(responseEntity.getContent());
                EntityUtils.consume(responseEntity);
            } else {
                String errorMessage = EntityUtils.toString(responseEntity);
                int errorCode = response.getStatusLine().getStatusCode();

                throw new VeracodeApiException("Error Code: " + errorCode + " Error Message: " + errorMessage);
            }
        } catch (SAXException e) {
            throw new VeracodeApiException("Could not Parse Response.", e);
        } catch (IOException e) {
            throw new VeracodeApiException("Could not Parse Response.", e);
        }


        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    private HttpEntity extractResponse(HttpResponse response) throws VeracodeApiException {
        HttpEntity responseEntity = response.getEntity();

        if (response.getStatusLine() == null || responseEntity == null) {
            throw new VeracodeApiException("Response was null");
        }

        return responseEntity;
    }
}
