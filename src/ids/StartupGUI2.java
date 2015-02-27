package ids;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class StartupGUI2 extends JFrame
{

	private JTable JTableChosenDevices;
	private JTable JTableAvailableDevices;
	private SimpleTableModel simpleTableModel;
	private JPanel myTablePanel;

	public StartupGUI2(StartupAgent myStartupAgent) throws IOException {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartupGUI2.class.getResource("/images/shield-icon.png")));
		getContentPane().setFont(new Font("Mongolian Baiti", Font.PLAIN, 11));
		setTitle("MA-IDS");
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);


		JButton JButtonAddNode = new JButton("");
		JButtonAddNode.setIcon(new ImageIcon(StartupGUI2.class.getResource("/images/add1.png")));
		JButtonAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		JTableChosenDevices = new JTable();
		JTableChosenDevices.setBackground(new Color(255, 255, 240));
		JTableChosenDevices.setBorder(new TitledBorder(null, "IDS Running On:", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		JTableChosenDevices.setFont(new Font("MV Boli", Font.PLAIN, 15));
		JTableChosenDevices.setBounds(20, 72, 220, 140);
		getContentPane().add(JTableChosenDevices);
		JButtonAddNode.setBounds(250, 84, 50, 50);
		getContentPane().add(JButtonAddNode);

		JButton JButtonRemoveNode = new JButton("");
		JButtonRemoveNode.setIcon(new ImageIcon(StartupGUI2.class.getResource("/images/remove.png")));
		JButtonRemoveNode.setBounds(250, 164, 50, 50);
		getContentPane().add(JButtonRemoveNode);

		JToggleButton JToggleStartStop = new JToggleButton("");
		JToggleStartStop.setSelectedIcon(new ImageIcon(StartupGUI2.class.getResource("/images/start2.png")));
		JToggleStartStop.setIcon(new ImageIcon(StartupGUI2.class.getResource("/images/Stop1.png")));
		JToggleStartStop.setBounds(10, 11, 50, 50);
		getContentPane().add(JToggleStartStop);

		JList JListAlerts = new JList();
		JListAlerts.setFont(new Font("MV Boli", Font.PLAIN, 15));
		JListAlerts.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Alerts:", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		JListAlerts.setBackground(new Color(255, 255, 240));
		JListAlerts.setBounds(10, 225, 220, 140);
		getContentPane().add(JListAlerts);

		JButton JButtonViewLogFile = new JButton("View Log File");
		JButtonViewLogFile.setFont(new Font("MV Boli", Font.PLAIN, 15));
		JButtonViewLogFile.setBounds(322, 240, 220, 50);
		getContentPane().add(JButtonViewLogFile);
		//JTable
		ArrayList <Device> deviceArray=new ArrayList<Device>();
		  List<PcapIf> listOfDevices= new ArrayList<PcapIf>();
		  
		  StringBuilder errorMessage= new StringBuilder();

			//Find all network interfaces
			int resultFindDevices= Pcap.findAllDevs(listOfDevices, errorMessage);
			if(resultFindDevices==Pcap.NOT_OK||listOfDevices.isEmpty())
			{
				System.err.println("Could not find devices, error: "+errorMessage);
				return;
			}
			int lastIndexOfList= listOfDevices.size();
			int i=1;
			
		  while(i!=lastIndexOfList)
		  { 
			  deviceArray.add(new Device(listOfDevices.get(i).getName(),listOfDevices.get(i).getHardwareAddress(),"345"));
			  //System.out.println("myArray:"+deviceArray.get(i).getDeviceName());
		
			  System.out.println(i+":"+listOfDevices.get(i).getName()+","+listOfDevices.get(i).getHardwareAddress()+","+listOfDevices.get(i).getAddresses());
			  i++;
		  }
		  System.out.println("out");

	    simpleTableModel = new SimpleTableModel(deviceArray);
	    JTableAvailableDevices = new JTable(simpleTableModel);
	    //JTableAvailableDevices.setBorder(new TitledBorder(null, "Available Devices:", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		JTableAvailableDevices.setBackground(new Color(255, 255, 240));
	    myTablePanel=new JPanel(new BorderLayout());
	    myTablePanel.setSize(new Dimension(300, 140));
		myTablePanel.setBounds(310,80,300,140);
	    JScrollPane scrollPane = new JScrollPane(JTableAvailableDevices);
	    myTablePanel.add(scrollPane);
	    add(myTablePanel);
	    //getContentPane().add(scrollPane);
		//getContentPane().add(JTableAvailableDevices);
	  }

	  class SimpleTableModel extends AbstractTableModel {
	    public String[] colNames = {"Device Name","IP ADDRESS","MAC ADDRESS"};

	    public Class[] colTypes = { String.class, Byte.class, String.class };

	   ArrayList<Device> devices;

	    public SimpleTableModel(ArrayList<Device> deviceArray) {
	      super();
	      devices = deviceArray;
	    }
	    public int getColumnCount() {
	      return colNames.length;
	    }
	    public int getRowCount() {
	      return devices.size();
	    }
	    public void setValueAt(Object value, int row, int col) {
	      Device myDevice = (devices.get(row));

	      switch (col) {
	      case 0:
	        myDevice.setDeviceName((String)value);
	        break;
	      case 1:
	        myDevice.setMacAddress((byte[])value);
	        break;
	      case 2:
	        myDevice.setIpAddress((String)value);
	        break;
	     
	      }
	    }

	    public String getColumnName(int col) {
	      return colNames[col];
	    }

	    public Class getColumnClass(int col) {
	      return colTypes[col];
	    }
	    public Object getValueAt(int row, int col) {
	      Device macData = (Device) (devices.get(row));

	      switch (col) {
	      case 0:
	        return macData.getDeviceName();
	      case 1:
	        return macData.getMacAddress();
	      case 2:
	        return macData.getIpAddress();
	     
	      }

	      return new String();
	    }
	  }

	/*	public static void main(String[] args) throws IOException {
	
		StartupGUI2 sgui= new StartupGUI2(new StartupAgent());
		sgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sgui.setVisible(true);
		sgui.setSize(800,500);
	    sgui.validate();
	}*/
	//public void displayStartupGUI() {
		//pack();
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//int centerX = (int)screenSize.getWidth() / 2;
		//int centerY = (int)screenSize.getHeight() / 2;
		//setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		//setSize(600, 450);
		//super.setVisible(true);
	}

