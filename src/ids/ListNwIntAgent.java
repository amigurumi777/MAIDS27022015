package ids;

import jade.core.Agent;

import java.io.*;
import java.net.*;
import java.util.*;

import static java.lang.System.out;

public class ListNwIntAgent extends Agent{
	protected void setup()
	{
		Enumeration<NetworkInterface> nets;
		try {
			nets = NetworkInterface.getNetworkInterfaces();

			for (NetworkInterface netint : Collections.list(nets))
			{

				out.printf("Name: %s\n", netint.getName());
				Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
				for (InetAddress inetAddress : Collections.list(inetAddresses)) {
					out.printf("InetAddress: %s\n", inetAddress);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

