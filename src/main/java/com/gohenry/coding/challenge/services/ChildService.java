package com.gohenry.coding.challenge.services;

import com.gohenry.coding.challenge.dto.ChildDto;
import com.gohenry.coding.challenge.exceptions.ChildNotFoundException;
import com.gohenry.coding.challenge.exceptions.DatabaseConnectionException;
import com.gohenry.coding.challenge.repositories.ChildRepository;
import com.gohenry.coding.challenge.repositories.entities.Child;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.NoSuchElementException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class ChildService {

    ChildRepository childRepository;

    public Mono<ChildDto> updateChild(long id, ChildDto childDto) {
        try {
            Child child = this.childRepository.findById(id).orElseThrow();
            child.setFirstName(childDto.getFirstName());
            child.setLastName(childDto.getLastName());
            child.setDateOfBirth(childDto.getDateOfBirth());
            child.setGender(childDto.getGender());
            child.setSecondName(childDto.getSecondName());
            return Mono.just(child)
                    .map(this.childRepository::save)
                    .map(savedChild -> ChildDto.build(child))
                    .doOnError(throwable -> {
                        log.error("Unable to access data from DB", throwable);
                        throw new DatabaseConnectionException(throwable);
                    });
        } catch (NoSuchElementException e) {
            log.error("Unable to find child", e);
            throw new ChildNotFoundException();
        } catch (DataAccessException e) {
            log.error("Unable to access data from DB", e);
            throw new DatabaseConnectionException(e);
        }
    }

}
