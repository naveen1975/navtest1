package com.customs.logservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/logowner")
public class LogOwnerService {

	Logger log = Logger.getLogger(LogOwnerService.class);
	
	Logger serviceLog = Logger.getLogger("LogService");
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssss");

    @Path("searchlogs")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String searchLogs(MultivaluedMap<String, String> userParams) {
    	
    	Response.ResponseBuilder builder = null;
    	 try {
	        	String sourceFunction = userParams.getFirst("function");
	        	String sourceModule = userParams.getFirst("module");
	        	String transactionReferenceId = userParams.getFirst("transRefId");
	        	String subTransactionId = userParams.getFirst("subTransRefId");
	        	String startLogDate = userParams.getFirst("startDate");
	        	String startLogTime = userParams.getFirst("startTime");
	        	String endLogDate = userParams.getFirst("endDate");
	        	String endLogTime = userParams.getFirst("endTime");
	        	
	        	
	        	Date now = new Date();
	        	
	        	
	        	log.info("Searching for logs...");
	        	
	        	String toJson = "{"
				 + "\"Log\": [ "
				 +"  { "
				 +"     \"Level\": \"INFO\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\", "
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Started\" "
				 +"   }, "
				 +"   { " 
				 +"     \"Level\": \"DEBUG\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\", "
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Stopped\" "
				 +"   }, "
				 +"   { "
				 +"     \"Level\": \"FATAL\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\","
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Ended\" "
				 +"   } "
				 +" ] "
				 +"}";
	        			
	        			
	        	
	            // Create an "ok" response
	          return toJson;
	        }  
	        catch (ValidationException e) {
	            // Handle the unique constrain violation
	            Map<String, String> responseObj = new HashMap<String, String>();
	            responseObj.put("email", "Email taken");
	            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
	        } catch (Exception e) {
	            // Handle generic exceptions
	            Map<String, String> responseObj = new HashMap<String, String>();
	            responseObj.put("error", e.getMessage());
	            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	        }
	        
	        return builder.build().toString();
    	
    }
    
    @Path("purgelogs")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String purgeLogs(MultivaluedMap<String, String> userParams) {
       	Response.ResponseBuilder builder = null;
   	 try {
	        	String sourceFunction = userParams.getFirst("function");
	        	String sourceModule = userParams.getFirst("module");
	        	String transactionReferenceId = userParams.getFirst("transRefId");
	        	String subTransactionId = userParams.getFirst("subTransRefId");
	        	String startLogDate = userParams.getFirst("startDate");
	        	String startLogTime = userParams.getFirst("startTime");
	        	String endLogDate = userParams.getFirst("endDate");
	        	String endLogTime = userParams.getFirst("endTime");
	        	
	        	Date now = new Date();
	        	
	        	
	        	log.info("Searching for logs...");
	        	
	        	String toJson = "{"
				 + "\"Log\": [ "
				 +"  { "
				 +"     \"Level\": \"INFO\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\", "
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Started\" "
				 +"   }, "
				 +"   { " 
				 +"     \"Level\": \"DEBUG\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\", "
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Stopped\" "
				 +"   }, "
				 +"   { "
				 +"     \"Level\": \"FATAL\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\","
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Ended\" "
				 +"   } "
				 +" ] "
				 +"}";
	        			
	        			
	        	
	            // Create an "ok" response
	          return toJson;
	        }  
	        catch (ValidationException e) {
	            // Handle the unique constrain violation
	            Map<String, String> responseObj = new HashMap<String, String>();
	            responseObj.put("email", "Email taken");
	            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
	        } catch (Exception e) {
	            // Handle generic exceptions
	            Map<String, String> responseObj = new HashMap<String, String>();
	            responseObj.put("error", e.getMessage());
	            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	        }
	        
	        return builder.build().toString();
    }   
    
    @Path("logdetail")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String logDetail(MultivaluedMap<String, String> userParams) {
       	Response.ResponseBuilder builder = null;
   	 try {
	        	String sourceFunction = userParams.getFirst("function");
	        	String sourceModule = userParams.getFirst("module");
	        	String transactionReferenceId = userParams.getFirst("transRefId");
	        	String subTransactionId = userParams.getFirst("subTransRefId");
	        	String startLogDate = userParams.getFirst("startDate");
	        	String startLogTime = userParams.getFirst("startTime");
	        	String endLogDate = userParams.getFirst("endDate");
	        	String endLogTime = userParams.getFirst("endTime");
	        	
	        	
	        	Date now = new Date();
	        	
	        	
	        	log.info("Searching for logs...");
	        	
	        	String toJson = "{"
				 + "\"Log\": [ "
				 +"  { "
				 +"     \"Level\": \"INFO\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\", "
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Started\" "
				 +"   }, "
				 +"   { " 
				 +"     \"Level\": \"DEBUG\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\", "
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Stopped\" "
				 +"   }, "
				 +"   { "
				 +"     \"Level\": \"FATAL\", "
				 +"     \"Date\": \"10/10/2017\", "
				 +"     \"Time\": \"03:30:234323\", "
				 +"     \"Module\": \"TradeXchange\", "
				 +"     \"Function\": \"PaaS\", "
				 +"     \"TransactionId\": \"PaaS101\","
				 +"     \"SubTransactionId\": \"PaaS101_1\", "
				 +"     \"Msg\": \"Application Ended\" "
				 +"   } "
				 +" ] "
				 +"}";
	        			
	        			
	        	
	            // Create an "ok" response
	          return toJson;
	        }  
	        catch (ValidationException e) {
	            // Handle the unique constrain violation
	            Map<String, String> responseObj = new HashMap<String, String>();
	            responseObj.put("email", "Email taken");
	            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
	        } catch (Exception e) {
	            // Handle generic exceptions
	            Map<String, String> responseObj = new HashMap<String, String>();
	            responseObj.put("error", e.getMessage());
	            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	        }
	        
	        return builder.build().toString();
    }    

}
