package com.pb.biser.conveyor.dao;

import com.pb.biser.conveyor.NullSafe;
import com.pb.biser.conveyor.controller.entity.Type;
import com.pb.biser.conveyor.dao.entity.Diff;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Bondarenko
 *         Date: 6/11/15
 *         Time: 2:06 PM
 */
public class DataAccessManager implements DataAccessManagerI {
    public static final String LOGGER = DataAccessManager.class.getName();

    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Set<Integer> getValidTestCompanies() {
        return callReadProcedure("{call conveyor.getvalid()}");
    }

    @Override
    public Set<Integer> getInvalidTestCompanies() {
        return callReadProcedure("{call conveyor.getinvalid()}");
    }

    private Set<Integer> callReadProcedure(String storedProc) {
        try {
            return template.execute(con -> {
                return con.prepareCall(storedProc);
            }, (CallableStatement cs) -> {
                cs.execute();
                Set<Integer> strings = new HashSet<>();
                ResultSet resultSet = cs.getResultSet();
                while (resultSet.next()) {
                    strings.add(resultSet.getInt(1));
                }
                resultSet.close();
                return strings;
            });
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(LOGGER).error(storedProc + " failed!", e);
            throw e;
        }
    }

    @Override
    public void putTestData(final SearchRequestAttributes searchRequestAttributes) {
        if (searchRequestAttributes == null) {
            throw new NullPointerException("Search request attributes not specified");
        }
        String storedProc = "{call conveyor.put(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try {
            template.execute((CallableStatementCreator) con -> {
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, NullSafe.toInt(searchRequestAttributes.getCompanyId()));
                if (searchRequestAttributes.getDiff() != null) {
                    Diff diff = searchRequestAttributes.getDiff();
                    cs.setString(2, diff.getBiserCode());
                    cs.setString(3, diff.getApi2xCode());
                    cs.setBytes(7, NullSafe.toBytes(diff.getApi2xPack()));
                    cs.setBytes(8, NullSafe.toBytes(diff.getBiserPack()));
                }
                cs.setInt(4, NullSafe.toInt(searchRequestAttributes.getPoint()));
                cs.setString(5, searchRequestAttributes.getSession());
                cs.setString(6, searchRequestAttributes.getParameters().toString());
                if (Type.SEARCH.equals(searchRequestAttributes.getType())) {
                    cs.setInt(9, 1);
                } else if (Type.PRESEARCH.equals(searchRequestAttributes.getType())) {
                    cs.setInt(9, 2);
                } else {
                    Integer nullInt = null;
                    cs.setInt(9, nullInt);
                }
                cs.setInt(10, searchRequestAttributes.getStatus());

                return cs;
            }, PreparedStatement::execute);
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(LOGGER).error(storedProc + " failed!", e);
            throw e;
        }
    }

    @Override
    public boolean isActiveCompany(Long companyId) {
        String storedProc = "{call conveyor.isactive(?)}";
        try {
            return template.execute(con -> {
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, NullSafe.toInt(companyId));
                return cs;
            }, (CallableStatement cs) -> {
                Boolean result = false;
                cs.execute();
                ResultSet resultSet = cs.getResultSet();
                while (resultSet.next()) {
                    result = resultSet.getBoolean(1);
                }
                resultSet.close();
                return result;
            });
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(LOGGER).error(storedProc + " failed!", e);
            throw e;
        }
    }

    @Override
    public Integer getLimit() {
        String storedProc = "{call conveyor.getlimit()}";
        try {
            return template.execute(con -> {
                return con.prepareCall(storedProc);
            }, (CallableStatement cs) -> {
                cs.execute();
                return cs.getInt(1);
            });
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(LOGGER).error(storedProc + " failed!", e);
            throw e;
        }
    }

    @Override
    public void kill(Long companyId) {
        String storedProc = "{call conveyor.kill(?)}";
        try {
            template.execute((CallableStatementCreator) con -> {
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, NullSafe.toInt(companyId));
                return cs;
            }, PreparedStatement::execute);

        } catch (DataAccessException e) {
            LoggerFactory.getLogger(LOGGER).error(storedProc + " failed!", e);
            throw e;
        }
    }
}
