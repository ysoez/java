package persistence.util;

import persistence.util.provider.HSQLDBDataSourceProvider;
import persistence.util.provider.PostgreSQLDataSourceProvider;

public enum Database {

    HSQLDB {
        @Override
        public Class<? extends DataSourceProvider> dataSourceProviderClass() {
            return HSQLDBDataSourceProvider.class;
        }
    },
    POSTGRESQL {
        @Override
        public Class<? extends DataSourceProvider> dataSourceProviderClass() {
            return PostgreSQLDataSourceProvider.class;
        }
    };

    public DataSourceProvider dataSourceProvider() {
        return ReflectionUtils.newInstance(dataSourceProviderClass().getName());
    }

    public abstract Class<? extends DataSourceProvider> dataSourceProviderClass();

}
