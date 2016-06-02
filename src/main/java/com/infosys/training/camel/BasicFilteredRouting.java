package com.infosys.training.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class BasicFilteredRouting {

	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		camelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:data/inbox?recursive=true").filter().xpath("//order[@customer='honda']", String.class)
						.to("jms:orders");

			}

		});

		camelContext.start();
		Thread.sleep(10000);
	}

}
