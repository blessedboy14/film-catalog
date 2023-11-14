package service.factory;

import service.CatalogService;
import service.ClientService;
import service.impl.CatalogServiceImpl;
import service.impl.ClientServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final CatalogService catalogService = new CatalogServiceImpl();
    private final ClientService clientService = new ClientServiceImpl();
    private ServiceFactory () {}

    public static ServiceFactory getInstance(){
        return instance;
    }
    public ClientService getClientService(){
        return clientService;
    }
    public CatalogService getCatalogService(){
        return catalogService;
    }

}
