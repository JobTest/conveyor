package com.pb.biser.conveyor.entity;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * Created by diver on 5/14/15.
 */
public class BCRequest implements BCRequestI {

    private static final String OK = "DT_OK";
    private static final String PRESEARCH_ID = "presearchId";

    public enum Type { SEARCH, PRESEARCH }

    public static class Builder {

        private BCRequest request;

        private Builder() {
            request = new BCRequest();
            request.code = OK;
        }

        public Builder addParameters(Map<String, String> parameters) {
            request.parameters = parameters;
            return this;
        }

        public Builder addPresearchId(String presearchId) {
            if (MapUtils.isEmpty(request.parameters)) {
                request.parameters = new HashMap<String, String>();
            }
            request.parameters.put(PRESEARCH_ID, presearchId);
            return this;
        }

        public Builder addCompany(Integer companyId) {
            request.companyId = companyId;
            return this;
        }

        public Builder addSession(String session) {
            request.session = session;
            return this;
        }

        public Builder addType(Type type) {
            request.type = type == null ? null : type.name();
            return this;
        }

        public Builder addObject(Object _object) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            try {
                GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream);
                ObjectOutputStream stream = new ObjectOutputStream(gzipStream);

                stream.writeObject(_object);
                stream.flush();
                stream.close();
                request._object = Base64.encodeBase64String(byteStream.toByteArray());
            } catch (IOException ex) {
                throw new IllegalArgumentException(ex.getMessage(), ex);
            }
            return this;
        }

        public Builder addPoint(String point) {
            request.point = point;
            return this;
        }

        public Builder addCode(String code) {
            request.code = code;
            return this;
        }

        public BCRequestI build() {
            if (MapUtils.isEmpty(request.parameters)) {
                throw new IllegalArgumentException("Отсутствуют поисковые параметры");
            }
            if (request.companyId == null) {
                throw new IllegalArgumentException("Отсутствует идентификатор компании");
            }
            if (request.session == null) {
                throw new IllegalArgumentException("Отсутствует идентификатор сессии системы");
            }
            if (request.code == null) {
                throw new IllegalArgumentException("Отсутствует код работы системы");
            }
            if (request.point == null) {
                throw new IllegalArgumentException("Отсутствует точка приема");
            }
            if (request.type == null) {
                throw new IllegalArgumentException("Не указан тип выполняемого запроса");
            }
            return request;
        }

        public static Builder create() {
            return new Builder();
        }
    }

    public Map<String, String> parameters;
    public Integer companyId;
    public String session;
    public String _object;
    public String point;
    public String type;
    public String code;

    private BCRequest() {
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public Integer getCompanyId() {
        return companyId;
    }

    @Override
    public String getObject() {
        return _object;
    }

    @Override
    public String getPoint() {
        return point;
    }

    @Override
    public String getSession() {
        return session;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getType() {
        return type;
    }
}
