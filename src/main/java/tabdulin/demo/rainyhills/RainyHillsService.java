package tabdulin.demo.rainyhills;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RainyHillsService {
    /**
     * Calculates water collected in "holes" after calculateWater over the surface
     *
     * @param surface Array of hills of fixed width where i'th element is a height of i'th hill
     *
     * @return water volume in fixed units of hills
     */
    public long calculateWaterVolume(int... surface) {
        if (surface == null || surface.length == 0) {
            log.info("Calculated water volume for empty surface is 0");
            return 0;
        }

        log.info("Calculating water volume for surface with length {}", surface.length);
        int l = -1;
        long left = 0;
        int r = surface.length;
        long right = 0;
        long volume = 0;
        while (l < r && l < surface.length - 1 && r > 0) {
            if (left <= right) {
                l++;
                if (surface[l] >= left) {
                    left = surface[l];
                } else {
                    volume += left - surface[l];
                }
            } else {
                r--;
                if (surface[r] >= right) {
                    right = surface[r];
                } else {
                    volume += right - surface[r];
                }
            }
        }

        log.info("Calculated water volume {}", volume);
        return volume;
    }
}
