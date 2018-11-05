package document;

public class Network {

    /*
    private static String readProperties(String key) {
        try {
            Properties prop = new Properties();
            prop.load(Network.class.getResourceAsStream("/network.properties"));
            return prop.getProperty(key);
        } catch(IOException e) { throw new RuntimeException(e); }
    }

    static final String HOST = readProperties("databaseHostName");
    static final int PORT = Integer.parseInt(readProperties("databasePort"));
    static final String DATABASE = "delivery-db";
    static final String COLLECTION = "delivery";
    */

    static final String HOST = "delivery-database";
    static final int PORT = 27017;
    static final String DATABASE = "delivery-db";
    static final String COLLECTION = "delivery";
}
