package com.gohenry.coding.challenge.repositories.entities;

import com.gohenry.coding.challenge.dto.ChildDto;
import com.gohenry.coding.challenge.models.GENDER;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(exclude = "parent")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "children")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    long id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentid")
    Parent parent;

    public static Child build(ChildDto childDto, Parent parent) {
        return Child.builder()
                .firstName(childDto.getFirstName())
                .lastName(childDto.getLastName())
                .emailAddress(childDto.getEmailAddress())
                .dateOfBirth(childDto.getDateOfBirth())
                .gender(childDto.getGender())
                .secondName(childDto.getSecondName())
                .parent(parent)
                .build();
    }

}
