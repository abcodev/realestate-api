package realestate.server.application.realestate.application.service;

public record RealEstateSummaryCommand(
        Long userId,
        String bgdCode) {

    public boolean isMember() {
        return userId != null;
    }

    public static RealEstateSummaryCommand of(Long userId, String bgdCode) {
        if (userId == null && bgdCode == null) {
            throw new IllegalArgumentException("userId 또는 bgdCode는 필수입니다.");
        }

        if (userId != null && bgdCode != null) {
            throw new IllegalArgumentException("userId와 bgdCode를 동시에 사용할 수 없습니다.");
        }

        return new RealEstateSummaryCommand(userId, bgdCode);
    }
}