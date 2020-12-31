package oss.airtel.service.copper;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import oss.airtel.Exceptions.CustomerException;
import oss.airtel.Exceptions.NAFaultStringException;
import oss.airtel.Exceptions.NAServerException;
import oss.airtel.component.NAconnect;
import oss.airtel.dao.copper.ResyncNAdao;
import oss.airtel.entity.copper.Builtin;
import oss.airtel.entity.copper.Expert;
import oss.airtel.entity.copper.Selt;

@Service
public class OSSService {
	private static Logger log = LoggerFactory.getLogger(OSSService.class);
	@Autowired
	ResyncNAdao resyncNAdao;
	
//	@Autowired
//	NAconnect nAconnect;
	
	@Async("asyncExecutor")
	@Cacheable(value="BuiltinCache", key="#xmlBuiltin", sync = true)//, unless = "#result==null"
	public CompletableFuture<Builtin> getBuilinXMLResult(String dslid, String xmlBuiltin)
	{
		log.info("getBuilinXMLResult Method Start...");
		Builtin builtin=new Builtin();

		String resyncCount="NA"; 
		if(dslid!=null)
		{
			resyncCount=getResync(dslid);
		}
		builtin.setResynchronizationTimes(resyncCount);
		
		//Builtin Start
		String output=NAconnect.runXMLHttps(xmlBuiltin);
		log.info("getBuilinXMLResult: "+output);
		
		if(output.contains("ErrorStatus: "))
	    {
			if(output.contains("Server returned HTTP response code: 500"))
	    	{
	    		throw new CustomerException(output.replace("ErrorStatus: ", "").replace(" for URL: https://10.232.161.137:10500/", ""));
	    	}
	    	else {
	    		throw new NAServerException(output.replace("ErrorStatus: ", ""));
			}
	    }
	    else {
	    	Document doc;
			Element element;
		    try 
		    {
		    	doc=parseXML(output);
		    	String fault="XYZ";
			    element = (Element) doc.getElementsByTagName("faultstring").item(0);
			    if(element!=null) {
			    	fault=element.getTextContent();
			    	builtin.setFaultstring(element.getTextContent());
			    }
			    //FaultString Handler
			    if (fault.contains("The entered parameter is invalid.") || fault.contains("The customer ID does not exist.") || fault.contains("The collected data is insufficient.") || fault.contains("Failed to collect data.")) {
					throw new NAFaultStringException(fault);
				}
			    /////
			    element = (Element) doc.getElementsByTagName("testConclusion").item(0);
			    if(element!=null) {
			    	builtin.setTestConclusion(element.getTextContent());
			    }
			    element = (Element) doc.getElementsByTagName("linelength").item(0);
			    if(element!=null) {
			    	builtin.setLinelength(element.getTextContent());
			    }
			    element = (Element) doc.getElementsByTagName("cpeType").item(0);
			    if(element!=null) {
			    	builtin.setCpeType(element.getTextContent());
			    }
			    
			} catch (Exception e) {
				log.info("Builtin Result Parsing error: "+e.getMessage());
			}
	    }
		return CompletableFuture.completedFuture(builtin);
	}
	
	@Async("asyncExecutor")
	@Cacheable(value="ExpertCache", key="#xmlExpert", sync = true)//, unless = "#result==null"
	public CompletableFuture<Expert> getExpertXMLResult(String xmlExpert)
	{
		log.info("getExpertXMLResult Method Start...");
		Expert expert=new Expert();
	    String output=NAconnect.runXMLHttps(xmlExpert);
	    log.info("getExpertXMLResult: "+output);
	    
	    if(output.contains("ErrorStatus: "))
	    {
	    	if(output.contains("Server returned HTTP response code: 500"))
	    	{
	    		throw new CustomerException(output.replace("ErrorStatus: ", "").replace(" for URL: https://10.232.161.137:10500/", ""));
	    	}
	    	else {
	    		throw new NAServerException(output.replace("ErrorStatus: ", ""));
			}
	    }
	    else {
	    	Document doc;
			Element element;
		    try 
		    {
		    	doc=parseXML(output);
		    	element = (Element) doc.getElementsByTagName("actualRateDS").item(0);
			    if(element!=null) {
				    expert.setActualRateDS(element.getTextContent());
			    }
			    element = (Element) doc.getElementsByTagName("actualNoiseMarginDS").item(0);
			    if(element!=null) {
			    	expert.setActualNoiseMarginDS(element.getTextContent());
			    }
			    element = (Element) doc.getElementsByTagName("resynchronizationTimes").item(0);
			    if(element!=null) {
			    	expert.setResynchronizationTimesLive(element.getTextContent());
			    }
			    element = (Element) doc.getElementsByTagName("cause").item(0);
			    if(element!=null) {
			    	expert.setCause(element.getTextContent());
			    }
			    element = (Element) doc.getElementsByTagName("suggestion").item(0);
			    if(element!=null) {
			    	expert.setSuggestion(element.getTextContent());
			    }
			    String fault="XYZ";
			    element = (Element) doc.getElementsByTagName("faultstring").item(0);
			    if(element!=null) {
			    	fault=element.getTextContent();
			    	expert.setFaultstring(element.getTextContent());
			    }
			  //FaultString Handler
			    if (fault.contains("The entered parameter is invalid.") || fault.contains("The customer ID does not exist.") || fault.contains("The collected data is insufficient.") || fault.contains("Failed to collect data.")) {
					throw new NAFaultStringException(fault);
				}
			    /////
			    String showTimes=null;
			    String mtbe=null;
			    
			    element = (Element) doc.getElementsByTagName("showTimes").item(0);
			    if(element!=null) {
			    	showTimes = element.getTextContent();
			    }
			    element = (Element) doc.getElementsByTagName("mtbe").item(0);
			    if(element!=null) {
			    	mtbe = element.getTextContent();
			    }
			    
			    String codeViolation="";
				double code_violation = 0.0;
				DecimalFormat df = new DecimalFormat("#");
				if(!isDouble(mtbe))
				{
					codeViolation="NA";									
				}
				else 
				{
					try {
						code_violation = Double.parseDouble(df.format(Integer.parseInt(showTimes)/Double.parseDouble(mtbe)));
						codeViolation = String.valueOf(code_violation);
					} catch (Exception e) {
						log.info(e.getMessage());
						codeViolation="NA";
					}
				}
				expert.setCodeViolation(codeViolation);
				 
			} catch (Exception e) {
				log.info("Expert Result Parsing error: "+e.getMessage());
			}
		}
	    return CompletableFuture.completedFuture(expert);
	}
	
	@Async("asyncExecutor")
	@Cacheable(value="SeltCache", key="#xmlSelt", sync = true)//, unless = "#result==null"
	public CompletableFuture<Selt> getSeltXMLResult(String xmlSelt)
	{
		log.info("getSeltXMLResult Method Start...");
		Selt selt=new Selt();
		String output=NAconnect.runXMLHttps(xmlSelt);
		log.info("getSeltXMLResult: "+output);
		
		if(output.contains("ErrorStatus: "))
	    {
	    	if(output.contains("Server returned HTTP response code: 500"))
	    	{
	    		throw new CustomerException(output.replace("ErrorStatus: ", "").replace(" for URL: https://10.232.161.137:10500/", ""));
	    	}
	    	else {
	    		throw new NAServerException(output.replace("ErrorStatus: ", ""));
			}
	    }
	    else {
	    	Document doc;
			Element element;
		    try 
		    {
		    	doc=parseXML(output);
		    	String fault="XYZ";
			    element = (Element) doc.getElementsByTagName("faultstring").item(0);
			    if(element!=null) {
			    	fault=element.getTextContent();
			    	selt.setFaultstring(element.getTextContent());
			    }
			    //FaultString Handler
			    if (fault.contains("The entered parameter is invalid.") || fault.contains("The customer ID does not exist.") || fault.contains("The collected data is insufficient.") || fault.contains("Failed to collect data.")) {
					throw new NAFaultStringException(fault);
				}
			    /////
			    element = (Element) doc.getElementsByTagName("seltConclusion").item(0);
			    if(element!=null) {
			    	selt.setSeltConclusion(element.getTextContent());
			    }
			    element = (Element) doc.getElementsByTagName("length").item(0);
			    if(element!=null) {
			    	selt.setLinelength(element.getTextContent());
			    }
			    
			} catch (Exception e) {
				log.info("Selt Result Parsing error: "+e.getMessage());
			}
		    
	    }
		return CompletableFuture.completedFuture(selt);
	}
	
	
	public Document parseXML(String output) {
		try 
		{
			StringReader in=new StringReader(output);
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(new InputSource(in));
		    doc.getDocumentElement().normalize();
		    return doc;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public boolean isDouble(String str) 
	{
        try 
        {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) 
        {
            return false;
        }
    }
	public String getResync(String dslid) {
		String resyncCount = this.resyncNAdao.getResyncCount(dslid);
		return resyncCount;
	}
}
