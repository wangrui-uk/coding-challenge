package com.gohenry.coding.challenge.resources;

import com.gohenry.coding.challenge.dto.ParentDto;
import com.gohenry.coding.challenge.models.GENDER;
import com.gohenry.coding.challenge.services.ParentService;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParentResourceTest {

    @Mock
    ParentService parentService;
    private static final long PARENT_ID = 101;
    private WebTestClient webTestClient;
    private ArgumentCaptor<ParentDto> acParent = ArgumentCaptor.forClass(ParentDto.class);
    private ArgumentCaptor<Long> acLong = ArgumentCaptor.forClass(Long.class);
    private ParentDto parentDto;

    @Before
    public void setup() {
        this.webTestClient = WebTestClient.bindToController(new ParentResource(this.parentService)).build();

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
        Mockito.verifyNoMoreInteractions(this.parentService);
    }

    @Test
    public void test001_CreateParent_Success() {
        Mockito.when(this.parentService.createParent(Mockito.any(ParentDto.class))).thenReturn(Mono.just(this.parentDto.withId(PARENT_ID)));

        this.webTestClient.post().uri("/"+ ParentResource.RESOURCE_NAME)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(this.parentDto), ParentDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("id").isEqualTo(PARENT_ID);

        Mockito.verify(this.parentService, Mockito.only()).createParent(this.acParent.capture());
    }

    @Test
    public void test002_CreateParent_Failed_Missing_ContextType() {
        Assertions.assertThatThrownBy(() -> this.webTestClient.post().uri("/"+ ParentResource.RESOURCE_NAME)
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just(this.parentDto), ParentDto.class)
                .exchange()
                .expectStatus().is4xxClientError())
                .isInstanceOf(UnsupportedMediaTypeException.class)
                .hasMessageContaining("Content type 'text/plain' not supported");
    }

    @Test
    public void test003_CreateParent_Failed_Missing_Email() {
        ParentDto parentDtoWithoutEmail = ParentDto.builder()
                .dateOfBirth(LocalDate.now())
                .firstName("Firstname")
                .lastName("Lastname")
                .title("TITLE")
                .gender(GENDER.other)
                .build();

        this.webTestClient.post().uri("/"+ ParentResource.RESOURCE_NAME)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(parentDtoWithoutEmail), ParentDto.class)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void test004_RetrieveParent_Success() {
        Mockito.when(this.parentService.retrieveParent(Mockito.anyLong())).thenReturn(Mono.just(this.parentDto.withId(PARENT_ID)));

        this.webTestClient.get().uri("/"+ ParentResource.RESOURCE_NAME+"/{id}", PARENT_ID)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("id").isEqualTo(PARENT_ID);

        Mockito.verify(this.parentService, Mockito.only()).retrieveParent(this.acLong.capture());
    }

    @Test
    public void test005_UpdateParent_Success() {
        ParentDto parentDtoWithoutEmail = ParentDto.builder()
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("DifferentFirstname")
                .lastName("Lastname")
                .title("TITLE")
                .gender(GENDER.other)
                .build();

        Mockito.when(this.parentService.updateParent(Mockito.anyLong(), Mockito.any(ParentDto.class))).thenReturn(Mono.just(parentDtoWithoutEmail.withId(PARENT_ID)));

        this.webTestClient.put().uri("/"+ ParentResource.RESOURCE_NAME+"/{id}", PARENT_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(this.parentDto), ParentDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("id").isEqualTo(PARENT_ID)
                .jsonPath("firstName").isNotEmpty()
                .jsonPath("firstName").isEqualTo("DifferentFirstname");

        Mockito.verify(this.parentService, Mockito.only()).updateParent(this.acLong.capture(), this.acParent.capture());
    }

    @Test
    public void test006_UpdateParent_Failed_Missing_ContextType() {
        Assertions.assertThatThrownBy(() -> this.webTestClient.put().uri("/"+ ParentResource.RESOURCE_NAME+"/{id}", PARENT_ID)
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just(this.parentDto), ParentDto.class)
                .exchange()
                .expectStatus().is4xxClientError())
                .isInstanceOf(UnsupportedMediaTypeException.class)
                .hasMessageContaining("Content type 'text/plain' not supported");
    }

}
