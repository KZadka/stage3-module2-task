package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorServiceTest {

    @Configuration
    @ComponentScan("com.mjc.school")
    static class Config {
    }

    @Autowired
    private BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service;

    @Test
    void givenSizeOfFullListWhenReadAllThenReturnListOfAllAuthors() {
        int expectedSize = 20;

        List<AuthorDtoResponse> authorList = service.readAll();

        assertEquals(expectedSize, authorList.size());
    }

    @Test
    void givenAuthorIdWhenReadAuthorByIdThenReturnAuthorDtoResponse() {
        long expectedId = 17;

        AuthorDtoResponse actualResponse = service.readById(expectedId);

        assertNotEquals(null, actualResponse);
        assertEquals(expectedId, actualResponse.getId());
    }

    @Test
    void givenWrongAuthorIdWhenReadByIdThenThrowsException() {
        long wrongAuthorId = 55;

        assertThrows(ResourceNotFoundException.class, () -> service.readById(wrongAuthorId));
    }

    @Test
    void givenAuthorDtoRequestWhenCreateThenReturnExpectedAuthorDtoResponse() {
        AuthorDtoResponse expected = new AuthorDtoResponse(
               21L,
               "Name",
               LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
               LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        AuthorDtoRequest request = new AuthorDtoRequest(
                21L,
                "Name");

        AuthorDtoResponse actual = service.create(request);

        assertEquals(expected, actual);
    }

    @Test
    void givenAuthorDtoRequestWhenUpdateThenReturnUpdatedDtoResponse() {
        LocalDateTime timeOfCreation = service.readById(3L).getCreateDate();
        AuthorDtoResponse expected = new AuthorDtoResponse(
                3L,
                "Updated name",
                timeOfCreation,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        AuthorDtoResponse actual = service.update(new AuthorDtoRequest(
                3L,
                "Updated name"));

        assertEquals(expected, actual);
    }

    @Test
    void givenWrongAuthorIdWhenUpdateThenThrowException() {
        long wrongAuthorId = 77;

        assertThrows(ResourceNotFoundException.class, () -> service.update(new AuthorDtoRequest(
                wrongAuthorId,
                "Updated name")));
    }

    @Test
    void givenAuthorIdWhenDeleteThenReturnTrue() {
        assertTrue(service.deleteById(1L));
    }

    @Test
    void givenWrongAuthorIdWhenDeleteThenThrowException() {
        long wrongAuthorId = 53;

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(wrongAuthorId));
    }
}