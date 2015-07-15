package com.pb.biser.conveyor;

import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Менеджер взаимодействия с ембедед базой
 * Created by alex on 5/8/15.
 */
public class EmbeddedBaseManager {

    private static final String INIT_SQL =
            //создание схемы
            "CREATE SCHEMA conveyor;" +
                    //создание таблицы состояний
                    "CREATE TABLE IF NOT EXISTS conveyor.state (\n" +
                    "  id   SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  name VARCHAR(100)       NOT NULL\n" +
                    ");" +
                    //создание таблицы типов
                    "CREATE TABLE IF NOT EXISTS conveyor.type (\n" +
                    "  id   SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  name VARCHAR(100)       NOT NULL\n" +
                    ");" +
                    //создание таблицы опций
                    "CREATE TABLE IF NOT EXISTS conveyor.options (\n" +
                    "  id    SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  name  VARCHAR(100)       NOT NULL,\n" +
                    "  value INTEGER            NOT NULL\n" +
                    ");" +
                    //создание таблицы записей
                    "CREATE TABLE IF NOT EXISTS conveyor.records (\n" +
                    "  id         SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  company_id INTEGER            NOT NULL,\n" +
                    "  biser_code VARCHAR(50)        NOT NULL,\n" +
                    "  api_code   VARCHAR(50)        NOT NULL,\n" +
                    "  point      INTEGER            NOT NULL,\n" +
                    "  sid        VARCHAR(50)        NOT NULL,\n" +
                    "  parameters TEXT               NOT NULL,\n" +
                    "  api_pack   BYTEA,\n" +
                    "  biser_pack BYTEA,\n" +
                    "  type       INTEGER            NOT NULL,\n" +
                    "  state      INTEGER            NOT NULL\n" +
                    ");" +
                    //создание процедуры получения граничного значения количеста запросов
                    "CREATE OR REPLACE FUNCTION conveyor.getLimit()\n" +
                    "  RETURNS INTEGER AS $LIMIT$\n" +
                    "SELECT value\n" +
                    "FROM conveyor.options\n" +
                    "WHERE name = 'request_count'\n" +
                    "ORDER BY name DESC\n" +
                    "LIMIT 1\n" +
                    "$LIMIT$\n" +
                    "LANGUAGE 'sql';" +
                    //создание процедуры удаления данных получателя
                    "CREATE OR REPLACE FUNCTION conveyor.kill(_company_id INTEGER)\n" +
                    "  RETURNS VOID AS $KILLER$\n" +
                    "BEGIN\n" +
                    "  IF ((SELECT COUNT(*)\n" +
                    "       FROM conveyor.records\n" +
                    "       WHERE _company_id = company_id) = 0)\n" +
                    "  THEN\n" +
                    "    RAISE EXCEPTION 'NOT VALID COMPANY_ID';\n" +
                    "  END IF;\n" +
                    "  DELETE FROM conveyor.records\n" +
                    "  WHERE company_id = _company_id;\n" +
                    "END;\n" +
                    "$KILLER$\n" +
                    "LANGUAGE 'plpgsql';" +
                    //создание процедуры проверки активности получателя
                    "CREATE OR REPLACE FUNCTION conveyor.isActive(_company_id INTEGER)\n" +
                    "  RETURNS BOOLEAN AS\n" +
                    "  $ACTIVE$\n" +
                    "  BEGIN\n" +
                    "    RETURN (CASE WHEN ((SELECT COUNT(*)\n" +
                    "                        FROM conveyor.records\n" +
                    "                        WHERE _company_id = company_id) < (conveyor.getLimit()) OR\n" +
                    "                       ((SELECT COUNT(*)\n" +
                    "                         FROM conveyor.records\n" +
                    "                         WHERE _company_id = company_id AND (state = 3 OR state = 4) AND\n" +
                    "                               api_code = biser_code) > (conveyor.getLimit())\n" +
                    "                        AND (SELECT COUNT(*)\n" +
                    "                             FROM conveyor.records\n" +
                    "                             WHERE _company_id = company_id AND state != 3 AND state != 4) = 0))\n" +
                    "      THEN TRUE\n" +
                    "            ELSE FALSE END);\n" +
                    "  END;\n" +
                    "  $ACTIVE$\n" +
                    "LANGUAGE 'plpgsql';" +
                    //сщздание процедуры получения валидных получателей
                    "CREATE OR REPLACE FUNCTION conveyor.getValid()\n" +
                    "  RETURNS SETOF INTEGER AS\n" +
                    "  $BODY$\n" +
                    "  SELECT DISTINCT company_id\n" +
                    "  FROM conveyor.records\n" +
                    "  WHERE conveyor.isActive(company_id);\n" +
                    "  $BODY$\n" +
                    "LANGUAGE 'sql';" +
                    //сщздание процедуры получения невалидных получателей
                    "CREATE OR REPLACE FUNCTION conveyor.getInvalid()\n" +
                    "  RETURNS SETOF INTEGER AS\n" +
                    "  $BODY$\n" +
                    "  SELECT DISTINCT company_id\n" +
                    "  FROM conveyor.records\n" +
                    "  WHERE NOT conveyor.isActive(company_id);\n" +
                    "  $BODY$\n" +
                    "LANGUAGE 'sql';" +
                    //создание процедуры добавления новых записей
                    "CREATE OR REPLACE FUNCTION conveyor.put(_company_id INTEGER,\n" +
                    "                                        _biser_code VARCHAR(50),\n" +
                    "                                        _api_code   VARCHAR(50),\n" +
                    "                                        _point      INTEGER,\n" +
                    "                                        _sid        VARCHAR(50),\n" +
                    "                                        _parameters TEXT,\n" +
                    "                                        _api_pack   BYTEA,\n" +
                    "                                        _biser_pack BYTEA,\n" +
                    "                                        _type       INTEGER,\n" +
                    "                                        _state      INTEGER)\n" +
                    "  RETURNS VOID AS $BODY$\n" +
                    "BEGIN\n" +
                    "  INSERT INTO conveyor.records (company_id, biser_code, api_code, point, sid, parameters, api_pack, biser_pack, type, state)\n" +
                    "  VALUES (_company_id, _biser_code, _api_code, _point, _sid, _parameters, _api_pack, _biser_pack, _type, _state);\n" +
                    "END;\n" +
                    "$BODY$\n" +
                    "LANGUAGE 'plpgsql';" +
                    //Инициализация данных
                    "INSERT INTO conveyor.state (name) VALUES ('Некорректные данные передаваемые от ПК API2.X');" +    //1
                    "INSERT INTO conveyor.state (name) VALUES ('Не совпал код ошибки от ПК API2.X и ПК BISER');" +     //2
                    "INSERT INTO conveyor.state (name) VALUES ('Совпал код ошибки от ПК API2.X и ПК BISER');" +        //3
                    "INSERT INTO conveyor.state (name) VALUES ('Совпал ответ от ПК API2.X и ПК BISER');" +             //4
                    "INSERT INTO conveyor.state (name) VALUES ('Не совпал ответ от ПК API2.X и ПК BISER');" +          //5
                    "INSERT INTO conveyor.state (name) VALUES ('Успешный ответ от ПК API2.X и ошибка от ПК BISER');" + //6
                    "INSERT INTO conveyor.state (name) VALUES ('Ошибка от ПК API2.X и успешный ответ от ПК BISER');" + //7
                    "INSERT INTO conveyor.type (name) VALUES ('search');" +
                    "INSERT INTO conveyor.type (name) VALUES ('presearch');" +
                    "INSERT INTO conveyor.options (name, value) VALUES ('request_count', 3);" +
                    //Company with tests less than max limit
                    "SELECT conveyor.put(1, '3', '3', 1, '1', '1', 'test', 'test', 1, 3);" +
                    //Company with tests where error codes of two systems is the same
                    "SELECT conveyor.put(4, '3', '3', 1, '1', '1', 'test', 'test', 1, 3);" +
                    "SELECT conveyor.put(4, '3', '3', 1, '1', '1', 'test', 'test', 1, 3);" +
                    "SELECT conveyor.put(4, '3', '3', 1, '1', '1', 'test', 'test', 1, 3);" +
                    //Company with tests where error codes of two systems is different
                    "SELECT conveyor.put(3, '3', '3', 1, '1', '1', 'test', 'test', 1, 2);" +
                    "SELECT conveyor.put(3, '3', '3', 1, '1', '1', 'test', 'test', 1, 2);" +
                    "SELECT conveyor.put(3, '3', '3', 1, '1', '1', 'test', 'test', 1, 2);" +
                    //Company with tests where answers of two system is different
                    "SELECT conveyor.put(5, '3', '3', 1, '1', '1', 'test', 'test', 1, 6);" +
                    "SELECT conveyor.put(5, '3', '3', 1, '1', '1', 'test', 'test', 1, 6);" +
                    "SELECT conveyor.put(5, '3', '3', 1, '1', '1', 'test', 'test', 1, 6);" +
                    "SELECT conveyor.put(6, '3', '3', 1, '1', '1', 'test', 'test', 1, 7);" +
                    "SELECT conveyor.put(6, '3', '3', 1, '1', '1', 'test', 'test', 1, 7);" +
                    "SELECT conveyor.put(6, '3', '3', 1, '1', '1', 'test', 'test', 1, 7);" +
                    //Company with tests where packs of two systems is the same
                    "SELECT conveyor.put(2, '3', '3', 1, '1', '1', 'test', 'test', 1, 5);" +
                    "SELECT conveyor.put(2, '3', '3', 1, '1', '1', 'test', 'test', 1, 5);" +
                    "SELECT conveyor.put(2, '3', '3', 1, '1', '1', 'test', 'test', 1, 5);";



    private static final String DROP_SQL = "DROP SCHEMA IF EXISTS conveyor CASCADE";

    private EmbeddedPostgreSQL postgreSQL;

    /**
     * Метод открытия базы
     *
     * @throws IOException
     */
    public void startDatabase() throws IOException, SQLException {
        postgreSQL = EmbeddedPostgreSQL.start();
        execute(INIT_SQL);
    }

    /**
     * Метод очистки базы
     *
     * @throws IOException
     */
    public void dropDatabase() throws IOException, SQLException {
        execute(DROP_SQL);
    }

    /**
     * Метод очистки базы
     *
     * @throws IOException
     */
    public void fillDatabase() throws IOException, SQLException {
        execute(INIT_SQL);
    }



    /**
     * Метод закрытия базы
     *
     * @throws IOException
     */
    public void closeDatabase() throws IOException {
        if (postgreSQL != null) {
            postgreSQL.close();
        }
    }

    private void execute(String query) throws IOException, SQLException {
        if (postgreSQL == null) {
            startDatabase();
        }
        try (Connection c = postgreSQL.getPostgresDatabase().getConnection()) {
            try (Statement statement = c.createStatement()) {
                statement.execute(query);
            }
        }
    }

    /**
     * Метод получения источника данных
     *
     * @return источник данных
     * @throws IOException
     * @throws SQLException
     */
    public DataSource getDataSource() throws IOException, SQLException {
        if (postgreSQL == null) {
            startDatabase();
        }
        return postgreSQL.getPostgresDatabase();
    }

}
