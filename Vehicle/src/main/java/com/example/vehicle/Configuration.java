package com.example.vehicle;

import java.util.Properties;

import org.restexpress.RestExpress;
import com.example.vehicle.objectid.VehicleController;
import com.example.vehicle.objectid.VehicleRepository;
import com.example.vehicle.objectid.VehicleService;
import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

/**
 *
 * @author gstafford
 */
public class Configuration
        extends Environment {

    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT_PROPERTY = "port";
    //private static final String BASE_URL_PROPERTY = "base.url";
    private static final String BASE_URL_PROPERTY = "base.url" + "port";

    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private int port;
    private String baseUrl;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;

    private VehicleController vehicleController;

    /**
     *
     * @param p
     */
    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        VehicleRepository vehiclesRepository = new VehicleRepository(mongo.getClient(), mongo.getDbName());
        VehicleService vehicleService = new VehicleService(vehiclesRepository);
        vehicleController = new VehicleController(vehicleService);
    }

    /**
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     *
     * @return
     */
    public int getExecutorThreadPoolSize() {
        return executorThreadPoolSize;
    }

    /**
     *
     * @return
     */
    public MetricsConfig getMetricsConfig() {
        return metricsSettings;
    }

    /**
     *
     * @return
     */
    public VehicleController getVehicleController() {
        return vehicleController;
    }
}
