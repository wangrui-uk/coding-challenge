package com.gohenry.coding.challenge.resources;

import com.gohenry.coding.challenge.dto.ChildDto;
import com.gohenry.coding.challenge.models.GENDER;
import com.gohenry.coding.challenge.services.ChildService;
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
public class ChildResourceTest {

    @Mock
    ChildService childService;
    private static final long PARENT_ID = 101;
    private WebTestClient webTestClient;
    private ArgumentCaptor<ChildDto> acChild = ArgumentCaptor.forClass(ChildDto.class);
    private ArgumentCaptor<Long> acLong = ArgumentCaptor.forClass(Long.class);
    private ChildDto childDto;

    @Before
    public void setup() {
        this.webTestClient = WebTestClient.bindToController(new ChildResource(this.childService)).build();

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
        Mockito.verifyNoMoreInteractions(this.childService);
    }

    @Test
    public void test001_UpdateChild_Success() {
        ChildDto childDtoWithoutEmail = ChildDto.builder()
                .dateOfBirth(LocalDate.now())
                .emailAddress("email@email.com")
                .firstName("DifferentFirstname")
                .lastName("Lastname")
                .gender(GENDER.other)
                .build();

        Mockito.when(this.childService.updateChild(Mockito.anyLong(), Mockito.any(ChildDto.class))).thenReturn(Mono.just(childDtoWithoutEmail.withId(PARENT_ID)));

        this.webTestClient.put().uri("/"+ ChildResource.RESOURCE_NAME+"/{id}", PARENT_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(this.childDto), ChildDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("id").isEqualTo(PARENT_ID)
                .jsonPath("firstName").isNotEmpty()
                .jsonPath("firstName").isEqualTo("DifferentFirstname");

        Mockito.verify(this.childService, Mockito.only()).updateChild(this.acLong.capture(), this.acChild.capture());
    }

    @Test
    public void test002_UpdateChild_Failed_Missing_ContextType() {
        Assertions.assertThatThrownBy(() -> this.webTestClient.put().uri("/"+ ChildResource.RESOURCE_NAME+"/{id}", PARENT_ID)
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just(this.childDto), ChildDto.class)
                .exchange()
                .expectStatus().is4xxClientError())
                .isInstanceOf(UnsupportedMediaTypeException.class)
                .hasMessageContaining("Content type 'text/plain' not supported");
    }

}
