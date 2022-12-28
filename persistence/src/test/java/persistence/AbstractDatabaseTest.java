package persistence;

import javax.sql.DataSource;

abstract class AbstractDatabaseTest extends TestContainers {

    abstract DataSource providerDataSource();

}
