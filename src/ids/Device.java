package ids;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;

public class Device
{
	//TODO class to be used to fill JTables on startup
	private PcapIf me;
	private String deviceName;
	private String macAddress;
	//private List<PcapSockAddr> ipAddress;
	private String ipAddress="";

	
	public Device(PcapIf dev)
	{
		me=dev;
		deviceName=dev.getName();
		List<PcapAddr> add= me.getAddresses();
		int ipAddCount=0;
		for(PcapAddr address :add)
		{
			if(ipAddCount!=0)
			{
				ipAddress+=", "+address.getAddr().toString();
			}
			else
			{
				ipAddress+=address.getAddr().toString();
			}
			ipAddCount++;
			//ipAddress.add(address.getAddr());
		}
		try {
			macAddress=Arrays.toString(me.getHardwareAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Device()
	 {
		
	 }
	
	public Device(String name,byte[] mac,String ip)
	{
		this.setDeviceName(name);
		this.setMacAddress(mac);
		this.setIpAddress(ip);
	 }
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress=ipAddress;	
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(byte[] macAddress) {
		this.macAddress = Arrays.toString(macAddress);
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	


}
