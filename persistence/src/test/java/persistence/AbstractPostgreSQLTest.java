package persistence;

import persistence.datasource.provider.Database;

public abstract class AbstractPostgreSQLTest extends AbstractTest {

    @Override
    protected Database database() {
        return Database.POSTGRESQL;
    }

}
