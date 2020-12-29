package oss.airtel.controller.copper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oss.airtel.entity.copper.Builtin;
import oss.airtel.entity.copper.Expert;
import oss.airtel.entity.copper.Selt;
import oss.airtel.service.copper.OSSService;
import oss.airtel.util.CustomException;

@RestController
@RequestMapping("/hnv/oss_support")
public class OSSController {
	private static Logger log = LoggerFactory.getLogger(OSSController.class);
	@Autowired
	OSSService ossservice;

	
	@GetMapping("/builtin/dslid/{dslid}")
	public ResponseEntity<Object> getBuiltin(@PathVariable("dslid") String dslid) {
		
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("dslid", dslid);
	    output.put("inputs", inputs);
		
		String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort><busyControl>No</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
        CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(dslid, xmlBuiltin);
        
        CompletableFuture.allOf(Futurebuiltin).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("builtin",builtin);
		if(builtin.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@GetMapping("/expert/dslid/{dslid}")
	public ResponseEntity<Object> getExpert(@PathVariable("dslid") String dslid) {
		
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("dslid", dslid);
	    output.put("inputs", inputs);
		
		String xmlExpert = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:lineDiagnosisRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort></intf:lineDiagnosisRequest></soapenv:Body></soapenv:Envelope>";
        CompletableFuture<Expert> Futureexpert = this.ossservice.getExpertXMLResult(xmlExpert);
        
        CompletableFuture.allOf(Futureexpert).join();
        
        Expert expert=null;
		try {
			expert = Futureexpert.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("expert",expert);
		if(expert.getErrorStatus()!=null)
		{
			log.info(expert.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(expert.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@GetMapping("/selt/dslid/{dslid}")
	public ResponseEntity<Object> getSelt(@PathVariable("dslid") String dslid) {
		
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("dslid", dslid);
	    output.put("inputs", inputs);
	    
	    String xmlSelt = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:lineTestRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort><testProfile>0.4mm/26AWG</testProfile><!--Optional:--><refTest>?</refTest><!--Optional:--><refDev><!--Optional:--><mdNm>?</mdNm><!--Optional:--><meNm>?</meNm></refDev></intf:lineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Selt> Futureselt = this.ossservice.getSeltXMLResult(xmlSelt);
        
        CompletableFuture.allOf(Futureselt).join();
        
        Selt selt=null;
		try {
			selt = Futureselt.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("selt",selt);
		if(selt.getErrorStatus()!=null)
		{
			log.info(selt.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(selt.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@GetMapping("/builtin-expert/dslid/{dslid}")
	public ResponseEntity<Object> getBuiltinExpert(@PathVariable("dslid") String dslid) throws IllegalAccessException
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("dslid", dslid);
	    output.put("inputs", inputs);
        
        String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort><busyControl>No</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
        CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(dslid, xmlBuiltin);
        
        String xmlExpert = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:lineDiagnosisRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort></intf:lineDiagnosisRequest></soapenv:Body></soapenv:Envelope>";
        CompletableFuture<Expert> Futureexpert = this.ossservice.getExpertXMLResult(xmlExpert);
        
        CompletableFuture.allOf(Futurebuiltin, Futureexpert).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("builtin",builtin);
		
		Expert expert=null;
		try {
			expert = Futureexpert.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("expert",expert);
		
		if(builtin.getErrorStatus()!=null || expert.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@GetMapping("/builtin-selt/dslid/{dslid}")
	public ResponseEntity<Object> getBuitinSelt(@PathVariable("dslid") String dslid) throws IllegalAccessException
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("dslid", dslid);
	    output.put("inputs", inputs);
        
        String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort><busyControl>No</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
        CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(dslid, xmlBuiltin);
        
        String xmlSelt = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header/></soapenv:Header><soapenv:Body><intf:lineTestRequest><linePort><CustomerID>"+dslid+"</CustomerID></linePort><testProfile>0.4mm/26AWG</testProfile><!--Optional:--><refTest>?</refTest><!--Optional:--><refDev><!--Optional:--><mdNm>?</mdNm><!--Optional:--><meNm>?</meNm></refDev></intf:lineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Selt> Futureselt = this.ossservice.getSeltXMLResult(xmlSelt);
        
        CompletableFuture.allOf(Futurebuiltin, Futureselt).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("builtin",builtin);
		
		Selt selt=null;
		try {
			selt = Futureselt.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("selt",selt);
		
		if(builtin.getErrorStatus()!=null || selt.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	
	///////////////////////// IP/PORT/SLOT/DSILD ////////////////////////////////
	
	@GetMapping("/builtin/ip/slot/port/{ip}/{slot}/{port}")
	public ResponseEntity<Object> getBuiltin_IPPORT(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port)
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("ip", ip);
		inputs.put("slot", slot);
		inputs.put("port", port);
	    output.put("inputs", inputs);
		
		String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header extVersion=\"?\" extAuthor=\"?\" tmf854Version=\"?\"><tmf:domain>?</tmf:domain><tmf:activityName>?</tmf:activityName><tmf:msgName>?</tmf:msgName><tmf:msgType>?</tmf:msgType><tmf:payloadVersion>?</tmf:payloadVersion><!--Optional:--><tmf:senderURI>?</tmf:senderURI><!--Optional:--><tmf:destinationURI>?</tmf:destinationURI><!--Optional:--><tmf:replyToURI>?</tmf:replyToURI><!--Optional:--><tmf:originatorURI>?</tmf:originatorURI><!--Optional:--><tmf:failureReplytoURI>?</tmf:failureReplytoURI><!--Optional:--><tmf:operationStatus>?</tmf:operationStatus><!--Optional:--><tmf:correlationId>?</tmf:correlationId><!--Optional:--><tmf:security>?</tmf:security><!--Optional:--><tmf:securityType>?</tmf:securityType><!--Optional:--><tmf:priority>?</tmf:priority><!--Optional:--><tmf:msgSpecificProperties><!--1 or more repetitions:--><tmf:property><tmf:propName>?</tmf:propName><tmf:propValue>?</tmf:propValue></tmf:property></tmf:msgSpecificProperties><tmf:communicationPattern>?</tmf:communicationPattern><tmf:communicationStyle>?</tmf:communicationStyle><!--Optional:--><tmf:requestedBatchSize>?</tmf:requestedBatchSize><!--Optional:--><tmf:batchSequenceNumber>?</tmf:batchSequenceNumber><!--Optional:--><tmf:batchSequenceEndOfReply>?</tmf:batchSequenceEndOfReply><!--Optional:--><tmf:fileLocationURI>?</tmf:fileLocationURI><!--Optional:--><tmf:compressionType>?</tmf:compressionType><!--Optional:--><tmf:packingType>?</tmf:packingType><tmf:timestamp>?</tmf:timestamp><!--Optional:--><tmf:vendorExtensions extAuthor=\"?\" version=\"?\" tmf854Version=\"?\"><!--You may enter ANY elements at this point--></tmf:vendorExtensions></tmf:MTOSI_Header></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><meNm>"+ip+"</meNm><!--Optional:--><ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm></linePort><busyControl>Forced</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(null, xmlBuiltin);
        
        CompletableFuture.allOf(Futurebuiltin).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("builtin", builtin);
		
		if(builtin.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found");
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);

	}
	
	@GetMapping("/expert/ip/slot/port/{ip}/{slot}/{port}")
	public ResponseEntity<Object> getExpert_IPPORT(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port)
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("ip", ip);
		inputs.put("slot", slot);
		inputs.put("port", port);
	    output.put("inputs", inputs);
		
		String xmlExpert="<?xml version=\"1.0\"?><soapenv:Envelope xmlns:intf=\"http://www.huawei.com/n2510/intf\" xmlns:tmf=\"tmf854.v1\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><tmf:MTOSI_Header tmf854Version=\"?\" extAuthor=\"?\" extVersion=\"?\"> <tmf:domain>iManager N2510 Line Assurance System</tmf:domain> <tmf:activityName>lineDiagnosis</tmf:activityName> <tmf:msgName>lineDiagnosisAction</tmf:msgName> <tmf:msgType>REQUEST</tmf:msgType> <tmf:senderURI>/lineAssurance/OSS</tmf:senderURI> <tmf:destinationURI>/LineAssuranceService/lineDiagnosisAction</tmf:destinationURI> <tmf:communicationPattern>SimpleResponse</tmf:communicationPattern> <tmf:communicationStyle>MSG</tmf:communicationStyle> </tmf:MTOSI_Header> </soapenv:Header><soapenv:Body><intf:lineDiagnosisRequest><linePort> <CustomerID/> <mdNm/> <meNm>"+ip+"</meNm> <ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm> </linePort> <lineID/> </intf:lineDiagnosisRequest> </soapenv:Body> </soapenv:Envelope>";
		CompletableFuture<Expert> Futureexpert = this.ossservice.getExpertXMLResult(xmlExpert);
        
        CompletableFuture.allOf(Futureexpert).join();
        
        Expert expert=null;
		try {
			expert = Futureexpert.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("expert", expert);
		
		if(expert.getErrorStatus()!=null)
		{
			log.info(expert.getErrorStatus()+": Data is not found");
			throw new CustomException(expert.getErrorStatus().replace("URL: https://10.232.161.137:10500/", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);

	}
	
	@GetMapping("/selt/ip/slot/port/{ip}/{slot}/{port}")
	public ResponseEntity<Object> getBEC_IPPORT(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port)
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("ip", ip);
		inputs.put("slot", slot);
		inputs.put("port", port);
	    output.put("inputs", inputs);
	    
	    String xmlSelt = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header extVersion=\"?\" extAuthor=\"?\" tmf854Version=\"?\"><tmf:domain>?</tmf:domain><tmf:activityName>?</tmf:activityName><tmf:msgName>?</tmf:msgName><tmf:msgType>?</tmf:msgType><tmf:payloadVersion>?</tmf:payloadVersion><!--Optional:--><tmf:senderURI>?</tmf:senderURI><!--Optional:--><tmf:destinationURI>?</tmf:destinationURI><!--Optional:--><tmf:replyToURI>?</tmf:replyToURI><!--Optional:--><tmf:originatorURI>?</tmf:originatorURI><!--Optional:--><tmf:failureReplytoURI>?</tmf:failureReplytoURI><!--Optional:--><tmf:operationStatus>?</tmf:operationStatus><!--Optional:--><tmf:correlationId>?</tmf:correlationId><!--Optional:--><tmf:security>?</tmf:security><!--Optional:--><tmf:securityType>?</tmf:securityType><!--Optional:--><tmf:priority>?</tmf:priority><!--Optional:--><tmf:msgSpecificProperties><!--1 or more repetitions:--><tmf:property><tmf:propName>?</tmf:propName><tmf:propValue>?</tmf:propValue></tmf:property></tmf:msgSpecificProperties><tmf:communicationPattern>?</tmf:communicationPattern><tmf:communicationStyle>?</tmf:communicationStyle><!--Optional:--><tmf:requestedBatchSize>?</tmf:requestedBatchSize><!--Optional:--><tmf:batchSequenceNumber>?</tmf:batchSequenceNumber><!--Optional:--><tmf:batchSequenceEndOfReply>?</tmf:batchSequenceEndOfReply><!--Optional:--><tmf:fileLocationURI>?</tmf:fileLocationURI><!--Optional:--><tmf:compressionType>?</tmf:compressionType><!--Optional:--><tmf:packingType>?</tmf:packingType><tmf:timestamp>?</tmf:timestamp><!--Optional:--><tmf:vendorExtensions extAuthor=\"?\" version=\"?\" tmf854Version=\"?\"><!--You may enter ANY elements at this point--></tmf:vendorExtensions></tmf:MTOSI_Header></soapenv:Header><soapenv:Body><intf:lineTestRequest><linePort><meNm>"+ip+"</meNm><!--Optional:--><ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm></linePort><testProfile>0.4mm/26AWG</testProfile><!--Optional:--><refTest>?</refTest><!--Optional:--><refDev><!--Optional:--><mdNm>?</mdNm><!--Optional:--><meNm>?</meNm></refDev></intf:lineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Selt> Futureselt=this.ossservice.getSeltXMLResult(xmlSelt);
        
        CompletableFuture.allOf(Futureselt).join();
        
        Selt selt=null;
		try {
			selt = Futureselt.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("selt", selt);
		
		if(selt.getErrorStatus()!=null)
		{
			log.info(selt.getErrorStatus()+": Data is not found");
			throw new CustomException(selt.getErrorStatus().replace("URL: https://10.232.161.137:10500/", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);

	}

	@GetMapping("/builtin-expert/ip/slot/port/{ip}/{slot}/{port}")
	public ResponseEntity<Object> getBuitinExpert_IPPORT(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port)
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("ip", ip);
		inputs.put("slot", slot);
		inputs.put("port", port);
	    output.put("inputs", inputs);
		
		String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header extVersion=\"?\" extAuthor=\"?\" tmf854Version=\"?\"><tmf:domain>?</tmf:domain><tmf:activityName>?</tmf:activityName><tmf:msgName>?</tmf:msgName><tmf:msgType>?</tmf:msgType><tmf:payloadVersion>?</tmf:payloadVersion><!--Optional:--><tmf:senderURI>?</tmf:senderURI><!--Optional:--><tmf:destinationURI>?</tmf:destinationURI><!--Optional:--><tmf:replyToURI>?</tmf:replyToURI><!--Optional:--><tmf:originatorURI>?</tmf:originatorURI><!--Optional:--><tmf:failureReplytoURI>?</tmf:failureReplytoURI><!--Optional:--><tmf:operationStatus>?</tmf:operationStatus><!--Optional:--><tmf:correlationId>?</tmf:correlationId><!--Optional:--><tmf:security>?</tmf:security><!--Optional:--><tmf:securityType>?</tmf:securityType><!--Optional:--><tmf:priority>?</tmf:priority><!--Optional:--><tmf:msgSpecificProperties><!--1 or more repetitions:--><tmf:property><tmf:propName>?</tmf:propName><tmf:propValue>?</tmf:propValue></tmf:property></tmf:msgSpecificProperties><tmf:communicationPattern>?</tmf:communicationPattern><tmf:communicationStyle>?</tmf:communicationStyle><!--Optional:--><tmf:requestedBatchSize>?</tmf:requestedBatchSize><!--Optional:--><tmf:batchSequenceNumber>?</tmf:batchSequenceNumber><!--Optional:--><tmf:batchSequenceEndOfReply>?</tmf:batchSequenceEndOfReply><!--Optional:--><tmf:fileLocationURI>?</tmf:fileLocationURI><!--Optional:--><tmf:compressionType>?</tmf:compressionType><!--Optional:--><tmf:packingType>?</tmf:packingType><tmf:timestamp>?</tmf:timestamp><!--Optional:--><tmf:vendorExtensions extAuthor=\"?\" version=\"?\" tmf854Version=\"?\"><!--You may enter ANY elements at this point--></tmf:vendorExtensions></tmf:MTOSI_Header></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><meNm>"+ip+"</meNm><!--Optional:--><ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm></linePort><busyControl>Forced</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(null, xmlBuiltin);
        
        String xmlExpert="<?xml version=\"1.0\"?><soapenv:Envelope xmlns:intf=\"http://www.huawei.com/n2510/intf\" xmlns:tmf=\"tmf854.v1\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><tmf:MTOSI_Header tmf854Version=\"?\" extAuthor=\"?\" extVersion=\"?\"> <tmf:domain>iManager N2510 Line Assurance System</tmf:domain> <tmf:activityName>lineDiagnosis</tmf:activityName> <tmf:msgName>lineDiagnosisAction</tmf:msgName> <tmf:msgType>REQUEST</tmf:msgType> <tmf:senderURI>/lineAssurance/OSS</tmf:senderURI> <tmf:destinationURI>/LineAssuranceService/lineDiagnosisAction</tmf:destinationURI> <tmf:communicationPattern>SimpleResponse</tmf:communicationPattern> <tmf:communicationStyle>MSG</tmf:communicationStyle> </tmf:MTOSI_Header> </soapenv:Header><soapenv:Body><intf:lineDiagnosisRequest><linePort> <CustomerID/> <mdNm/> <meNm>"+ip+"</meNm> <ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm> </linePort> <lineID/> </intf:lineDiagnosisRequest> </soapenv:Body> </soapenv:Envelope>";
		CompletableFuture<Expert> Futureexpert = this.ossservice.getExpertXMLResult(xmlExpert);
        
        CompletableFuture.allOf(Futurebuiltin, Futureexpert).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("builtin", builtin);
		
		Expert expert=null;
		try {
			expert = Futureexpert.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("expert", expert);
		
		if(builtin.getErrorStatus()!=null || expert.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found");
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);

	}
	
	@GetMapping("/builtin-selt/ip/slot/port/{ip}/{slot}/{port}")
	public ResponseEntity<Object> getBuiltinSelt_IPPORT(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port)
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("ip", ip);
		inputs.put("slot", slot);
		inputs.put("port", port);
	    output.put("inputs", inputs);
		
		String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header extVersion=\"?\" extAuthor=\"?\" tmf854Version=\"?\"><tmf:domain>?</tmf:domain><tmf:activityName>?</tmf:activityName><tmf:msgName>?</tmf:msgName><tmf:msgType>?</tmf:msgType><tmf:payloadVersion>?</tmf:payloadVersion><!--Optional:--><tmf:senderURI>?</tmf:senderURI><!--Optional:--><tmf:destinationURI>?</tmf:destinationURI><!--Optional:--><tmf:replyToURI>?</tmf:replyToURI><!--Optional:--><tmf:originatorURI>?</tmf:originatorURI><!--Optional:--><tmf:failureReplytoURI>?</tmf:failureReplytoURI><!--Optional:--><tmf:operationStatus>?</tmf:operationStatus><!--Optional:--><tmf:correlationId>?</tmf:correlationId><!--Optional:--><tmf:security>?</tmf:security><!--Optional:--><tmf:securityType>?</tmf:securityType><!--Optional:--><tmf:priority>?</tmf:priority><!--Optional:--><tmf:msgSpecificProperties><!--1 or more repetitions:--><tmf:property><tmf:propName>?</tmf:propName><tmf:propValue>?</tmf:propValue></tmf:property></tmf:msgSpecificProperties><tmf:communicationPattern>?</tmf:communicationPattern><tmf:communicationStyle>?</tmf:communicationStyle><!--Optional:--><tmf:requestedBatchSize>?</tmf:requestedBatchSize><!--Optional:--><tmf:batchSequenceNumber>?</tmf:batchSequenceNumber><!--Optional:--><tmf:batchSequenceEndOfReply>?</tmf:batchSequenceEndOfReply><!--Optional:--><tmf:fileLocationURI>?</tmf:fileLocationURI><!--Optional:--><tmf:compressionType>?</tmf:compressionType><!--Optional:--><tmf:packingType>?</tmf:packingType><tmf:timestamp>?</tmf:timestamp><!--Optional:--><tmf:vendorExtensions extAuthor=\"?\" version=\"?\" tmf854Version=\"?\"><!--You may enter ANY elements at this point--></tmf:vendorExtensions></tmf:MTOSI_Header></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><meNm>"+ip+"</meNm><!--Optional:--><ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm></linePort><busyControl>Forced</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(null, xmlBuiltin);
        
		String xmlSelt = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header extVersion=\"?\" extAuthor=\"?\" tmf854Version=\"?\"><tmf:domain>?</tmf:domain><tmf:activityName>?</tmf:activityName><tmf:msgName>?</tmf:msgName><tmf:msgType>?</tmf:msgType><tmf:payloadVersion>?</tmf:payloadVersion><!--Optional:--><tmf:senderURI>?</tmf:senderURI><!--Optional:--><tmf:destinationURI>?</tmf:destinationURI><!--Optional:--><tmf:replyToURI>?</tmf:replyToURI><!--Optional:--><tmf:originatorURI>?</tmf:originatorURI><!--Optional:--><tmf:failureReplytoURI>?</tmf:failureReplytoURI><!--Optional:--><tmf:operationStatus>?</tmf:operationStatus><!--Optional:--><tmf:correlationId>?</tmf:correlationId><!--Optional:--><tmf:security>?</tmf:security><!--Optional:--><tmf:securityType>?</tmf:securityType><!--Optional:--><tmf:priority>?</tmf:priority><!--Optional:--><tmf:msgSpecificProperties><!--1 or more repetitions:--><tmf:property><tmf:propName>?</tmf:propName><tmf:propValue>?</tmf:propValue></tmf:property></tmf:msgSpecificProperties><tmf:communicationPattern>?</tmf:communicationPattern><tmf:communicationStyle>?</tmf:communicationStyle><!--Optional:--><tmf:requestedBatchSize>?</tmf:requestedBatchSize><!--Optional:--><tmf:batchSequenceNumber>?</tmf:batchSequenceNumber><!--Optional:--><tmf:batchSequenceEndOfReply>?</tmf:batchSequenceEndOfReply><!--Optional:--><tmf:fileLocationURI>?</tmf:fileLocationURI><!--Optional:--><tmf:compressionType>?</tmf:compressionType><!--Optional:--><tmf:packingType>?</tmf:packingType><tmf:timestamp>?</tmf:timestamp><!--Optional:--><tmf:vendorExtensions extAuthor=\"?\" version=\"?\" tmf854Version=\"?\"><!--You may enter ANY elements at this point--></tmf:vendorExtensions></tmf:MTOSI_Header></soapenv:Header><soapenv:Body><intf:lineTestRequest><linePort><meNm>"+ip+"</meNm><!--Optional:--><ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm></linePort><testProfile>0.4mm/26AWG</testProfile><!--Optional:--><refTest>?</refTest><!--Optional:--><refDev><!--Optional:--><mdNm>?</mdNm><!--Optional:--><meNm>?</meNm></refDev></intf:lineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Selt> Futureselt=this.ossservice.getSeltXMLResult(xmlSelt);
        
        CompletableFuture.allOf(Futurebuiltin, Futureselt).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("builtin", builtin);
		
		Selt selt=null;
		try {
			selt = Futureselt.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("selt", selt);
		
		if(builtin.getErrorStatus()!=null || selt.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found");
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@GetMapping("/builtin-expert/ip/slot/port/dslid/{ip}/{slot}/{port}/{dslid}")
	public ResponseEntity<Object> getBEC_IPPORT(@PathVariable("ip") String ip, @PathVariable("slot") String slot,
			@PathVariable("port") String port, @PathVariable("dslid") String dslid)
	{
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, String> inputs = new LinkedHashMap<>();
		inputs.put("ip", ip);
		inputs.put("slot", slot);
		inputs.put("port", port);
		inputs.put("dslid", dslid);
	    output.put("inputs", inputs);
		
		String xmlBuiltin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tmf=\"tmf854.v1\" xmlns:intf=\"http://www.huawei.com/n2510/intf\"><soapenv:Header><tmf:MTOSI_Header extVersion=\"?\" extAuthor=\"?\" tmf854Version=\"?\"><tmf:domain>?</tmf:domain><tmf:activityName>?</tmf:activityName><tmf:msgName>?</tmf:msgName><tmf:msgType>?</tmf:msgType><tmf:payloadVersion>?</tmf:payloadVersion><!--Optional:--><tmf:senderURI>?</tmf:senderURI><!--Optional:--><tmf:destinationURI>?</tmf:destinationURI><!--Optional:--><tmf:replyToURI>?</tmf:replyToURI><!--Optional:--><tmf:originatorURI>?</tmf:originatorURI><!--Optional:--><tmf:failureReplytoURI>?</tmf:failureReplytoURI><!--Optional:--><tmf:operationStatus>?</tmf:operationStatus><!--Optional:--><tmf:correlationId>?</tmf:correlationId><!--Optional:--><tmf:security>?</tmf:security><!--Optional:--><tmf:securityType>?</tmf:securityType><!--Optional:--><tmf:priority>?</tmf:priority><!--Optional:--><tmf:msgSpecificProperties><!--1 or more repetitions:--><tmf:property><tmf:propName>?</tmf:propName><tmf:propValue>?</tmf:propValue></tmf:property></tmf:msgSpecificProperties><tmf:communicationPattern>?</tmf:communicationPattern><tmf:communicationStyle>?</tmf:communicationStyle><!--Optional:--><tmf:requestedBatchSize>?</tmf:requestedBatchSize><!--Optional:--><tmf:batchSequenceNumber>?</tmf:batchSequenceNumber><!--Optional:--><tmf:batchSequenceEndOfReply>?</tmf:batchSequenceEndOfReply><!--Optional:--><tmf:fileLocationURI>?</tmf:fileLocationURI><!--Optional:--><tmf:compressionType>?</tmf:compressionType><!--Optional:--><tmf:packingType>?</tmf:packingType><tmf:timestamp>?</tmf:timestamp><!--Optional:--><tmf:vendorExtensions extAuthor=\"?\" version=\"?\" tmf854Version=\"?\"><!--You may enter ANY elements at this point--></tmf:vendorExtensions></tmf:MTOSI_Header></soapenv:Header><soapenv:Body><intf:hostLoopLineTestRequest><linePort><meNm>"+ip+"</meNm><!--Optional:--><ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm></linePort><busyControl>Forced</busyControl></intf:hostLoopLineTestRequest></soapenv:Body></soapenv:Envelope>";
		CompletableFuture<Builtin> Futurebuiltin=this.ossservice.getBuilinXMLResult(dslid, xmlBuiltin);
        
        String xmlExpert="<?xml version=\"1.0\"?><soapenv:Envelope xmlns:intf=\"http://www.huawei.com/n2510/intf\" xmlns:tmf=\"tmf854.v1\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><tmf:MTOSI_Header tmf854Version=\"?\" extAuthor=\"?\" extVersion=\"?\"> <tmf:domain>iManager N2510 Line Assurance System</tmf:domain> <tmf:activityName>lineDiagnosis</tmf:activityName> <tmf:msgName>lineDiagnosisAction</tmf:msgName> <tmf:msgType>REQUEST</tmf:msgType> <tmf:senderURI>/lineAssurance/OSS</tmf:senderURI> <tmf:destinationURI>/LineAssuranceService/lineDiagnosisAction</tmf:destinationURI> <tmf:communicationPattern>SimpleResponse</tmf:communicationPattern> <tmf:communicationStyle>MSG</tmf:communicationStyle> </tmf:MTOSI_Header> </soapenv:Header><soapenv:Body><intf:lineDiagnosisRequest><linePort> <CustomerID/> <mdNm/> <meNm>"+ip+"</meNm> <ptpNm>/shelf=0/slot="+slot+"/port="+port+"</ptpNm> </linePort> <lineID/> </intf:lineDiagnosisRequest> </soapenv:Body> </soapenv:Envelope>";
		CompletableFuture<Expert> Futureexpert = this.ossservice.getExpertXMLResult(xmlExpert);
        
        CompletableFuture.allOf(Futurebuiltin, Futureexpert).join();
        
        Builtin builtin=null;
		try {
			builtin = Futurebuiltin.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("builtin", builtin);
		
		Expert expert=null;
		try {
			expert = Futureexpert.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		output.put("expert", expert);
		
		if(builtin.getErrorStatus()!=null || expert.getErrorStatus()!=null)
		{
			log.info(builtin.getErrorStatus()+": Data is not found for " +dslid);
			throw new CustomException(builtin.getErrorStatus().replace("URL: https://10.232.161.137:10500/", "") +dslid);
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);

	}
}
