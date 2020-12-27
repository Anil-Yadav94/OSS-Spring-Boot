package oss.airtel.controller;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oss.airtel.entity.Builtin;
import oss.airtel.entity.BuiltinExpert;
import oss.airtel.entity.Expert;
import oss.airtel.service.OSSService;
import oss.airtel.util.CustomException;

@RestController
@RequestMapping("/hnv/oss")
public class OSSController {
	private static Logger log = LoggerFactory.getLogger(OSSController.class);
	@Autowired
	OSSService ossservice;

	
	@GetMapping("/builtin/expert/conclusion/dslid/{dslid}")
	@Cacheable(value="DslidConclusion", key="#dslid", unless = "#result==null")
	public BuiltinExpert getBEC_DSLID(@PathVariable("dslid") String dslid) throws IllegalAccessException
	{
		BuiltinExpert bec=new BuiltinExpert();
		
		HashMap<String, String> map = new HashMap<>();
        map.put("dslid", dslid);
        bec.setInputs(map);
        
        String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort><busyControl>No</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
        CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(dslid, xmlBuiltin);
        
        String xmlExpert = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:lineDiagnosisRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort></intf:lineDiagnosisRequest></soapenv:Body></soapenv:Envelope>";
        CompletableFuture<Expert> Futureexpert = this.ossservice.getExpertXMLResult(dslid, xmlExpert);
        
        CompletableFuture.allOf(Futurebuiltin, Futureexpert).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		bec.setBuiltin(builtin);
		
		Expert expert=null;
		try {
			expert = Futureexpert.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		bec.setExpert(expert);
		
		if(builtin.getErrorStatus()!=null || expert.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		return bec;
	}
	
	@GetMapping("/builtin/expert/conclusion/ip/port/dslid/{ip}/{slot}/{port}/{dslid}")
	@Cacheable(value="IPPortConclusion", key="#dslid", unless = "#result==null")
	public BuiltinExpert getBEC_IPPORT(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port, @PathVariable("dslid") String dslid)
	{
		BuiltinExpert bec=new BuiltinExpert();
		
		HashMap<String, String> map = new HashMap<>();
        map.put("ip", ip);
        map.put("slot", slot);
        map.put("port", port);
        map.put("dslid", dslid);
        bec.setInputs(map);
        
		String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header extVersion=\"?\" extAuthor=\"?\" tmf854Version=\"?\"><tmf:domain>?</tmf:domain><tmf:activityName>?</tmf:activityName><tmf:msgName>?</tmf:msgName><tmf:msgType>?</tmf:msgType><tmf:payloadVersion>?</tmf:payloadVersion><!--Optional:--><tmf:senderURI>?</tmf:senderURI><!--Optional:--><tmf:destinationURI>?</tmf:destinationURI><!--Optional:--><tmf:replyToURI>?</tmf:replyToURI><!--Optional:--><tmf:originatorURI>?</tmf:originatorURI><!--Optional:--><tmf:failureReplytoURI>?</tmf:failureReplytoURI><!--Optional:--><tmf:operationStatus>?</tmf:operationStatus><!--Optional:--><tmf:correlationId>?</tmf:correlationId><!--Optional:--><tmf:security>?</tmf:security><!--Optional:--><tmf:securityType>?</tmf:securityType><!--Optional:--><tmf:priority>?</tmf:priority><!--Optional:--><tmf:msgSpecificProperties><!--1 or more repetitions:--><tmf:property><tmf:propName>?</tmf:propName><tmf:propValue>?</tmf:propValue></tmf:property></tmf:msgSpecificProperties><tmf:communicationPattern>?</tmf:communicationPattern><tmf:communicationStyle>?</tmf:communicationStyle><!--Optional:--><tmf:requestedBatchSize>?</tmf:requestedBatchSize><!--Optional:--><tmf:batchSequenceNumber>?</tmf:batchSequenceNumber><!--Optional:--><tmf:batchSequenceEndOfReply>?</tmf:batchSequenceEndOfReply><!--Optional:--><tmf:fileLocationURI>?</tmf:fileLocationURI><!--Optional:--><tmf:compressionType>?</tmf:compressionType><!--Optional:--><tmf:packingType>?</tmf:packingType><tmf:timestamp>?</tmf:timestamp><!--Optional:--><tmf:vendorExtensions extAuthor=\"?\" version=\"?\" tmf854Version=\"?\"><!--You may enter ANY elements at this point--></tmf:vendorExtensions></tmf:MTOSI_Header></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><meNm>"+ip+"</meNm><!--Optional:--><ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm></linePort><busyControl>Forced</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(dslid, xmlBuiltin);
        
        String xmlExpert="<?xml version=\"1.0\"?><soapenv:Envelope xmlns:intf=\"http://www.huawei.com/n2510/intf\" xmlns:tmf=\"tmf854.v1\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><tmf:MTOSI_Header tmf854Version=\"?\" extAuthor=\"?\" extVersion=\"?\"> <tmf:domain>iManager N2510 Line Assurance System</tmf:domain> <tmf:activityName>lineDiagnosis</tmf:activityName> <tmf:msgName>lineDiagnosisAction</tmf:msgName> <tmf:msgType>REQUEST</tmf:msgType> <tmf:senderURI>/lineAssurance/OSS</tmf:senderURI> <tmf:destinationURI>/LineAssuranceService/lineDiagnosisAction</tmf:destinationURI> <tmf:communicationPattern>SimpleResponse</tmf:communicationPattern> <tmf:communicationStyle>MSG</tmf:communicationStyle> </tmf:MTOSI_Header> </soapenv:Header><soapenv:Body><intf:lineDiagnosisRequest><linePort> <CustomerID/> <mdNm/> <meNm>"+ip+"</meNm> <ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm> </linePort> <lineID/> </intf:lineDiagnosisRequest> </soapenv:Body> </soapenv:Envelope>";
		CompletableFuture<Expert> Futureexpert = this.ossservice.getExpertXMLResult(dslid, xmlExpert);
        
        CompletableFuture.allOf(Futurebuiltin, Futureexpert).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		bec.setBuiltin(builtin);
		
		Expert expert=null;
		try {
			expert = Futureexpert.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		bec.setExpert(expert);
		
		if(builtin.getErrorStatus()!=null || expert.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		return bec;

	}

	@GetMapping("/requestCheck/{ip}/{slot}/{port}/{dslid}")
	public void getResync(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port, @PathVariable("dslid") String dslid)
	{
		System.out.println(ip+"/"+slot+"/"+port+"/"+dslid);
	}

	
}
