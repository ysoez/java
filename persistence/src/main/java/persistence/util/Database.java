package persistence.util;

import persistence.util.provider.HSQLDBDataSourceProvider;

public enum Database {

    HSQLDB {
        @Override
        public Class<? extends DataSourceProvider> dataSourceProviderClass() {
            return HSQLDBDataSourceProvider.class;
        }
    };

    public DataSourceProvider dataSourceProvider() {
        return ReflectionUtils.newInstance(dataSourceProviderClass().getName());
    }

    public abstract Class<? extends DataSourceProvider> dataSourceProviderClass();

}
