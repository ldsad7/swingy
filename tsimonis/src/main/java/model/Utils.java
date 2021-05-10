package model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.enums.Color;
import model.exceptions.ValidationException;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import service.db.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.Set;


public class Utils {
    public static final Random RANDOM = new Random();
    public static final Charset charset = StandardCharsets.UTF_8;
    //    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String H2_JDBC_DB_URL = "jdbc:h2:mem:database;DB_CLOSE_DELAY=-1";
//    public static final JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcConnectionPool.create(H2_JDBC_DB_URL, "sa", ""));
    private final static String PROPERTIES_FILE = "application.properties";
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    public static Properties props;
    public static final JdbcTemplate jdbcTemplate = new JdbcTemplate(setUpPool());

    private static void readProperties() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(PROPERTIES_FILE);
        Objects.requireNonNull(url);
        props = new Properties();
        try (FileReader fr = new FileReader(url.getPath())) {
            props.load(fr);
        } catch (IOException e) {
            throw new ValidationException("We weren't able to read `properties` file");
        }
    }

    private static DataSource setUpPool() {
//        try {
//            Class.forName(props.getProperty("db.driver.class"));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }

        // Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
        GenericObjectPool gPool = new GenericObjectPool();
        gPool.setMaxActive(5);

        // Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
        if (props == null) {
            readProperties();
        }
        ConnectionFactory cf = new DriverManagerConnectionFactory(
                props.getProperty("db.conn.url"), props.getProperty("db.username"), props.getProperty("db.password"));

        // Creates a PoolableConnectionFactory That Will Wrap the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
        new PoolableConnectionFactory(cf, gPool, null, null, false, true);

        return new PoolingDataSource(gPool);
    }

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations.size() > 0) {
            throw new ValidationException("Incorrect arguments were given to the " + object +
                    " (" + constraintViolations + ")");
        }
    }

    public static String getColorString(String string, Color color) {
        return color.getColor() + string + Color.ENDC.getColor();
    }

    public static String readFile(String path, Charset charset) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes, charset);
    }
}
