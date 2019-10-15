package com.gohenry.coding.challenge.resources;

import com.gohenry.coding.challenge.dto.ParentDto;
import com.gohenry.coding.challenge.exceptions.ErrorDetails;
import com.gohenry.coding.challenge.services.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(ParentResource.RESOURCE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Api(value = "Parent API")
@Slf4j
public class ParentResource {

    static final String RESOURCE_NAME = "parents";
    ParentService parentService;

    @ApiOperation(value = "Create a Parent with children")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Parent created", response = ParentDto.class),
            @ApiResponse(code = 400, message = "Unable to create parent", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Unable to create parent", response = ErrorDetails.class)
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<ParentDto> createParent(@RequestBody @NotNull @Valid ParentDto parent) {
        return this.parentService.createParent(parent);
    }

    @ApiOperation(value = "Retrieve Parent by Parent ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Found a parent", response = ParentDto.class),
            @ApiResponse(code = 400, message = "Unable to find parent", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Unable to find parent", response = ErrorDetails.class)
    })
    @GetMapping("/{id}")
    public Mono<ParentDto> retrieveParent(@PathVariable("id") @NotNull long id) {
        return this.parentService.retrieveParent(id);
    }

    @ApiOperation(value = "Update Parent details")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Parent updated", response = ParentDto.class),
            @ApiResponse(code = 400, message = "Unable to update parent", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Unable to update parent", response = ErrorDetails.class)
    })
    @PutMapping("/{id}")
    public Mono<ParentDto> updateParent(@PathVariable("id") @NotNull long id, @RequestBody @NotNull @Valid ParentDto parent) {
        return this.parentService.updateParent(id, parent);
    }

}
