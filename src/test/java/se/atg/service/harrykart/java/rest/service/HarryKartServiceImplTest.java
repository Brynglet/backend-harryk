package se.atg.service.harrykart.java.rest.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.atg.service.harrykart.java.rest.pojo.HarryResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.atg.service.harrykart.java.rest.utility.CommonConstants.NR_OF_MEDAL_FINISHERS;

@RunWith(SpringJUnit4ClassRunner.class)
public class HarryKartServiceImplTest {

    private String inputXMLOk;
    private String inputXMLBadSpeed;

    @InjectMocks
    private HarryKartServiceImpl harryKartServiceImpl;

    @Before
    public void before() {

        /* As input_2.xml */

        inputXMLOk = "<harryKart>\n" +
                "    <numberOfLoops>3</numberOfLoops>\n" +
                "    <startList>\n" +
                "        <participant>\n" +
                "            <lane>1</lane>\n" +
                "            <name>TIMETOBELUCKY</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "        <participant>\n" +
                "            <lane>2</lane>\n" +
                "            <name>CARGO DOOR</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "        <participant>\n" +
                "            <lane>3</lane>\n" +
                "            <name>HERCULES BOKO</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "        <participant>\n" +
                "            <lane>4</lane>\n" +
                "            <name>WAIKIKI SILVIO</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "    </startList>\n" +
                "    <powerUps>\n" +
                "        <loop number=\"1\">\n" +
                "            <lane number=\"1\">6</lane>\n" +
                "            <lane number=\"2\">10</lane>\n" +
                "            <lane number=\"3\">4</lane>\n" +
                "            <lane number=\"4\">0</lane>\n" +
                "        </loop>\n" +
                "        <loop number=\"2\">\n" +
                "            <lane number=\"1\">0</lane>\n" +
                "            <lane number=\"2\">-10</lane>\n" +
                "            <lane number=\"3\">5</lane>\n" +
                "            <lane number=\"4\">15</lane>\n" +
                "        </loop>\n" +
                "    </powerUps>\n" +
                "</harryKart>\n";

        /* As input_2.xml but speed will be 0 for one horse */
        inputXMLBadSpeed = "<harryKart>\n" +
                "    <numberOfLoops>3</numberOfLoops>\n" +
                "    <startList>\n" +
                "        <participant>\n" +
                "            <lane>1</lane>\n" +
                "            <name>TIMETOBELUCKY</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "        <participant>\n" +
                "            <lane>2</lane>\n" +
                "            <name>CARGO DOOR</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "        <participant>\n" +
                "            <lane>3</lane>\n" +
                "            <name>HERCULES BOKO</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "        <participant>\n" +
                "            <lane>4</lane>\n" +
                "            <name>WAIKIKI SILVIO</name>\n" +
                "            <baseSpeed>10</baseSpeed>\n" +
                "        </participant>\n" +
                "    </startList>\n" +
                "    <powerUps>\n" +
                "        <loop number=\"1\">\n" +
                "            <lane number=\"1\">6</lane>\n" +
                "            <lane number=\"2\">10</lane>\n" +
                "            <lane number=\"3\">4</lane>\n" +
                "            <lane number=\"4\">0</lane>\n" +
                "        </loop>\n" +
                "        <loop number=\"2\">\n" +
                "            <lane number=\"1\">0</lane>\n" +
                "            <lane number=\"2\">-20</lane>\n" + // -> gets speed=0
                "            <lane number=\"3\">5</lane>\n" +
                "            <lane number=\"4\">15</lane>\n" +
                "        </loop>\n" +
                "    </powerUps>\n" +
                "</harryKart>\n";
    }

    @Test
    public void verifyCalculationsOK() {

        HarryResponse actual = harryKartServiceImpl
                .getResponse(inputXMLOk);

        assertTrue(NR_OF_MEDAL_FINISHERS == actual.getRanking().size());

        /*
        TIMETOBELUCKY 10 16 16 (1000/10 + 1000/16 + 1000/16): 225
        CARGO DOOR    10 20 10 (1000/10 + 1000/20 + 1000/10): 250
        HERCULES BOKO 10 14 19 (1000/10 + 1000/14 + 1000/19): 224.060150...
        WAIKIKI SILVIO 10 10 25 (1000/10 + 1000/10 + 1000/25): 240
        */

        assertTrue("HERCULES BOKO".equals(actual.getRanking().get(0).getHorse()));
        assertTrue("TIMETOBELUCKY".equals(actual.getRanking().get(1).getHorse()));
        assertTrue("WAIKIKI SILVIO".equals(actual.getRanking().get(2).getHorse()));

        Assertions.assertEquals((1000/10.0) + (1000/14.0) + (1000/19.0),
                actual.getRanking().get(0).getTotalTime());
        Assertions.assertEquals(Double.valueOf(225), actual.getRanking().get(1).getTotalTime());
        Assertions.assertEquals(Double.valueOf(240), actual.getRanking().get(2).getTotalTime());
    }

    @Test(expected = Exception.class)
    public void verifyBadSpeedThrowsException() {
        try {
            HarryResponse actual = harryKartServiceImpl
                    .getResponse(inputXMLBadSpeed);
        } catch (Exception e) {
            assertEquals("getLapSpeed exception lapSpeed <= 0 :0.0", e.getMessage());
            throw e;
        }
    }
}