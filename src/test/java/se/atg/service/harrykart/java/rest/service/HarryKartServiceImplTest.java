package se.atg.service.harrykart.java.rest.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.atg.service.harrykart.java.rest.pojo.HarryResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        inputXMLOk = """
                <harryKart>
                    <numberOfLoops>3</numberOfLoops>
                    <startList>
                        <participant>
                            <lane>1</lane>
                            <name>TIMETOBELUCKY</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                        <participant>
                            <lane>2</lane>
                            <name>CARGO DOOR</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                        <participant>
                            <lane>3</lane>
                            <name>HERCULES BOKO</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                        <participant>
                            <lane>4</lane>
                            <name>WAIKIKI SILVIO</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                    </startList>
                    <powerUps>
                        <loop number="1">
                            <lane number="1">6</lane>
                            <lane number="2">10</lane>
                            <lane number="3">4</lane>
                            <lane number="4">0</lane>
                        </loop>
                        <loop number="2">
                            <lane number="1">0</lane>
                            <lane number="2">-10</lane>
                            <lane number="3">5</lane>
                            <lane number="4">15</lane>
                        </loop>
                    </powerUps>
                </harryKart>
                """;

        /* As input_2.xml but speed will be 0 for one horse */

        inputXMLBadSpeed = """
                <harryKart>
                    <numberOfLoops>3</numberOfLoops>
                    <startList>
                        <participant>
                            <lane>1</lane>
                            <name>TIMETOBELUCKY</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                        <participant>
                            <lane>2</lane>
                            <name>CARGO DOOR</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                        <participant>
                            <lane>3</lane>
                            <name>HERCULES BOKO</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                        <participant>
                            <lane>4</lane>
                            <name>WAIKIKI SILVIO</name>
                            <baseSpeed>10</baseSpeed>
                        </participant>
                    </startList>
                    <powerUps>
                        <loop number="1">
                            <lane number="1">6</lane>
                            <lane number="2">10</lane>
                            <lane number="3">4</lane>
                            <lane number="4">0</lane>
                        </loop>
                        <loop number="2">
                            <lane number="1">0</lane>
                            <lane number="2">-20</lane>
                            <lane number="3">5</lane>
                            <lane number="4">15</lane>
                        </loop>
                    </powerUps>
                </harryKart>
                """;
    }

    @Test
    public void verifyCalculationsOK() {

        HarryResponse actual = harryKartServiceImpl
                .getResponse(inputXMLOk);

        assertEquals((int) NR_OF_MEDAL_FINISHERS, actual.getRanking().size());

        /*
        TIMETOBELUCKY 10 16 16 (1000/10 + 1000/16 + 1000/16): 225
        CARGO DOOR    10 20 10 (1000/10 + 1000/20 + 1000/10): 250
        HERCULES BOKO 10 14 19 (1000/10 + 1000/14 + 1000/19): 224.060150...
        WAIKIKI SILVIO 10 10 25 (1000/10 + 1000/10 + 1000/25): 240
        */

        assertEquals("HERCULES BOKO", actual.getRanking().get(0).getHorse());
        assertEquals("TIMETOBELUCKY", actual.getRanking().get(1).getHorse());
        assertEquals("WAIKIKI SILVIO", actual.getRanking().get(2).getHorse());

        Assertions.assertEquals((1000/10.0) + (1000/14.0) + (1000/19.0),
                actual.getRanking().get(0).getTotalTime());
        Assertions.assertEquals(Double.valueOf(225), actual.getRanking().get(1).getTotalTime());
        Assertions.assertEquals(Double.valueOf(240), actual.getRanking().get(2).getTotalTime());
    }

    @Test(expected = Exception.class)
    public void verifyBadSpeedThrowsException() {
        try {
            harryKartServiceImpl
                    .getResponse(inputXMLBadSpeed);
        } catch (RuntimeException e) {
            assertEquals("getLapSpeed exception lapSpeed <= 0 :0.0", e.getMessage());
            throw e;
        }
    }
}