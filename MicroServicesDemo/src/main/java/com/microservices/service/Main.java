package com.microservices.service;

import com.microservices.app.ApplicationServer;
import com.microservices.app.PricingServer;
import com.microservices.app.ProductServer;
import com.microservices.app.RegistrationServer;

/*
 * Main entry point of execution.This functions starts the corresponding Spring boot server based on the the input arguments
 * 
 */
public class Main {

	public static void main(String[] args) {

		String serverName = "NO-VALUE";

		switch (args.length) {
		case 2:
			System.setProperty("server.port", args[1]);
		case 1:
			serverName = args[0].toLowerCase();
			break;
		default:
			return;
		}

		if (serverName.equals("registration")) {
			RegistrationServer.main(args);
		} else if (serverName.equals("product")) {
			ProductServer.main(args);
		} else if (serverName.equals("pricing")) {
			PricingServer.main(args);
		}else if (serverName.equals("application")) {
			ApplicationServer.main(args);
		} else {
			System.out.println("Unknown server type: " + serverName);
		}
	}
}
