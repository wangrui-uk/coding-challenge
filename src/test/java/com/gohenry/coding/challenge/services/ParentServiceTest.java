package com.gohenry.coding.challenge.services;

import com.gohenry.coding.challenge.dto.ParentDto;
import com.gohenry.coding.challenge.exceptions.ParentExistedException;
import com.gohenry.coding.challenge.exceptions.ParentNotFoundException;
import com.gohenry.coding.challenge.models.GENDER;
import com.gohenry.coding.challenge.repositories.ParentRepository;
import com.gohenry.coding.challenge.repositories.entities.Parent;
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
public class ParentServiceTest {

    @InjectMocks
    ParentService parentService;
    @Mock
    ParentRepository parentRepository;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final long PARENT_ID = 101;
    private ArgumentCaptor<ParentDto> acParentDto = ArgumentCaptor.forClass(ParentDto.class);
    private ArgumentCaptor<Parent> acParent = ArgumentCaptor.forClass(Parent.class);
    private ArgumentCaptor<Long> acLong = ArgumentCaptor.forClass(Long.class);
    private ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
    private Parent parent;
    private ParentDto parentDto;

    @Before
    public void setup() {
        this.parent = Parent.builder()
                .id(PARENT_ID)
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("Firstname")
                .lastName("Lastname")
                .title("TITLE")
                .gender(GENDER.other)
                .build();

        this.parentDto = ParentDto.builder()
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("Firstname")
                .lastName("Lastname")
                .title("TITLE")
                .gender(GENDER.other)
                .build();
    }

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(this.parentRepository);
    }

    @Test
    public void test001_CreateParent_Success() {
        Mockito.when(this.parentRepository.findByEmailAddress(Mockito.anyString())).thenReturn(null);
        Mockito.when(this.parentRepository.save(Mockito.any(Parent.class))).thenReturn(this.parent);

        ParentDto result = this.parentService.createParent(this.parentDto).block();
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), PARENT_ID);

        Mockito.verify(this.parentRepository, Mockito.times(1)).findByEmailAddress(this.acString.capture());
        Mockito.verify(this.parentRepository, Mockito.times(1)).save(this.acParent.capture());
        Parent parent = this.acParent.getValue();
        Assert.assertEquals(parent.getEmailAddress(), "email@email.com");
    }

    @Test
    public void test002_CreateParent_Failed_Existed_Parent() {
        Mockito.when(this.parentRepository.findByEmailAddress(Mockito.anyString())).thenReturn(this.parent);

        Assertions
                .assertThatThrownBy(() -> this.parentService.createParent(this.parentDto).block())
                .isInstanceOf(ParentExistedException.class)
                .hasMessage("Parent email email@email.com already registered, please use a different email address");

        Mockito.verify(this.parentRepository, Mockito.only()).findByEmailAddress(this.acString.capture());
    }

    @Test
    public void test003_RetrieveParent_Success() {
        Mockito.when(this.parentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(this.parent));

        ParentDto result = this.parentService.retrieveParent(PARENT_ID).block();
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), PARENT_ID);

        Mockito.verify(this.parentRepository, Mockito.only()).findById(this.acLong.capture());
    }

    @Test
    public void test004_RetrieveParent_Failed_Unable_To_Find() {
        Mockito.when(this.parentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> this.parentService.retrieveParent(PARENT_ID).block())
                .isInstanceOf(ParentNotFoundException.class)
                .hasMessage("Unable to find parent");

        Mockito.verify(this.parentRepository, Mockito.times(1)).findById(this.acLong.capture());
    }

    @Test
    public void test005_UpdateParent_Success() {
        Parent oldParent = Parent.builder()
                .id(PARENT_ID)
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("OldFirstname")
                .lastName("OldLastname")
                .title("OldTITLE")
                .gender(GENDER.other)
                .build();

        Mockito.when(this.parentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(oldParent));
        Mockito.when(this.parentRepository.save(Mockito.any(Parent.class))).thenReturn(this.parent);

        ParentDto result = this.parentService.updateParent(PARENT_ID, this.parentDto).block();
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), PARENT_ID);

        Mockito.verify(this.parentRepository, Mockito.times(1)).findById(this.acLong.capture());
        Mockito.verify(this.parentRepository, Mockito.times(1)).save(this.acParent.capture());
        Parent parent = this.acParent.getValue();
        Assert.assertEquals(parent.getFirstName(), "Firstname");
    }

    @Test
    public void test006_UpdateParent_Failed_Unable_To_Find() {
        Mockito.when(this.parentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> this.parentService.updateParent(PARENT_ID, this.parentDto).block())
                .isInstanceOf(ParentNotFoundException.class)
                .hasMessage("Unable to find parent");

        Mockito.verify(this.parentRepository, Mockito.times(1)).findById(this.acLong.capture());
    }

}
