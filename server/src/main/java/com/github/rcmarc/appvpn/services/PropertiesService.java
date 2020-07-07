package com.github.rcmarc.appvpn.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Properties;

@Service
@Slf4j
@Data
public class PropertiesService {

    private final Properties properties = new Properties();
    private final URL url = PropertiesService.class.getResource("config.properties");
    private InputStream stream;

    @PostConstruct
    private void init() {
        try {
            stream = url.openStream();
            loadProperties();
        } catch (IOException ex) {
            log.error("Error opening file: "  + url.getPath() + ", cause: " + ex.getMessage());
        }
    }

    public void setPath(String file) {
        try {
            stream.close();
            stream = PropertiesService.class.getResourceAsStream(file);
            properties.clear();
            loadProperties();
        } catch (IOException ex) {
            log.error("Failed to close file: ");
        }
    }

    private void loadProperties() {
        try {
            properties.load(stream);
        } catch (IOException ex) {
            log.error("Failed to load properties file, cause: " + ex.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Boolean getFlowBidirectional() {
        return getBooleanProperty("flow.bidirectional");
    }

    public Long getFlowTimeout() {
        return getLongProperty("flow.timeout");
    }

    public int getSnapLength() {
        return getIntProperty("pcap.snap.length");
    }

    public int getPromiscuous() {
        return getIntProperty("pcap.promiscuous");
    }

    public int getPcapTimeout() {
        return getIntProperty("pcap.timeout");
    }

    public Boolean getIPv4() {
        return getBooleanProperty("ip.v4");
    }

    public Boolean getIPv6() {
        return getBooleanProperty("ip.v6");
    }

    public void setValue(String key, String value) {
        try (OutputStream stream = new FileOutputStream(url.getPath())) {
            properties.setProperty(key, value);
            properties.store(stream, "saved at: " + LocalDateTime.now());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private Boolean getBooleanProperty(String key) {
        return Boolean.valueOf(getProperty(key));
    }

    private Long getLongProperty(String key) {
        return Long.valueOf(getProperty(key));
    }

    private Integer getIntProperty(String key) {
        return Integer.valueOf(getProperty(key));
    }
}
