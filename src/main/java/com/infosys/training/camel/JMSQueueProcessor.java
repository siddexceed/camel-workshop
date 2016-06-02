package com.infosys.training.camel;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JMSQueueProcessor implements Processor {

	public void process(Exchange exhange) throws Exception {

		System.out.println("Processing the message Here" + exhange.getIn().getBody());

		Message message = exhange.getIn();
		String messageBody = message.getBody(String.class);

		System.out.println("Message Content" + messageBody);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append(messageBody);
		ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		Document doc = builder.parse(input);

		XPath xPath = XPathFactory.newInstance().newXPath();

		NodeList nodeList = (NodeList) xPath.compile("/order").evaluate(doc, XPathConstants.NODESET);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);

			if (nNode.getAttributes().getNamedItem("customer").getTextContent().equalsIgnoreCase("infosys")) {
				message.setHeader("priority", 1);
			}
		}

		exhange.setOut(message);

	}

}
