package com.pb.biser.conveyor.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by diver on 5/15/15.
 */
public final class ConveyorSettings implements ConveyorSettingsI {

    private static final String PROPERTIES = "biser_conveyor.properties";

    private static final int DEFAULT_MAX_COUNT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 1000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 1000;

    private static final String ID = "conveyor.id";
    private static final String URL = "conveyor.url";
    private static final String API_KEY = "conveyor.api.key";
    private static final String API_LOGIN = "conveyor.api.login";
    private static final String MAX_COUNT = "conveyor.max_count";
    private static final String READ_TIMEOUT = "conveyor.read_timeout";
    private static final String CONNECT_TIMEOUT = "conveyor.connect_timeout";


    private String id;
    private String url;
    private String apiKey;
    private String apiLogin;
    private int maxCount;
    private int connectTimeout;
    private int readTimeout;

    private ConveyorSettings() {}

    @Override
    public String getConveyorId() {
        return id;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getApiLogin() {
        return apiLogin;
    }

    @Override
    public int getConnectTimeout() {
        return connectTimeout;
    }

    @Override
    public int getReadTimeout() {
        return readTimeout;
    }

    @Override
    public int getMaxCount() {
        return maxCount;
    }

    public static ConveyorSettingsI getDefault() throws IOException {
        ConveyorSettings conveyorSettings = new ConveyorSettings();
        InputStream stream = conveyorSettings.getStream();
        try {
            Properties properties = new Properties();
            properties.load(stream);
            conveyorSettings.initialize(properties);
            return conveyorSettings;
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private void initialize(Properties properties) {
        this.id = properties.getProperty(ID);
        this.url = properties.getProperty(URL);
        this.apiKey = properties.getProperty(API_KEY);
        this.apiLogin = properties.getProperty(API_LOGIN);
        if (isOneOfInitializedParametersIsEmpty()) {
            throw new NullPointerException("Отсутствует обязательный параметрв настройках по умолчанию");
        }
        this.maxCount = getAsInteger(properties.getProperty(MAX_COUNT), DEFAULT_MAX_COUNT);
        this.readTimeout = getAsInteger(properties.getProperty(READ_TIMEOUT), DEFAULT_READ_TIMEOUT);
        this.connectTimeout = getAsInteger(properties.getProperty(CONNECT_TIMEOUT), DEFAULT_CONNECT_TIMEOUT);
    }

    public int getAsInteger(String value, int defaultValue) {
        try {
            return Integer.valueOf(value);
        } catch (Exception ex) {
            ex.printStackTrace();
            return defaultValue;
        }
    }

    private boolean isOneOfInitializedParametersIsEmpty() {
        return (url == null || url.isEmpty()) ||
                (apiKey == null || apiKey.isEmpty()) ||
                (apiLogin == null || apiLogin.isEmpty());
    }

    private InputStream getStream() {
        return this.getClass().getClassLoader().getResourceAsStream(PROPERTIES);
    }
}
