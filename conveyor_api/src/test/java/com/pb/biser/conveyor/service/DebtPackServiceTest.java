package com.pb.biser.conveyor.service;

import com.pb.bpln.cashier.core.debt.search.DebtPackI;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Alexander Bondarenko
 *         Date: 5/18/15
 *         Time: 3:12 PM
 */
public class DebtPackServiceTest {

    private DebtPackService packService;

    @BeforeTest
    public void setUp() throws Exception {
        packService = new DebtPackService();
    }

    @Test
    public void shouldSuccessfullySerializeJavaObject() throws Exception {
        assertEquals("H4sIAAAAAAAAAFvzloG1hIHVw9XHxx8A6wmu3wwAAAA=", packService.serialize("HELLO"));
    }

    @Test
    public void shouldSuccessfullyDeserializeJavaObject() throws Exception {
        assertEquals("HELLO", packService.deserialize("H4sIAAAAAAAAAFvzloG1hIHVw9XHxx8A6wmu3wwAAAA="));
    }

    @Test
    public void shouldSuccessfullySerializeAndDeserializeDebtPack() throws Exception {
        DebtPackI debtPack = packService.<DebtPackI>deserialize(SERIALIZED_DEBT_PACK);
        assertEquals(SERIALIZED_DEBT_PACK, packService.<DebtPackI>serialize(debtPack));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionBecauseOfDeserializationError() throws Exception {
        packService.deserialize(TEST);
    }

    private static final String TEST = "test";

    public static final String SERIALIZED_DEBT_PACK = "H4sIAAAAAAAAAJVWa2wUVRS+u9vtiyLlIVpQM8FHIZHZ7Yu2IYLb7jYubh/utCS" +
            "QmOV29tKOnZ2Zztwtsz9EHmKIYpoQraCAiAnGoEnlEbGlbYyJCUTjXRJ/1D8m/WFiDPjTaDB67t3tdrGKZdKZ3nvuOeee13fOfnIL+R0b" +
            "bVbNlGz1y/2WbsgqdgY1YsuqaRM5Sfqp7BBsq4NyGNY9WB1681zfE7dfx494kTeGlhGX2rgH2zjlUFQdewmP4ECaanogpjl0awyVO8Qe0" +
            "VTiDKP9yOPa6Mm7LuOXWFxaFjraTYNizSD2rO/TW1vWuee9yBNDFYIjjCmmaEMM5ANWf4DLB7h8QJwGeuZ5troW+CTd6xrOpox6+rwbZy" +
            "eKbnA6sUXR6iInnoNgADGnciWny5wu5+n+sh++/GrtnqwPeTtQpW7iZAdWqWlHUQUdtIkzaOpJ19r+LOJP1b5y+FbD66Woikc2kSKOgwc" +
            "I9dSwU9kxNgXvNTbOJrPHYH1Yyh7MHmbX+GKUTcA/NsWusGn2WfYIMF6G3TSwsBk2ycazh9hV/kpAn8yOSqBlgu/ZRTh7LTuWfUdiF0Hf" +
            "WPaIxD4H2iicTIK6KZAD0nEQuMbFZ9g06JyEiy+BBL9yHMQOwX6GXyjMgc8YEEe5YqlS4govLVgEksB3GZTNvTomtbYG61qam7kvh+AOo" +
            "UJolepa5WCTXNdUfHSRe5E9JjUGJVB5gE3JFFWIUNGMRfbbaENxWnVzYEAzBkRlhm1thNi9wIXyzyqEoNweEEnTMbBFjHSq+BByXdrdFY" +
            "t2RVzI7pqF7MY0Y4gkeQFXbVJe3LHn6AYfZ99XAoKee9RWDiGwCn1z8OWdsz8Pe1HJblRpEdsBXcSgMeTHuoYBKatyRcbtCijUBi84VlI" +
            "GSZmGpnKsrAHmEaynidi4loeCpuR8yVicuswVqFpZMKzxvoCs5JA5MFdV/t0rx2Uv8u1Gy1VsqERX0ql4Wicx9JDmxImKdbWTUHAD6F2E" +
            "JEmyABPhQZtp6gQb3AVdxC6azFlto033NonbkrfjZkXn8jn/7A0ByOK+kusc/OsVa7/4ludh9bCAlcShBbXyfEhREsquzjZIbmXa0IbTJ" +
            "DFEMhRJ4YZwMNgQaozUNwU7GoINraH2+taGlrbWjpZw45amMEUr8q2qEGOKfEpfJ8R17aKEd5oGyWz+9c8V3p+m74DFO/LJcq35CgNjQC" +
            "qlOZppgKbl88otnCEEdNb+f2B6OOvM8fGS2nO/Pe6FEvyvGCy0Fh+vEi6WUM0koaikvr6hoUAzhywTbClr7+7sCXXtslyKHlhwmicYDGt" +
            "echGJmii07I4dX5954eOmbUsztJSilbkrE5ZNRhJ7NZckC01W4DVfVd9K9oHvT/1xG6K8eyHK4JNp6JlEkugU8yu2AKjyClUzbdCcxvxJ" +
            "Tf5ESyawkfwXhuo8QwqOMXTwTJ5emg/LAs6QWxw1wLZmcrvXLSoRGDIkDn6Q3w/e3PWgVXJWDEx/P4GeRdGji+dYj9DFxQBIPmII27aL0" +
            "bN+kfYi5tW11zPNN7KA3yjyJXEmivzQRehgFJVkIFmu9Rc8YLgfobIfhSehYgoMo3lveILv6/dA1NhrXr0e76qd27h/yXkvVQexLeBV1o" +
            "913m9gWaVDW+TgSDjpFOwrbALYyYOnwkn3O1pSIw7v2bZoSLx4q0Lt7d19Xb2JcKg39E+Q2UsHmb2t6qPtRy88U7Y0H0qgV7RFY7FENBz" +
            "p6o12RCNx8CU/6WAVCofjEUWh6DH2PnubnZXZKXaGvQfvW7AdY6fZBXbyaameQkEoxZK+jmg3RevZB+xDdhbYj7MT7Dx7F74nJXZahj+3" +
            "KFs5hHvqQIMSie+MtkcoqoNRekUM9oncsBY/KiZyPwLYF3x314xnVylalivihGMRFWLWsqTs5+pPARFtr6ZiCom688aJ6aeyiV+gEKGBQ" +
            "yHGyXBas3MYq4HSLwAONhV3Ia3GteCBdhWOKL084QZMmUQuzUXY84n1ahiF1Q4gOSVgTE2ebt6sg3KwwFAODAIFcOBpKpDLgMxBwe+qD9" +
            "Y1uciy3L8BIOsDPwcLAAA=";

}