package gizmo.core;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.time.ZonedDateTime;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;


@Singleton
@Startup
public class Starter {

	private static final Logger LOG = Logger.getLogger(Starter.class);
	
	public static String DEFAULT_HOST = "localhost";
	
	private ZonedDateTime startTime;
  private MemoryUsage heapUsageAtStartTime;
  private MemoryMXBean memoryMxBean;
  
  @PersistenceContext(unitName="primary")
  EntityManager em;
  
  public Starter() {
		LOG.info("Starter called");
		Connection conn = getConnectionPostgres();
		try {
			LOG.info("Connecting to '" + conn.getCatalog() + "' database catalog");
		}
		catch (Exception e) {
			LOG.error(e);
		}
		finally {
			DbUtils.closeQuietly(conn);
		}
	}
	
  private Connection getConnectionPostgres() {
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource)ic.lookup("java:jboss/datasources/PostgresDS");
			return ds.getConnection();
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	
	@PostConstruct
  public void initialize() {
		initializeStartTime();
		initializeMemory();
		String host = getHost();
    LOG.info("Initialization complete on " + host);
  }
	
	@Schedule(hour="11", minute="42", persistent=false)
	public void scheduleDbBackup() {
		LOG.info("Db backup fired");
		runDbBackup();
	}
	
	void initializeStartTime() {
    startTime = ZonedDateTime.now();
	}
	
	private void initializeMemory() {
		memoryMxBean = ManagementFactory.getMemoryMXBean();
    heapUsageAtStartTime = memoryMxBean.getHeapMemoryUsage();
	}
	
	private void runDbBackup() {
		DbBackup backup = new DbBackup();
		backup.run();
	}
	
	public ZonedDateTime getDateTime() {
		return startTime;
	}
	
	public double availableMemoryInMB() {
		MemoryUsage current = this.memoryMxBean.getHeapMemoryUsage();
		long available = (current.getCommitted() - current.getUsed());
		return asMb(available);
	}
	
	public double usedMemoryInMb() {
		MemoryUsage current = this.memoryMxBean.getHeapMemoryUsage();
		return asMb(current.getUsed());
	}
	
	public String usedMemoryInMbAtStartTime() {
		return heapUsageAtStartTime.toString();
	}
	
	public static JsonObject osInfo() {
		OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    	return Json.createObjectBuilder()
    			.add("System Load Average", osBean.getSystemLoadAverage())
    			.add("Available CPUs", osBean.getAvailableProcessors())
    			.add("Architecture", osBean.getArch())
    			.add("OS Name", osBean.getName())
    			.add("Version", osBean.getVersion())
    			.build();
	}
	
	double asMb(long bytes) {
		return bytes / 1024 / 1024;
	}

	@PreDestroy
	private void atShutdown() {
		LOG.info("Shutting down server...");
		shutdown();
	}
	
	private void shutdown() {
		LOG.info("shutdown...");
	}
	
	private String getHost() {
		String host = DEFAULT_HOST;
  	try {
  		InetAddress address = InetAddress.getLocalHost();
  		if (address != null) {
  			host = address.getHostName();
  		}
  	}
  	catch (UnknownHostException uhe) {
			uhe.printStackTrace();
  	}
  	return host;		
	}
	
}