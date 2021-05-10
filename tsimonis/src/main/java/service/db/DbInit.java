package service.db;

import com.google.common.io.Resources;
import model.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

public class DbInit {
    private final JdbcTemplate source;
    private final String h2InitFileName = "h2DbCreate.sql";
    private final String mysqlInitFileName = "mysqlDbCreate.sql";

    public DbInit(JdbcTemplate source) {
        this.source = source;
    }

    private String getSQL() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(mysqlInitFileName);
        Objects.requireNonNull(url);
        return Resources.toString(url, Utils.charset);
    }

    public void create() throws SQLException, IOException {
        String sqlString = getSQL();
        source.statement(statement -> {
            statement.execute(sqlString);
        });
    }
}
