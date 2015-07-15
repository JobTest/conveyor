package com.pb.biser.conveyor.entity;

import java.util.Map;

/**
 * Created by diver on 5/14/15.
 */
public interface BCRequestI {

    /**
     *
     * @return Поисковые параметры задолженности
     */
    Map<String, String> getParameters();

    /**
     *
     * @return Идентификатор предприятия
     */
    Integer getCompanyId();

    /**
     *
     * @return Точка приема
     */
    String getPoint();

    /**
     *
     * @return Код работы системы
     */
    String getCode();

    /**
     *
     * @return Проверяемый объект
     */
    String getObject();

    /**
     *
     * @return Сессия комплекса
     */
    String getSession();

    /**
     *
     * @return Тип выполняемого запроса
     */
    String getType();
}
