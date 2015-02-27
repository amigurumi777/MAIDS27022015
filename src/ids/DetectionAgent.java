package ids;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class DetectionAgent extends Agent
{
	protected void setup()
	{
		System.out.println("Hello! My name is: "+getLocalName());
		addBehaviour(new SniffPackets());
	}
	protected void takeDown()
	{
		doDelete();
	}
	private class SniffPackets extends SimpleBehaviour
	{

		@Override
		public void action()
		{
			// TODO Find a way to obtain list of devices from GUI
			//TODO Move this piece of code to startupAgent
			List<PcapIf> listOfDevices= new ArrayList<PcapIf>(); //Set Array List to store devices
			StringBuilder errorMessage= new StringBuilder();

			//Find all network interfaces
			int resultFindDevices= Pcap.findAllDevs(listOfDevices, errorMessage);
			if(resultFindDevices==Pcap.NOT_OK||listOfDevices.isEmpty())
			{
				System.err.println("Could not find devices, error: "+errorMessage);
				return;
			}

			//print available devices
			for (PcapIf nwInterface :listOfDevices)
			{
				try
				{
					byte [] mac=nwInterface.getHardwareAddress();
					String desc;
					if(nwInterface.getDescription()==null)
					{
						desc="No description";
					}
					else
					{
						desc=nwInterface.getDescription();
					}
					System.out.println(" Device Name: "+nwInterface.getDescription()+ " Description: "+desc+ " Mac Address: "+ Arrays.toString(mac));
					System.out.println("Addresses");
					List<PcapAddr> add= nwInterface.getAddresses();
					int c=0;
					String ipAdd="";
					for(PcapAddr address :add)
					{
						
						//c++;
						//System.out.println("CLASS: "+address.getClass());
						//System.out.println("Address: "+c+" "+address.get(0).getAddr());
						if(c!=0)
						{
							ipAdd+=", "+address.getAddr().toString();
							
						}
						else
						{
							ipAdd+=address.getAddr().toString();
						}
						c++;
						
					}
					System.out.println("IP ADDRESS: "+ipAdd);
				} 
				catch (IOException ex)
				{
					Logger.getLogger(DetectionAgent.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			//TODO: Merge with Vaynee's GUI to obtain chosen device
			PcapIf chosenDevice=listOfDevices.get(0); //for time being using first element in list          
			//open chosenDevice: Device Name, 65536(=64K),Not promisc,no time out(=0),errormsg
			Pcap openedChosenDevice=Pcap.openLive(chosenDevice.getName(), 65536, Pcap.MODE_NON_PROMISCUOUS, 0, errorMessage);
			if(openedChosenDevice==null)
			{
				System.err.println(errorMessage);
				return;
			}
			//Packet Handler--callback function-->will be run each time a packet is received
			PcapPacketHandler packetHandler = new PcapPacketHandler() 
			{  
				@Override
				public void nextPacket(PcapPacket pp, Object t)
				{
					System.out.printf("Received packet at %s caplen=%-4d len=%-4d \n",  
							new Date(pp.getCaptureHeader().timestampInMillis()),   
							pp.getCaptureHeader().caplen(),  // Length actually captured  
							pp.getCaptureHeader().wirelen() // Original length                              
							);
				}
			};  
			//use loop to capture packet
			openedChosenDevice.loop(Pcap.LOOP_INFINITE, packetHandler, null); //Pcap.LOOP_INFINITE
			openedChosenDevice.close();

		}

		@Override
		public boolean done() {
			doDelete();
			// TODO Auto-generated method stub
			return true;
		}

	}

}
