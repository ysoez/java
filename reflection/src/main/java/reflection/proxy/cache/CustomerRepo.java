package reflection.proxy.cache;

interface CustomerRepo {

    @Cacheable
    Long getIdByName(String name);

}
