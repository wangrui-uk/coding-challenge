package com.gohenry.coding.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gohenry.coding.challenge.models.GENDER;
import com.gohenry.coding.challenge.repositories.entities.Child;
import com.gohenry.coding.challenge.repositories.entities.Parent;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParentDto {

    long id;
    @NotBlank(message = "Title cannot be empty")
    @NotNull(message = "Title cannot be empty")
    String title;
    @NotBlank(message = "First name cannot be empty")
    @NotNull(message = "First name cannot be empty")
    String firstName;
    @NotBlank(message = "First name cannot be empty")
    @NotNull(message = "Last name cannot be empty")
    String lastName;
    @Email(message = "Please enter valid email address")
    @NotNull(message = "Email cannot be empty")
    String emailAddress;
    @NotNull(message = "Date of Birth cannot be empty")
    @JsonFormat(pattern = "yyy-MM-dd")
    LocalDate dateOfBirth;
    @NotNull(message = "Gender cannot be empty")
    GENDER gender;
    String secondName;
    Set<ChildDto> children;

    public static ParentDto build(Parent parent) {
        return ParentDto.builder()
                .id(parent.getId())
                .title(parent.getTitle())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .emailAddress(parent.getEmailAddress())
                .dateOfBirth(parent.getDateOfBirth())
                .gender(parent.getGender())
                .secondName(parent.getSecondName())
                .children(Optional.ofNullable(parent.getChildren()).orElse(Collections.emptySet()).stream().map(ChildDto::build).collect(Collectors.toSet()))
                .build();
    }

}
