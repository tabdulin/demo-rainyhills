package tabdulin.demo.rainyhills;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RainyHillsServiceTest {
    private RainyHillsService rainyHillsService = new RainyHillsService();

    @Test
    void testEmptySurface() {
        assertEquals(0, rainyHillsService.calculateWaterVolume());
    }

    @Test
    void testConstantlyDeclinedSurface() {
        assertEquals(0, rainyHillsService.calculateWaterVolume(1, 2, 3));
        assertEquals(0, rainyHillsService.calculateWaterVolume(1, 2, 2, 3));
        assertEquals(0, rainyHillsService.calculateWaterVolume(3, 2, 1));
    }

    @Test
    void testFlatSurface() {
        assertEquals(0, rainyHillsService.calculateWaterVolume(new int[10]));
        assertEquals(0, rainyHillsService.calculateWaterVolume(1));
        assertEquals(0, rainyHillsService.calculateWaterVolume(1, 1));
        assertEquals(0, rainyHillsService.calculateWaterVolume(1, 1, 1));
    }

    @Test
    void testSingleHole() {
        assertEquals(1, rainyHillsService.calculateWaterVolume(1, 0, 2));
        assertEquals(6, rainyHillsService.calculateWaterVolume(3, 2, 1, 0, 10));
    }

    @Test
    void testFullNegativeSurface() {
        assertEquals(1, rainyHillsService.calculateWaterVolume(-1));
        assertEquals(7, rainyHillsService.calculateWaterVolume(-1, -2, -1, -2, -1));
    }

    @Test
    void testFullNonNegativeSurface() {
        assertEquals(2, rainyHillsService.calculateWaterVolume(3, 2, 4, 1, 2));
        assertEquals(8, rainyHillsService.calculateWaterVolume(4, 1, 1, 0, 2, 3));
        assertEquals(1, rainyHillsService.calculateWaterVolume(3, 2, 0, 1));
        assertEquals(2, rainyHillsService.calculateWaterVolume(3, 2, 0, 0, 1));
    }

    @Test
    void testSeveralHoles() {
        assertEquals(21, rainyHillsService.calculateWaterVolume(5, 2, 1, 6, 0, 1, 0, 5, 3, 0));
        assertEquals(11, rainyHillsService.calculateWaterVolume(2, 5, -2, 0, 3, -1, 2, 0));
    }

    @Test
    void testIntegerOverflowVolume() {
        assertEquals(2 * ((long) Integer.MAX_VALUE + 1), rainyHillsService.calculateWaterVolume(
                Integer.MAX_VALUE, -1, -1, Integer.MAX_VALUE));
    }
}