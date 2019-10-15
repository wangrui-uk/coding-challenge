package com.gohenry.coding.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gohenry.coding.challenge.models.GENDER;
import com.gohenry.coding.challenge.repositories.entities.Child;
import com.gohenry.coding.challenge.repositories.entities.Parent;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(exclude = "parent")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChildDto {

    long id;
    @NotBlank(message = "First name cannot be empty")
    @NotNull(message = "First name cannot be empty")
    String firstName;
    @NotBlank(message = "First name cannot be empty")
    @NotNull(message = "Last name cannot be empty")
    String lastName;
    @Email(message = "Please enter valid email address")
    String emailAddress;
    @NotNull(message = "Date of Birth cannot be empty")
    @JsonFormat(pattern = "yyy-MM-dd")
    LocalDate dateOfBirth;
    @NotNull(message = "Gender cannot be empty")
    GENDER gender;
    String secondName;

    public static ChildDto build(Child child) {
        return ChildDto.builder()
                .id(child.getId())
                .firstName(child.getFirstName())
                .lastName(child.getLastName())
                .emailAddress(child.getEmailAddress())
                .dateOfBirth(child.getDateOfBirth())
                .gender(child.getGender())
                .secondName(child.getSecondName())
                .build();
    }

}
