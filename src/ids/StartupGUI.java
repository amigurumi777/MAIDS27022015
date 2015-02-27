package ids;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.UIManager;
import javax.swing.JToggleButton;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.util.Vector;

public class StartupGUI extends JFrame
{

	private JTable JTableChosenDevices;
	private JTable JTableAvailableDevices;
	private JScrollPane scrollpaneAvailableDevices;
	public StartupGUI(StartupAgent myStartupAgent) {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartupGUI.class.getResource("/images/shield-icon.png")));
		getContentPane().setFont(new Font("Mongolian Baiti", Font.PLAIN, 11));
		setTitle("MA-IDS");
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/*JList JListAvailableDevices = new JList();
		JListAvailableDevices.setFont(new Font("MV Boli", Font.PLAIN, 15));
		JListAvailableDevices.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Available Network Interfaces:", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		JListAvailableDevices.setBackground(new Color(255, 255, 240));
		JListAvailableDevices.setBounds(322, 74, 220, 140);
		JListAvailableDevices.setVisibleRowCount(2);
		JListAvailableDevices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		getContentPane().add(JListAvailableDevices);*/

		JTableAvailableDevices = new JTable();
		JTableAvailableDevices.setBorder(new TitledBorder(null, "Available Devices:", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		JTableAvailableDevices.setBackground(new Color(255, 255, 240));
		JTableAvailableDevices.setBounds(310, 73, 220, 140);
		//scrollpaneAvailableDevices= new JScrollPane(JTableAvailableDevices);
		//getContentPane().add(scrollpaneAvailableDevices);
		getContentPane().add(JTableAvailableDevices);
		//TODO FIll in JTable
		class AvailableDevicesTableModel extends AbstractTableModel
		{
			public static final int DEVICENAME_INDEX=0;
			public static final int MACADDRESS_INDEX=1;
			public static final int IPADDRESS_INDEX=2;
			String errormsg= "";
			private String [] ColumnNames= {"Device Name","IP ADDRESS","MAC ADDRESS"};
			protected Vector data;
			public int getColumnCount() {
				return ColumnNames.length;
			}
			public String getColumnName(int col)
			{
				return ColumnNames[col];
			}
			public Class getColumnClass(int col)
			{
				return getValueAt(0,col).getClass();
			}

			public int getRowCount() {
				return 0;
			}

			public Object getValueAt(int row, int col) {
				Device device= (Device)data.get(row);
				switch(col)
				{
					case DEVICENAME_INDEX:
						return device.getDeviceName();
					case MACADDRESS_INDEX:
						return device.getMacAddress();
					case IPADDRESS_INDEX:
						return device.getIpAddress();
					default:
						errormsg="An error ocurred";
				}
				return null;
			}
			public void setVaueAt(Object val, int row, int col)
			{
				Device device=(Device)data.get(row);
				switch(col)
				{
					case DEVICENAME_INDEX:
						device.setDeviceName((String) val);
					case MACADDRESS_INDEX:
						device.setMacAddress((byte[]) val);
					case IPADDRESS_INDEX:
						device.setIpAddress((String) val);
					default:
						errormsg="An error occured";
				}
				fireTableCellUpdated(row,col);
			}
			public boolean hasEmptyRow()
			{
				Device device=(Device)data.get(data.size()-1);
				if(data.size()==0)
					return false;
				if(device.getDeviceName().trim().equals("")&&device.getMacAddress().trim().equals("")&&device.getIpAddress().trim().equals(""))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			public void appendNewEmptyRow()
			{
				data.add(new Device());
				fireTableRowsInserted(data.size()-1,data.size()-1);
				
			}

		}

		JButton JButtonAddNode = new JButton("");
		JButtonAddNode.setIcon(new ImageIcon(StartupGUI.class.getResource("/images/add1.png")));
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
		JButtonRemoveNode.setIcon(new ImageIcon(StartupGUI.class.getResource("/images/remove.png")));
		JButtonRemoveNode.setBounds(250, 164, 50, 50);
		getContentPane().add(JButtonRemoveNode);

		JToggleButton JToggleStartStop = new JToggleButton("");
		JToggleStartStop.setSelectedIcon(new ImageIcon(StartupGUI.class.getResource("/images/start2.png")));
		JToggleStartStop.setIcon(new ImageIcon(StartupGUI.class.getResource("/images/Stop1.png")));
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

	}

	/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartupGUI sgui= new StartupGUI();
		sgui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sgui.setVisible(true);
		sgui.setSize(800, 500);
	}*/
	public void displayStartupGUI() {
		pack();
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//int centerX = (int)screenSize.getWidth() / 2;
		//int centerY = (int)screenSize.getHeight() / 2;
		//setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		setSize(600, 450);
		super.setVisible(true);
	}
}
