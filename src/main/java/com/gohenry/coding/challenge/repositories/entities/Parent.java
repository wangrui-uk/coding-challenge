package com.gohenry.coding.challenge.repositories.entities;

import com.gohenry.coding.challenge.dto.ParentDto;
import com.gohenry.coding.challenge.models.GENDER;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@EqualsAndHashCode(exclude = "children")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    long id;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "firstname", nullable = false)
    String firstName;
    @Column(name = "lastname", nullable = false)
    String lastName;
    @Column(name = "emailaddress", nullable = false)
    String emailAddress;
    @Column(name = "dateofbirth", nullable = false)
    LocalDate dateOfBirth;
    @Column(name = "gender", nullable = false)
    GENDER gender;
    @Column(name = "secondname", nullable = true)
    String secondName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Child> children;

    public static Parent build(ParentDto parentDto) {
        Parent parent = Parent.builder()
                .title(parentDto.getTitle())
                .firstName(parentDto.getFirstName())
                .lastName(parentDto.getLastName())
                .emailAddress(parentDto.getEmailAddress())
                .dateOfBirth(parentDto.getDateOfBirth())
                .gender(parentDto.getGender())
                .secondName(parentDto.getSecondName())
                .build();
        parent.setChildren(Optional.ofNullable(parentDto.getChildren()).orElse(Collections.emptySet()).stream().map(childDto -> Child.build(childDto, parent)).collect(Collectors.toSet()));
        return parent;
    }

}
