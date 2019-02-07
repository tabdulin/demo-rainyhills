package tabdulin.demo.rainyhills;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(description = "Rainy hills water calculator controller")
@RestController
public class RainyHillsController {
    public static final String SURFACE_ENDPOINT = "/surface";

    @Autowired
    private RainyHillsService rainyHillsService;

    @ApiOperation(value = "Surface to rain at",
            consumes = "application/json",
            produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok.", response = Long.class),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @PostMapping(SURFACE_ENDPOINT)
    public long calculateWater(@RequestBody int[] surface) {
        return rainyHillsService.calculateWaterVolume(surface);
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(Throwable throwable) {
        if (throwable instanceof HttpMessageNotReadableException
                || throwable instanceof HttpMediaTypeNotSupportedException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Only json array of numbers is supported as a request body (numbers will be floored " +
                            "to the closest integer value");
        }

        log.error("Unknown error occurred", throwable);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
