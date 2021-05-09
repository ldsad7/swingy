package service.db;

import com.google.common.io.Resources;
import model.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class DbInit {
    private final JdbcTemplate source;
    private final String initFileName = "dbcreate.sql";

    public DbInit(JdbcTemplate source) {
        this.source = source;
    }

    private String getSQL(String fileName) throws IOException {
        return Resources.toString(this.getClass().getResource(fileName), Utils.charset);
    }

    public void create() throws SQLException, IOException {
        String sqlString = getSQL(initFileName);
        source.statement(stmt -> {
            stmt.execute(sqlString);
        });
    }
}
