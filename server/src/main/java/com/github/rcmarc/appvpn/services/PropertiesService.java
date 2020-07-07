package com.github.rcmarc.appvpn.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class PropertiesService {

    private String PATH = "./src/main/resources/";
    private final Properties properties = new Properties();
    private String file = "config.properties";

    @PostConstruct
    private void init() {
        PATH += file;
        try (FileReader reader = new FileReader(PATH)) {
            properties.load(reader);
        } catch (IOException e) {
            log.error("Failed to load properties file, cause: " + e.getMessage());
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
        try (OutputStream stream = new FileOutputStream(PATH)) {
            properties.setProperty(key, value);
            properties.store(stream, "saved in: " + LocalDateTime.now());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private Boolean getBooleanProperty(String key){
        return Boolean.valueOf(getProperty(key));
    }

     private Long getLongProperty(String key) {
        return Long.valueOf(getProperty(key));
    }

    private Integer getIntProperty(String key) {
        return Integer.valueOf(getProperty(key));
    }
}
