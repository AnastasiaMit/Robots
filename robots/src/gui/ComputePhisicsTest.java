package gui;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArrayList;


public class ComputePhisicsTest extends TestCase {

    public void testDistance() throws Exception {
        assertEquals(0.0, ComputePhisics.distance(0, 0, 0, 0));
        assertEquals(1.0, ComputePhisics.distance(0, 0, 0, 1));
        assertEquals(5.0, ComputePhisics.distance(0, 3, 4, 0));
        assertEquals(1.0, ComputePhisics.distance(0, 1, 0, 0));
        assertEquals(5.0, ComputePhisics.distance(4, 0, 0, 3));
    }

    public void testAngleTo() throws Exception {
        assertEquals(0.0, ComputePhisics.angleTo(0, 0, 0, 0));
        BigDecimal fig = new BigDecimal(ComputePhisics.angleTo(3, 1, 6, 3));
        Double roundFig = fig.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(0.588, roundFig);

        BigDecimal figSec = new BigDecimal(ComputePhisics.angleTo(1, 8, 0, 0));
        Double roundFigSec = figSec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(4.588, roundFigSec);

        BigDecimal figTh = new BigDecimal(ComputePhisics.angleTo(-1, -8, 0, 0));
        Double roundFigTh = figTh.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(1.446, roundFigTh);


    }

    public void testApplyLimits() throws Exception {
        assertEquals(0.0, ComputePhisics.applyLimits(0, 0, 0));
        assertEquals(1.0, ComputePhisics.applyLimits(0, 1, 10));
        assertEquals(10.0, ComputePhisics.applyLimits(100, 1, 10));
        assertEquals(10.0, ComputePhisics.applyLimits(10, 0, 100));
        assertEquals(-10.0, ComputePhisics.applyLimits(-10, -100, 100));
        assertEquals(100.0, ComputePhisics.applyLimits(-10, 100, 100));
        assertEquals(0.0, ComputePhisics.applyLimits(-10, 0, 100));
    }

    public void testAsNormalizedRadians() throws Exception {
        BigDecimal figFir = new BigDecimal(ComputePhisics.asNormalizedRadians(-8));
        Double roundFigFir = figFir.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(4.566, roundFigFir);

        BigDecimal figSec = new BigDecimal(ComputePhisics.asNormalizedRadians(9));
        Double roundFigSec = figSec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(2.717, roundFigSec);
    }

    public void testRound() throws Exception {
        assertEquals(0, ComputePhisics.round(0));
        assertEquals(0, ComputePhisics.round(0.4));
        assertEquals(1, ComputePhisics.round(0.5));
        assertEquals(0, ComputePhisics.round(0.45));
        assertEquals(1, ComputePhisics.round(0.99));
    }
}