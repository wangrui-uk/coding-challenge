package com.gohenry.coding.challenge.services;

import com.gohenry.coding.challenge.dto.ParentDto;
import com.gohenry.coding.challenge.exceptions.DatabaseConnectionException;
import com.gohenry.coding.challenge.exceptions.ParentExistedException;
import com.gohenry.coding.challenge.exceptions.ParentNotFoundException;
import com.gohenry.coding.challenge.repositories.ParentRepository;
import com.gohenry.coding.challenge.repositories.entities.Parent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class ParentService {

    ParentRepository parentRepository;

    public Mono<ParentDto> createParent(ParentDto parentDto) {
        try {
            Parent parent = this.parentRepository.findByEmailAddress(parentDto.getEmailAddress());
            if (Optional.ofNullable(parent).isEmpty()) {
                return Mono.just(Parent.build(parentDto))
                        .map(this.parentRepository::save)
                        .map(savedParent -> ParentDto.build(savedParent))
                        .doOnError(throwable -> {
                            log.error("Unable to access data from DB", throwable);
                            throw new DatabaseConnectionException(throwable);
                        });
            } else {
                log.warn("Parent already registered");
                throw new ParentExistedException(parentDto.getEmailAddress());
            }
        } catch (DataAccessException e) {
            log.error("Unable to access data from DB", e);
            throw new DatabaseConnectionException(e);
        }
    }

    public Mono<ParentDto> retrieveParent(long id) {
        try {
            Parent parent = this.parentRepository.findById(id).orElseThrow();
            return Mono.just(parent)
                    .map(this.parentRepository::save)
                    .map(savedParent -> ParentDto.build(savedParent))
                    .doOnError(throwable -> {
                        log.error("Unable to access data from DB", throwable);
                        throw new DatabaseConnectionException(throwable);
                    });
        } catch (NoSuchElementException e) {
            log.error("Unable to find parent", e);
            throw new ParentNotFoundException();
        } catch (DataAccessException e) {
            log.error("Unable to access data from DB", e);
            throw new DatabaseConnectionException(e);
        }
    }

    public Mono<ParentDto> updateParent(long id, ParentDto parentDto) {
        try {
            Parent parent = this.parentRepository.findById(id).orElseThrow();
            parent.setTitle(parentDto.getTitle());
            parent.setFirstName(parentDto.getFirstName());
            parent.setLastName(parentDto.getLastName());
            parent.setDateOfBirth(parentDto.getDateOfBirth());
            parent.setGender(parentDto.getGender());
            parent.setSecondName(parentDto.getSecondName());
            return Mono.just(parent)
                    .map(this.parentRepository::save)
                    .map(savedParent -> ParentDto.build(savedParent))
                    .doOnError(throwable -> {
                        log.error("Unable to access data from DB", throwable);
                        throw new DatabaseConnectionException(throwable);
                    });
        } catch (NoSuchElementException e) {
            log.error("Unable to find parent", e);
            throw new ParentNotFoundException();
        } catch (DataAccessException e) {
            log.error("Unable to access data from DB", e);
            throw new DatabaseConnectionException(e);
        }
    }

}
