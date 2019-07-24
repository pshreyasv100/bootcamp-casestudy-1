package staticcode.analysis;

import java.io.IOException;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FindbugsStaticCodeAnalyzer extends StaticCodeAnalyzer {

	
	public static final String CSV_OUTPUT_PATH = "../reports/findbugs_report.csv";
	
	/**
	 * 
	 * @param sourceCodePath - findbugs expects project path
	 * @param resultsPath
	 * @param optionsMap
	 */
	public FindbugsStaticCodeAnalyzer(String sourceCodePath, String resultsPath, Map<String, String> optionsMap) {
		super(sourceCodePath, resultsPath, optionsMap);
	}


	@Override
	public String[] getCommand() {

		String[] command = { "cmd", "/c", "findbugs", "-textui", "-output", resultsPath, optionsMap.get("outputFormat"),
				sourceCodePath };
		return command;
	}

	@Override
	public void parseXMLToCSV() throws ParserConfigurationException, SAXException, IOException {

		try {
			
			System.setOut(new PrintStream(new FileOutputStream(CSV_OUTPUT_PATH)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("../reports/findbugs_report.xml");
		NodeList allBugs = doc.getElementsByTagName("BugInstance");
		System.out.println("Findbugs Results");
		System.out.println("Type," + "Category," + "Classname," + "Startline," + "Endline");
		
		for (int i = 0; i < allBugs.getLength(); i++) {
			Node bugInstance = allBugs.item(i);
			if (bugInstance.getNodeType() == Node.ELEMENT_NODE) {
				Element bug = (Element) bugInstance;
				System.out.print(bug.getAttribute("type") + ",");
				System.out.print(bug.getAttribute("category") + ",");
				NodeList bugInfo = bug.getChildNodes();

				for(int j=0; j < bugInfo.getLength(); j++) {
					
					if(bugInfo.item(j).getNodeName().equals("SourceLine")) {
						System.out.print(((Element) bugInfo.item(j)).getAttribute("classname") + ",");
						System.out.print(((Element) bugInfo.item(j)).getAttribute("start") + ",");
						System.out.print(((Element) bugInfo.item(j)).getAttribute("end") + ",");
						System.out.println();
					}
				}

			}
		}

	}

}