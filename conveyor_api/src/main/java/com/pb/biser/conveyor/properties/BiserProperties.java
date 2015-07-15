package com.pb.biser.conveyor.properties;

import com.pb.bpln.cashier.core.db.storage.objects.PointTypeDictionaryI;
import com.pb.bpln.cashier.core.global_access.GlobalOptionsAccessI;
import com.pb.bpln.cashier.core.global_access.db.DefaultTemplatesValuesI;
import com.pb.bpln.core.Bank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by diver on 5/11/15.
 */
@Component
@PropertySource("classpath:biser.properties")
public class BiserProperties implements GlobalOptionsAccessI {

    @Value("${url}")
    private String url;

    @Override
    public List<String> getDebtServerURL() {
        return Collections.singletonList(url);
    }

    @Override
    public List<String> getBiserUrl() {
        return Collections.singletonList(url);
    }

    @Override
    public Integer getTemplatesServerTimeoutMS() {
        return null;
    }

    @Override
    public Integer getDopComissServerTimeoutMS() {
        return null;
    }

    @Override
    public String getLdap() {
        return null;
    }

    @Override
    public String getBranch() {
        return null;
    }

    @Override
    public Integer getCurrencyCode() {
        return null;
    }

    @Override
    public String getBaseCurrency() {
        return null;
    }

    @Override
    public String getDopCommissServerAddress() {
        return null;
    }

    @Override
    public String getTemplatesServletUrl() {
        return null;
    }

    @Override
    public String getCentsText() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public String getCurrency() {
        return null;
    }

    @Override
    public Boolean isDbTest() {
        return null;
    }

    @Override
    public Integer getFreePaySumLimit() {
        return null;
    }

    @Override
    public Double getUndefClientFreePaySumLimit() {
        return null;
    }

    @Override
    public Integer getHalfAuthorizationSumLimit() {
        return null;
    }

    @Override
    public String getMonetaryUnitText() {
        return null;
    }

    @Override
    public Long getNDS() {
        return null;
    }

    @Override
    public Boolean isSavePaysForTemplates() {
        return null;
    }

    @Override
    public String getCommissServerAddresses() {
        return null;
    }

    @Override
    public Integer getCommissionServerTimeout() {
        return null;
    }

    @Override
    public Integer getDebtServerTimeout() {
        return 90000;
    }

    @Override
    public Integer getConnectionTimeout() {
        return 90000;
    }

    @Override
    public String getApiPortalAddress() {
        return null;
    }

    @Override
    public Integer getApiPortalTimeout() {
        return null;
    }

    @Override
    public String getCISAddress() {
        return null;
    }

    @Override
    public String getCISIdentificationAddress() {
        return null;
    }

    @Override
    public Integer getCISTimeout() {
        return null;
    }

    @Override
    public String getChameleonAddress() {
        return null;
    }

    @Override
    public Integer getChameleonTimeout() {
        return null;
    }

    @Override
    public String getOuterAccountServiceAddress() {
        return null;
    }

    @Override
    public Integer getOuterAccountServiceTimeout() {
        return null;
    }

    @Override
    public Boolean isDisableStornoAlgorithm() {
        return null;
    }

    @Override
    public Boolean isFeelanceSkpModeEnable() {
        return null;
    }

    @Override
    public Integer getMaxStornoSum() {
        return null;
    }

    @Override
    public Integer getMinStornoSum() {
        return null;
    }

    @Override
    public Integer getTemplates() {
        return null;
    }

    @Override
    public String getTimeZone() {
        return null;
    }

    @Override
    public Integer getStornoPhoneCountLimitEAS() {
        return null;
    }

    @Override
    public PointTypeDictionaryI getPointTypeDictionary() {
        return null;
    }

    @Override
    public Set<Locale> getAvailableLocales() {
        return null;
    }

    @Override
    public Set<Bank> getAvailableBanks() {
        return null;
    }

    @Override
    public DefaultTemplatesValuesI getDefaultTemplatesValues() {
        return null;
    }

    @Override
    public Integer getPaymentLifetime() {
        return null;
    }

    @Override
    public String getDebtRedisDataUrl() {
        return null;
    }

    @Override
    public Integer getDebtRedisKeyExpTime() {
        return null;
    }

    @Override
    public Boolean isDebtUseSearchCache() {
        return Boolean.FALSE;
    }

    @Override
    public Set<Long> getDebtCacheExcludedProviders() {
        return null;
    }

    @Override
    public Integer getDebtRedisServerTimeout() {
        return null;
    }

    @Override
    public Integer getDebtRedisDb() {
        return null;
    }

    @Override
    public Integer getDBFNumberOfRows() {
        return null;
    }

    @Override
    public Integer getPayedRequestMaxRowCount() {
        return null;
    }

    @Override
    public Integer getStornoReasonMinLength() {
        return null;
    }

    @Override
    public Integer getDebtServerSearchTimeout() {
        return 90000;
    }

    @Override
    public Integer getDebtServerCheckTimeout() {
        return null;
    }

    @Override
    public Integer getDebtServerPayTimeout() {
        return null;
    }

    @Override
    public Integer getDebtServerSearchTimeoutAvto() {
        return null;
    }

    @Override
    public Integer getDebtServerCheckTimeoutAvto() {
        return null;
    }

    @Override
    public Integer getDebtServerPayTimeoutAvto() {
        return null;
    }

    @Override
    public Integer getDebtServerTimeoutAvto() {
        return null;
    }

    @Override
    public Boolean isDebtBlockActiveMQ() {
        return Boolean.TRUE;
    }

    @Override
    public String getServiceSearchAccInODBUrl() {
        return null;
    }

    @Override
    public Integer getMQTimeDelayInMS() {
        return null;
    }

    @Override
    public String getExternalErrorHolderURL() {
        return null;
    }

    @Override
    public String getMqServerAddress() {
        return null;
    }

    @Override
    public String getMqExchange() {
        return null;
    }

    @Override
    public String getMqRoutingKey() {
        return null;
    }

    @Override
    public Boolean isAPIBlockRabbitMQ() {
        return null;
    }

    @Override
    public Boolean isBiserSearchUsed() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean isBiserPresearchUsed() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean isBiserCheckUsed() {
        return Boolean.FALSE;
    }

    @Override
    public Set<String> getSetOfAllowedCompanieForBiserSearch() {
        return null;
    }

    @Override
    public Set<String> getSetOfAllowedCompanieForBiserPresearch() {
        return null;
    }

    @Override
    public Set<String> getSetOfAllowedCompanieForBiserCheck() {
        return null;
    }

    @Override
    public Set<String> getSetOfAllowedCompanieForBiserPay() {
        return null;
    }

    @Override
    public Set<String> getSetOfAllowedCompanieForBiserAsyncTest() {
        return null;
    }

    @Override
    public String getDBFSeparators() {
        return null;
    }

    @Override
    public Set<String> getDbfExclusionsDictionary() {
        return null;
    }

    @Override
    public String getFurbyUrl() {
        return null;
    }

    @Override
    public Integer getFurbyTimeout() {
        return null;
    }

    @Override
    public Integer getSessionCheckingType() {
        return null;
    }

    @Override
    public Integer getPrintReceiptSystemId() {
        return null;
    }
}
