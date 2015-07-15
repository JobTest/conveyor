package com.pb.biser.conveyor.junit;

import com.pb.biser.conveyor.entity.ConveyorSettings;
import com.pb.biser.conveyor.entity.ConveyorSettingsI;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by diver on 5/15/15.
 */
public class CeonveyorSettingsGetDefaultTest {

    @Test
    public void shouldSuccessfullyReadDefaultConveyorSettings() throws IOException {

        ConveyorSettingsI conveyorSettings = ConveyorSettings.getDefault();

        Assert.assertEquals("https://cp.privatbank.ua:443/api/1/json", conveyorSettings.getUrl());
        Assert.assertEquals("meVXKlCOjX5uIrT5bIV7AKzkdZzuPoGlN6MLeo23bGdtzZ37H1", conveyorSettings.getApiKey());
        Assert.assertEquals("4649", conveyorSettings.getApiLogin());
        Assert.assertEquals("18111", conveyorSettings.getConveyorId());
        Assert.assertEquals(10, conveyorSettings.getMaxCount());
        Assert.assertEquals(1000, conveyorSettings.getReadTimeout());
        Assert.assertEquals(1001, conveyorSettings.getConnectTimeout());
    }
}
