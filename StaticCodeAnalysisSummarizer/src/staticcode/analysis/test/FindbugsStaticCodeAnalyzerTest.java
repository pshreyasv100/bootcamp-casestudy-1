package staticcode.analysis.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import staticcode.analysis.FindbugsStaticCodeAnalyzer;
import staticcode.analysis.StaticCodeAnalyzer;

public class FindbugsStaticCodeAnalyzerTest {
	
	
	@Test
	public void testGetCommand() {
		String findbugsSourcePath = ".";
		String findbugsOutputPath = "../reports/findbugs_report.xml";
		Map<String, String> optionsMap = new HashMap<String, String>();
		optionsMap.put("outputFormat", "xml");
		StaticCodeAnalyzer instance =  new FindbugsStaticCodeAnalyzer(findbugsSourcePath, findbugsOutputPath, optionsMap);
		
		String[] expectedCommand = {"cmd" , "/c" , "findbugs" , "-textui" , "-output" , "../reports/findbugs_report.xml" , "xml" , "."};
		String[] actualCommand = instance.getCommand();
		assertArrayEquals(expectedCommand, actualCommand);
	}

	@Test
	public void testParseXMLToCSV() throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		
		String findbugsSourcePath = ".";
		String findbugsOutputPath = "../reports/findbugs_report.xml";
		Map<String, String> optionsMap = new HashMap<String, String>();
		optionsMap.put("outputFormat", "xml");
		StaticCodeAnalyzer instance =  new FindbugsStaticCodeAnalyzer(findbugsSourcePath, findbugsOutputPath, optionsMap);
		
		ProcessBuilder pb = new ProcessBuilder();
		Map<String, String> envMap = pb.environment();
		String path = envMap.get("Path");
		path += "../static-code-analyzers/pmd/bin;";
		path += "../static-code-analyzers/findbugs/bin;";
			
		pb.command(instance.getCommand());
		Process process = pb.start();
		process.waitFor();
		
		instance.parseXMLToCSV();
		
		boolean check = new File("../reports/", "findbugs_reports.csv").exists();
		
		//File f = new File("../reports/findbugs_reports.csv");
		assert(check);
		
		
	}

}
