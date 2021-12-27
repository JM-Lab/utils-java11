package kr.jm.utils.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JMDistanceTest {

    @Test
    public void calKilometer() {
        Assert.assertEquals("324.5353381690539", Double.toString(JMDistance.getInstance()
                .calKilometer(40.689202777778D, -74.044219444444D, 38.889069444444D, -77.034502777778D)));
        Assert.assertEquals("4331.991201001969", Double.toString(JMDistance.getInstance().calKilometer(33, 22, 0, 0)));
        Assert.assertEquals("200.16596953256945",
                Double.toString(JMDistance.getInstance().calKilometer(40.689202777778D, 30D, 38.889069444444D, 30D)));
        Assert.assertEquals("12757.79203219697",
                Double.toString(JMDistance.getInstance().calKilometer(40.689202777778D, 30D, -74.044219444444D, 30D)));
    }

    @Test
    public void nearestKeyDistanceByKmMap() {
        Map<Integer, Map.Entry<Double, Double>> latLongEntryMap = JMJson.getInstance().withJsonString(
                "{\"1\":{\"37.302765\":127.166985},\"2\":{\"37.640804\":126.90605},\"3\":{\"37.29686\":127.736374}," +
                        "\"4\":{\"37.879517\":127.79078},\"5\":{\"37.18825\":127.403336},\"6\":{\"34.802704\":126.9708}," +
                        "\"7\":{\"36.846172\":126.918625},\"8\":{\"35.281147\":128.89584},\"9\":{\"37.797787\":127.30332}," +
                        "\"10\":{\"37.385864\":127.181625},\"11\":{\"34.881073\":128.59062},\"12\":{\"35.869125\":129.25578}," +
                        "\"13\":{\"35.858707\":129.28163},\"14\":{\"36.323326\":127.24695},\"15\":{\"35.81448\":128.38254}," +
                        "\"16\":{\"35.681534\":128.2939}}",
                new TypeReference<>() {});

        List<Map.Entry<Integer, Double>> distanceByKmList =
                JMDistance.getInstance().nearestKeyDistanceByKmMap(latLongEntryMap, 37.1778, 127.178,
                        300, 0);
        Assert.assertEquals(16, distanceByKmList.size());
        Assert.assertEquals(
                "[1=13.92966293498367, 5=19.99632905081611, 10=23.137915937685857, 7=43.47610325069693, 3=51.172901853734864, 2=56.81123257843832, 9=69.82057481461139, 4=94.91131214872523, 14=95.2116784184609, 15=185.93757739705651, 16=194.0280964792945, 12=235.88574515389573, 13=238.42855085613874, 8=261.17751191746663, 6=264.75585388505567, 11=285.2229290775498]",
                distanceByKmList.toString());

        distanceByKmList =
                JMDistance.getInstance().nearestKeyDistanceByKmMap(latLongEntryMap, 37.1778, 127.178,
                        300, 6);
        Assert.assertEquals(6, distanceByKmList.size());
        Assert.assertEquals(
                "[1=13.92966293498367, 5=19.99632905081611, 10=23.137915937685857, 7=43.47610325069693, 3=51.172901853734864, 2=56.81123257843832]",
                distanceByKmList.toString());

        distanceByKmList =
                JMDistance.getInstance().nearestKeyDistanceByKmMap(latLongEntryMap, 37.1778, 127.178,
                        100, 0);
        Assert.assertEquals(9, distanceByKmList.size());
        Assert.assertEquals(
                "[1=13.92966293498367, 5=19.99632905081611, 10=23.137915937685857, 7=43.47610325069693, 3=51.172901853734864, 2=56.81123257843832, 9=69.82057481461139, 4=94.91131214872523, 14=95.2116784184609]",
                distanceByKmList.toString());

        distanceByKmList =
                JMDistance.getInstance().nearestKeyDistanceByKmMap(latLongEntryMap, 37.1778, 127.178,
                        100, 2);
        Assert.assertEquals(2, distanceByKmList.size());
        Assert.assertEquals(
                "[1=13.92966293498367, 5=19.99632905081611]",
                distanceByKmList.toString());
    }
}