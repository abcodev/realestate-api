package realestate.server.application.realestate.infrastructure.jpa.converter;

import realestate.server.application.realestate.domain.AbolishStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AbolishStatusConverterTest {

    private final AbolishStatusConverter converter = new AbolishStatusConverter();

    @Test
    void abolishStatus_EXIST가_주어지면_DB컬럼값_존재로_변환된다() {
        // given
        AbolishStatus status = AbolishStatus.EXIST;

        // when
        String dbData = converter.convertToDatabaseColumn(status);

        // then
        assertThat(dbData).isEqualTo("존재");
    }

    @Test
    void DB컬럼값_존재가_주어지면_abolishStatus_EXIST로_변환된다() {
        // given
        String dbData = "존재";

        // when
        AbolishStatus status = converter.convertToEntityAttribute(dbData);

        // then
        assertThat(status).isEqualTo(AbolishStatus.EXIST);
    }

    @Test
    void abolishStatus가_null이면_DB컬럼도_null로_변환된다() {

        String dbData = converter.convertToDatabaseColumn(null);

        assertThat(dbData).isNull();
    }

    @Test
    void 알수없는_DB값이_주어지면_예외가_발생한다() {

        String dbData = "없는값";

        assertThatThrownBy(() -> converter.convertToEntityAttribute(dbData))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
