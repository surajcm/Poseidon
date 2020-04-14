package com.poseidon.dataaccess.db;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

@Converter(autoApply = true)
public class OffsetDateTimePersistenceConverter implements AttributeConverter<OffsetDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(final OffsetDateTime attribute) {
        return attribute == null ? null : Timestamp.from(Instant.from(attribute));
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(final Timestamp dbData) {
        return dbData == null ? null : OffsetDateTime.parse(dbData.toInstant().toString());
    }

}
