package com.dh.sp.postgres.model.identity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.Serializable;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class BookId implements Serializable {

    private final UUID id;

    public BookId() {
        this.id =  UUID.randomUUID();
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Converter
    public static class DbConverter implements AttributeConverter<BookId, UUID> {

        @Override
        public UUID convertToDatabaseColumn(BookId id) {
            return id != null ? id.getId() : null;
        }

        @Override
        public BookId convertToEntityAttribute(UUID id) {
            return id != null ? new BookId(id) : null;
        }
    }
}
