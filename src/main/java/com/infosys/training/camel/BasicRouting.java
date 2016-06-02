package com.infosys.training.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class BasicRouting {

	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();

		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:data/inbox?recursive=true").to("file:data/outbox");

			}

		});

		camelContext.start();
		Thread.sleep(10000);
	}

}
