package gizmo.core.db;


public enum DbConnectionType {

	N              ("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@vcetdbvn:1521/vetdb01","DEVSUP","summ3rfun",Boolean.FALSE),
	N1             ("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@entn1dbw1:1521/etn1db","DEVSUP","summ3rfun",Boolean.FALSE),
	N2A            ("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@scan-etdbn2ah:1521/etn2a_dev_ro","ent_user_svc","us3rsvc#n2a",Boolean.FALSE),
	POSTGRESQL     ("org.postgresql.Driver","jdbc:postgresql://localhost:5432/postgres","postgres","<password>",Boolean.FALSE),
	PSSO           ("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@vcpssodbtn2:1521/vpssodb01_psso_rw","DEVSUP","summ3rfun",Boolean.FALSE);
	
	private String driver;
	private String db;
	private String user;
	private String password;
	private boolean queryTimeout;
	
	private DbConnectionType(String driver, String db, String user, String password, boolean queryTimeout) {
		this.driver = driver;
		this.db = db;
		this.user = user;
		this.password = password;
		this.queryTimeout = queryTimeout;
	}

	public String getDriver() {
		return driver;
	}

	public String getDb() {
		return db;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean implementsQueryTimeout() {
		return queryTimeout;
	}
	
	public static DbConnectionType getEnumFromOrdinal(int value) {
		for (DbConnectionType type : DbConnectionType.values()) {
			if (type.ordinal() == value) {
				return type;
			}
		}
		return null;
	}
	
	public static DbConnectionType getEnumFromName(String value) {
		for (DbConnectionType type : DbConnectionType.values()) {
			if (type.name().equalsIgnoreCase(value)) {
				return type;
			}
		}
		return null;
	}
	
}