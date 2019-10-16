package com.gohenry.coding.challenge.services;

import com.gohenry.coding.challenge.dto.ChildDto;
import com.gohenry.coding.challenge.exceptions.ChildNotFoundException;
import com.gohenry.coding.challenge.models.GENDER;
import com.gohenry.coding.challenge.repositories.ChildRepository;
import com.gohenry.coding.challenge.repositories.entities.Child;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChildServiceTest {

    @InjectMocks
    ChildService childService;
    @Mock
    ChildRepository childRepository;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final long PARENT_ID = 101;
    private ArgumentCaptor<Child> acChild = ArgumentCaptor.forClass(Child.class);
    private ArgumentCaptor<Long> acLong = ArgumentCaptor.forClass(Long.class);
    private Child child;
    private ChildDto childDto;

    @Before
    public void setup() {
        this.child = Child.builder()
                .id(PARENT_ID)
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("Firstname")
                .lastName("Lastname")
                .gender(GENDER.other)
                .build();

        this.childDto = ChildDto.builder()
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("Firstname")
                .lastName("Lastname")
                .gender(GENDER.other)
                .build();
    }

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(this.childRepository);
    }

    @Test
    public void test001_UpdateChild_Success() {
        Child oldChild = Child.builder()
                .id(PARENT_ID)
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("OldFirstname")
                .lastName("OldLastname")
                .gender(GENDER.other)
                .build();

        Mockito.when(this.childRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(oldChild));
        Mockito.when(this.childRepository.save(Mockito.any(Child.class))).thenReturn(this.child);

        ChildDto result = this.childService.updateChild(PARENT_ID, this.childDto).block();
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), PARENT_ID);

        Mockito.verify(this.childRepository, Mockito.times(1)).findById(this.acLong.capture());
        Mockito.verify(this.childRepository, Mockito.times(1)).save(this.acChild.capture());
        Child child = this.acChild.getValue();
        Assert.assertEquals(child.getFirstName(), "Firstname");
    }

    @Test
    public void test002_UpdateChild_Failed_Unable_To_Find() {
        Mockito.when(this.childRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> this.childService.updateChild(PARENT_ID, this.childDto).block())
                .isInstanceOf(ChildNotFoundException.class)
                .hasMessage("Unable to find child");

        Mockito.verify(this.childRepository, Mockito.times(1)).findById(this.acLong.capture());
    }

}
