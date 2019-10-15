package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties configFile;


    static {

            try {
                //path to our .properties file
                //MIGHT BE WRONG PATH HERE, PAY ATTENTION
                String path = "configuration.properties";
                //we create object of input stream to access file
                System.out.println(path);
                //provides access to file
                FileInputStream input = new FileInputStream(path);
                //initialize configFile
                configFile = new Properties();
                //load properties file
                configFile.load(input);
                //close input stream
                input.close();
            } catch (Exception e){
                e.printStackTrace();
            }
    }

    public static synchronized String getProperty(String key){
        return configFile.getProperty(key);
    }
}
