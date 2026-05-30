package realestate.server.application.realestate.application.service;

import realestate.server.application.realestate.infrastructure.client.dto.RssFeedResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class RssDateParser {

    private static final DateTimeFormatter RFC_1123_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    /**
     * Parse pubDate like "Mon, 30 Mar 2026 17:45:06 +0900" into ZonedDateTime.
     */
    public static Optional<ZonedDateTime> parsePubDate(String pubDate) {
        if (pubDate == null || pubDate.isBlank()) return Optional.empty();
        try {
            return Optional.of(ZonedDateTime.parse(pubDate.trim(), RFC_1123_FORMATTER));
        } catch (DateTimeParseException e) {
            log.warn("Failed to parse RSS pubDate: {}", pubDate);
            return Optional.empty();
        }
    }

    /**
     * Check if the item's pubDate is within the last 24 hours from the given 'now'.
     */
    public static boolean isWithin24Hours(RssFeedResponse.Item item, ZonedDateTime now) {
        return parsePubDate(item.getPubDate())
                .map(pubDate -> ChronoUnit.HOURS.between(pubDate, now) <= 24 && ChronoUnit.HOURS.between(pubDate, now) >= 0)
                .orElse(false);
    }
}
