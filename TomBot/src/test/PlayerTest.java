import BattleshipsExamplePlayer.F632msPa4lrn;
import BattleshipsInterface.ICoordinate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by TEE on 10/09/2016.
 */
public class PlayerTest {
    @Test
    public void test2Plus2()
    {
        assertEquals(2+2, 4);
    }

    @Test
    public void testCanGetShips()
    {
        for (int i = 0; i < 100; i++) {
            F632msPa4lrn player = new F632msPa4lrn();
            assertEquals(player.GetShipPositions().iterator().hasNext(), true);
        }
    }

    @Test
    public void testCanFire()
    {
        F632msPa4lrn player = new F632msPa4lrn();
        player.GetShipPositions();
        ICoordinate coordinate = player.SelectTarget();
        assertNotNull(coordinate);
    }
//
//    @Test
//    public void testBucketPredictor()
//    {
//        List<Pair<Integer, Double>> pairs = new ArrayList<>();
//        pairs.add(new Pair<>(1, 2.0));
//        pairs.add(new Pair<>(2, 3.0));
//        pairs.add(new Pair<>(3, 4.0));
//        pairs.add(new Pair<>(4, 5.0));
//        pairs.add(new Pair<>(5, 6.0));
//        pairs.add(new Pair<>(6, 7.0));
//        pairs.add(new Pair<>(7, 8.0));
//        pairs.add(new Pair<>(8, 9.0));
//        pairs.add(new Pair<>(9, 10.0));
//        pairs.add(new Pair<>(10, 11.0));
//        pairs.add(new Pair<>(11, 12.0));
//        pairs.add(new Pair<>(12, 13.0));
//        pairs.add(new Pair<>(13, 14.0));
//        BucketPredictor player = new BucketPredictor(pairs, 5, Mockito(Logger.class));
//        double output = player.getScore(12);
//        double out2 = player.getScore(7);
//        assertNotNull(out2);
//
//    }
}
