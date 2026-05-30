package realestate.server.application.realestate.infrastructure.jpa.converter;

import realestate.server.application.realestate.domain.AbolishStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class AbolishStatusConverter implements AttributeConverter<AbolishStatus, String> {

    @Override
    public String convertToDatabaseColumn(AbolishStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public AbolishStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(AbolishStatus.values())
                .filter(status -> status.getDescription().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}



