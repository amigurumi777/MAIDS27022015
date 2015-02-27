package ids;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.ByteBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class LoggerAgent extends Agent{
	protected void setup()
	{
		System.out.println("Hello! My name is: "+getLocalName());
		addBehaviour(new LoggingPackets());
	}
	
	private class LoggingPackets extends SimpleBehaviour
	{

		@Override
		public void action() 
		{
			
			/*
			StringBuilder errbuf = new StringBuilder();  
			String fname = "tests/test-afs.pcap";  
			  
			Pcap pcap = Pcap.openOffline(fname, errbuf);  
			  
			String ofile = "tmp-capture-file.cap";  
			PcapDumper dumper = pcap.dumpOpen(ofile); // output file  
			  
			pcap.loop(10, dumper); // Special native dumper call to loop  
			                  
			File file = new File(ofile);  
			System.out.printf("%s file has %d bytes in it!\n", ofile, file.length());  
			                  
			dumper.close(); // Won't be able to delete without explicit close  
			    pcap.close();  
			*/
			  List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
			    StringBuilder errbuf = new StringBuilder();     // For any error msgs

			    /***************************************************************************
			     * First get a list of devices on this system
			     **************************************************************************/
			    int r = Pcap.findAllDevs(alldevs, errbuf);
			    if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			      System.err.printf("Can't read list of devices, error is %s\n", 
			        errbuf.toString());
			      return;
			    }
			    PcapIf device = alldevs.get(0); // We know we have atleast 1 device

			    /***************************************************************************
			     * Second we open up the selected device
			     **************************************************************************/
			    int snaplen = 64 * 1024;           // Capture all packets, no trucation
			    int flags = Pcap.MODE_NON_PROMISCUOUS; // capture all packets
			    int timeout = 10 * 1000;           // 10 seconds in millis
			    Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
			    if (pcap == null) {
			      System.err.printf("Error while opening device for capture: %s\n", 
			        errbuf.toString());
			      return;
			    }
					
			    /***************************************************************************
			     * Third we create a PcapDumper and associate it with the pcap capture
			     ***************************************************************************/
			    String ofile = "C:/Users/xxxxxx/Documents/tmp-capture-file.cap";
			    PcapDumper dumper = pcap.dumpOpen(ofile); // output file

			    /***************************************************************************
			     * Fouth we create a packet handler which receives packets and tells the 
			     * dumper to write those packets to its output file
			     **************************************************************************/
			    ByteBufferHandler<PcapDumper> dumpHandler = new  ByteBufferHandler<PcapDumper>() {

			      /*public void nextPacket(PcapDumper dumper, long seconds, int useconds,
			        int caplen, int len, ByteBuffer buffer) {

			        dumper.dump(seconds, useconds, caplen, len, buffer);
			       System.out.println("Len:"+len);
			      }
			*/
				
				public void nextPacket(PcapHeader hdr, ByteBuffer buffer, PcapDumper dumper) {
					dumper.dump(hdr, buffer);
					System.out.println("inside");
				       System.out.println("Header:"+hdr+"buffer:"+buffer);
					
				}
			    };

			    /***************************************************************************
			     * Fifth we enter the loop and tell it to capture 10 packets. We pass
			     * in the dumper created in step 3
			     **************************************************************************/
			   // pcap.loop(10, dumpHandler, dumper);
				pcap.loop(10, dumpHandler, dumper);	
			    File file = new File(ofile);
			    System.out.printf("%s file has %d bytes in it!\n", ofile, file.length());
					

			    /***************************************************************************
			     * Last thing to do is close the dumper and pcap handles
			     **************************************************************************/
			    dumper.close(); // Won't be able to delete without explicit close
			    pcap.close();
					
			  /* if (file.exists()) {
			      file.delete(); // Cleanup
			    }*/
			
		}

		@Override
		public boolean done() {
			
			return true;
		}
		
	}
	
}
