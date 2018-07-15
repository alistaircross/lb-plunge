package my.application.browser.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AuthHelper {
    private static final Log LOG = LogFactory.getLog(AuthHelper.class);
    private static final String AUTH_FILENAME = "login.properties";
    private Properties prop;
    private InputStream input = null;

    public AuthHelper() {
        prop = new Properties();
        LOG.trace("Loading properties for authentication.");
        input = AuthHelper.class.getClassLoader().getResourceAsStream(AUTH_FILENAME);
        try {
            prop.load(input);
        } catch (IOException e) {
            LOG.error("Problem loading the auth props.", e);
        }
    }

    public CharSequence getPassword() {
        return prop.getProperty("password");
    }

    public CharSequence getUserName() {
        return prop.getProperty("username");
    }
}
