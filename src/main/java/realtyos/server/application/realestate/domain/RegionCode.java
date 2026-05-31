package realtyos.server.application.realestate.domain;

public record RegionCode(
        String sggCode,
        String umdCode) {

    public RegionCode {
        if (sggCode == null || sggCode.length() != 5) {
            throw new IllegalArgumentException("Invalid sggCode: it should be 5 characters.");
        }
        if (umdCode == null || umdCode.length() != 5) {
            throw new IllegalArgumentException("Invalid umdCode: it should be 5 characters.");
        }
    }

    /**
     * bgdCode (법정동 코드)를 이용해 RegionCode 객체 생성
     * @param bgdCode : 법정동 코드 (10자리, sggCode + umdCode)
     * @return RegionCode 객체
     * @throws IllegalArgumentException : bgdCode가 유효하지 않으면 예외 발생
     */
    public static RegionCode fromBgdCode(String bgdCode) {
        if (bgdCode == null || bgdCode.length() != 10) {
            throw new IllegalArgumentException("올바르지 않은 법정동코드입니다. 10자여야 합니다.");
        }

        String sggCode = bgdCode.substring(0, 5);
        String umdCode = bgdCode.substring(5);

        return new RegionCode(sggCode, umdCode);
    }


    public String getFullRegionCode() {
        return sggCode + umdCode;
    }

    @Override
    public String toString() {
        return String.format("RegionCode{sggCode='%s', umdCode='%s'}", sggCode, umdCode);
    }

    public boolean isValid() {
        return sggCode.length() == 5 && umdCode.length() == 5;
    }

    public static RegionCode createRegionCode(String sggCode, String umdCode) {
        if (sggCode == null || sggCode.length() != 5 || umdCode == null || umdCode.length() != 5) {
            throw new IllegalArgumentException("Invalid region code. sggCode and umdCode must be 5 characters each.");
        }
        return new RegionCode(sggCode, umdCode);
    }
}