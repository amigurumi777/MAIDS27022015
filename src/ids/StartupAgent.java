package ids;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class StartupAgent extends Agent
{
	private StartupGUI maidsGui;
	private static int DetectionAgentCount;
	protected void setup()
	{
		DetectionAgentCount=0;
		retrieveStateFromDB();

		maidsGui= new StartupGUI(this);

		maidsGui.displayStartupGUI();
		createDetectionAgent();
		DFAgentDescription startupAgentDescription = new DFAgentDescription();
		startupAgentDescription.setName(getAID());
		ServiceDescription startupAgentServiceDesc = new ServiceDescription();
		startupAgentServiceDesc.setType("start-ids");
		startupAgentServiceDesc.setName(getName());
		startupAgentDescription.addServices(startupAgentServiceDesc);
		try {
			DFService.register(this, startupAgentDescription);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	protected void takeDown()
	{
		//TODO Save state in DB
	}
	protected void retrieveStateFromDB()
	{
		System.out.println("Hello! My name is: "+getLocalName());
		System.out.println("AID: "+getAID());
		System.out.println("getName: "+getName());
	}
	protected void createDetectionAgent()
	{
		DetectionAgentCount++;
		String agentName= "Sherlock"+DetectionAgentCount;
		ContainerController cc= this.getContainerController();
		try {
			AgentController ca=cc.createNewAgent(agentName, "ids.DetectionAgent", null);

			ca.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*public List<PcapIf>  findNetworkInterfaces()
{
	List<PcapIf> listOfDevices= new ArrayList<PcapIf>(); //Set Array List to store devices
	StringBuilder errorMessage= new StringBuilder();

	//Find all network interfaces
	int resultFindDevices= Pcap.findAllDevs(listOfDevices, errorMessage);



	return listOfDevices;
}*/
}
