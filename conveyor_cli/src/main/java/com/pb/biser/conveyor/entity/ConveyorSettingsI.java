package com.pb.biser.conveyor.entity;

/**
 * Created by diver on 5/14/15.
 */
public interface ConveyorSettingsI {

    /**
     *
     * @return Идентификатор конвейреа
     */
    String getConveyorId();

    /**
     *
     * @return API ключ доступа к конвейреу
     */
    String getApiKey();

    /**
     *
     * @return УРЛ адрес доступа к конвейеру
     */
    String getUrl();

    /**
     *
     * @return API логин доступа к конвейеру
     */
    String getApiLogin();

    /**
     *
     * @return Максимальное количество запросов к конвейеру
     */
    int getMaxCount();

    /**
     *
     * @return Время ожидания отклика от конвейера
     */
    int getConnectTimeout();

    /**
     *
     * @return Время ожидания получения данных от конвейера
     */
    int getReadTimeout();
}
