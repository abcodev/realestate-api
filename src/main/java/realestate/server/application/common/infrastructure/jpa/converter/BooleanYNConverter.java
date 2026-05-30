package realestate.server.application.common.infrastructure.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanYNConverter implements AttributeConverter<Boolean, String> {

    private static final String Y = "Y";
    private static final String N = "N";

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return Boolean.TRUE.equals(value) ? Y : N;
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return Y.equals(value);
    }
}
