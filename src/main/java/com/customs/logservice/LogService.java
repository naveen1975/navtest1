package com.customs.logservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/log")
public class LogService {
	
		Logger log = Logger.getLogger(LogService.class);
		
		Logger serviceLog = Logger.getLogger("LogService");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssss");

	    @Path("writeLog")
	    @POST
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response writeLog(MultivaluedMap<String, String> userParams) {
	    	Response.ResponseBuilder builder = null;

	        try {
	        	String logLevel = userParams.getFirst("level");
	        	String sourceFunction = userParams.getFirst("function");
	        	String sourceModule = userParams.getFirst("module");
	        	String transactionReferenceId = userParams.getFirst("transRefId");
	        	String subTransactionId = userParams.getFirst("subTransRefId");
	        	String logDate = userParams.getFirst("logDate");
	        	String logTime = userParams.getFirst("logTime");
	        	
	        	String msg = userParams.getFirst("logmsg");
	        	
	        	Date now = new Date();
	        	logLevel = logLevel == null? "INFO": logLevel;
	        	logDate = logDate == null? dateFormat.format(now) : logDate;
	        	logTime = logTime == null? timeFormat.format(now) : logTime;
	        	
	        	sourceModule  = sourceModule == null? "NA" : sourceModule;
	        	sourceFunction  = sourceFunction == null? "NA" : sourceFunction;
	        	transactionReferenceId  = transactionReferenceId == null? "NA" : transactionReferenceId;
	        	subTransactionId  = subTransactionId == null? "NA" : subTransactionId;
	        	
	        	String finalMsg = logDate + " " + logTime + " " + sourceModule + " " + sourceFunction 
	        						+ " " +  transactionReferenceId + " " + subTransactionId + " " + msg;
	        	
	        	log.info(finalMsg);
	        	
	        	
	        	if(logLevel != null && logLevel.equalsIgnoreCase("INFO"))
	        	{
	        		serviceLog.info(finalMsg);
	        	}
	        	
	        	if(logLevel != null && logLevel.equalsIgnoreCase("DEBUG"))
	        	{
	        		serviceLog.debug(finalMsg);
	        	}
	        	
	        	if(logLevel != null && logLevel.equalsIgnoreCase("ERROR"))
	        	{
	        		serviceLog.error(finalMsg);
	        	}	        	
	        
	        	if(logLevel != null && logLevel.equalsIgnoreCase("FATAL"))
	        	{
	        		serviceLog.fatal(finalMsg);
	        	}
	        	
	        	writeToDB(logLevel, logDate, logTime, sourceModule, sourceFunction, transactionReferenceId, subTransactionId, msg);
	            // Create an "ok" response
	            builder = Response.ok();
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
	        
	        return builder.build();
	    }
	    
	    
	    boolean writeToDB(String level, String logDate, String logTime, String module, String function, String txn, String subTxn, String msg)
	    {
	    	//SVC_LOG
	    	//LOG_ID,
	    	//"INSERT INTO SVC_LOG(LOG_ID, LOG_LEVEL, LOG_DATE, LOG_TIME, LOG_MODULE, LOG_FUNCTION, LOG_TXN, LOG_SUBTXN, LOG_MSG) "
	    	//+ "VALUES (?,?,?,?,?,?,?,?,?);"
	    	
	    	Connection result = null;
	    	try {
	    	    Context initialContext = new InitialContext();
	    	    DataSource datasource = (DataSource)initialContext.lookup("java:jboss/datasources/MySQLDS");
	    	    result = datasource.getConnection();

	    	    String insertStmt = "INSERT INTO SVC_LOG(LOG_ID, LOG_LEVEL, LOG_DATE, LOG_TIME, LOG_MODULE, LOG_FUNCTION, LOG_TXN, LOG_SUBTXN, LOG_MSG) " 
	    	    			  + " VALUES (?,?,?,?,?,?,?,?,?) ";
	    	    
	    	    PreparedStatement stmt = result.prepareStatement(insertStmt) ;
	    	    
	    	    stmt.setString(1, "" + System.currentTimeMillis());
	    	    stmt.setString(2, level);
	    	    stmt.setDate(3,  new java.sql.Date(dateFormat.parse(logDate).getTime()));
	    	    stmt.setTimestamp(4, new java.sql.Timestamp(timeFormat.parse(logTime).getTime()));
	    	    stmt.setString(5, module);
	    	    stmt.setString(6, function);
	    	    stmt.setString(7, txn);
	    	    stmt.setString(8, subTxn);
	    	    stmt.setString(9, msg);
	    	    
	    	    
	    	    return stmt.execute();
	    	} catch (Exception ex) {
	    	    log.error(ex.getMessage());
	    	}
	    	finally
	    	{
	    		try
	    		{
	    			if(result!=null)
	    				result.close();
	    		}
	    		catch(Exception err)
	    		{
	    			err.printStackTrace();
	    		}
	    	}
	    	
	    	return false;
	    	
	    }

}
