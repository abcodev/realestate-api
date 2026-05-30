package realestate.server.application.realestate.infrastructure.client.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "rss")
public class RssFeedResponse {

    @JacksonXmlProperty(localName = "channel")
    private Channel channel;

    @Data
    public static class Channel {
        @JacksonXmlProperty(localName = "title")
        private String title;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        private List<Item> items;
    }

    @Data
    public static class Item {
        @JacksonXmlProperty(localName = "title")
        private String title;

        @JacksonXmlProperty(localName = "link")
        private String link;

        @JacksonXmlProperty(localName = "pubDate")
        private String pubDate;
    }
}
