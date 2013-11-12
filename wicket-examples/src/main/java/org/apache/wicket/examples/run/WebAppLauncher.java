package org.apache.wicket.examples.run;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebAppLauncher
{
	public static void main(String[] args) throws Exception
	{
		int timeout = (int)Duration.ONE_HOUR.getMilliseconds();

		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(timeout);
		connector.setSoLingerTime(-1);
		connector.setPort(8080);
		server.addConnector(connector);

		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/test");
		bb.setWar("src/main/webapp");

		// Setup the test security realm, its name must match what's in the web.xml's 'realm-name'
// tag:
		// eto don't think I need these
// HashLoginService dummyLoginService = new HashLoginService("MySecurityRealm");
// dummyLoginService.setConfig("src/test/resources/jetty-users.properties");
// bb.getSecurityHandler().setLoginService( dummyLoginService );

		server.setHandler(bb);

		try
		{
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			int sleepTime = 1000;
			if (args[0] != null)
				sleepTime = Integer.parseInt(args[0]);
			while (true)
			{
				Thread.sleep(sleepTime);
				if (false)
					break;// just to get rid of compiler nag
			}

			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
			server.stop();
			server.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

}
