package com.gohenry.coding.challenge.resources;

import com.gohenry.coding.challenge.dto.ChildDto;
import com.gohenry.coding.challenge.exceptions.ErrorDetails;
import com.gohenry.coding.challenge.services.ChildService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(ChildResource.RESOURCE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Api(value = "Child API")
@Slf4j
public class ChildResource {

    static final String RESOURCE_NAME = "children";
    ChildService childService;

    @ApiOperation(value = "Update Child details")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Child updated", response = ChildDto.class),
            @ApiResponse(code = 400, message = "Unable to update child", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Unable to update child", response = ErrorDetails.class)
    })
    @PutMapping("/{id}")
    public Mono<ChildDto> updateParent(@PathVariable("id") @NotNull long id, @RequestBody @NotNull @Valid ChildDto child) {
        return this.childService.updateChild(id, child);
    }

}
