package com.example.springsecurity;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		tcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

		return tcat;
	}

	private Connector httpToHttpsRedirectConnector() {
		Connector con = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		con.setScheme("http");
		con.setPort(8082);
		con.setSecure(false);
		con.setRedirectPort(8443);
		return con;
	}
}
