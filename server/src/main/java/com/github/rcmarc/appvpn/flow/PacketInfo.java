package com.github.rcmarc.appvpn.flow;

import com.github.rcmarc.appvpn.interfaces.IdGenerator;
import lombok.Data;
import org.jnetpcap.packet.format.FormatUtils;

import java.util.Arrays;

@Data
public class PacketInfo {
    private long id;
    private byte[] src;
    private byte[] dst;
    private int srcPort;
    private int dstPort;
    private int protocol;
    private long timeStamp;
    private long payloadBytes;
    private String flowId;
    private boolean flagFIN;

    public PacketInfo(IdGenerator generator) {
        super();
        this.id = generator.nextId();
    }

    public String generateFlowId() {
        boolean forward = true;
        for (int i = 0; i < this.src.length; i++) {
            if (Byte.toUnsignedInt(this.src[i]) != Byte.toUnsignedInt(this.dst[i])) {
                if (Byte.toUnsignedInt(this.src[i]) > Byte.toUnsignedInt(this.dst[i])) {
                    forward = false;
                }
                i = this.src.length;
            }
        }

        if (forward) {
            this.flowId = this.getSourceIP() + "-" + this.getDestinationIP() + "-" + this.srcPort + "-" + this.dstPort + "-" + this.protocol;
        } else {
            this.flowId = this.getDestinationIP() + "-" + this.getSourceIP() + "-" + this.dstPort + "-" + this.srcPort + "-" + this.protocol;
        }
        return this.flowId;
    }

    public String getSourceIP() {
        return FormatUtils.ip(this.src);
    }

    public String getDestinationIP() {
        return FormatUtils.ip(this.dst);
    }

    public String getFlowId() {
        return this.flowId != null ? this.flowId : generateFlowId();
    }

    public boolean isForwardPacket(byte[] sourceIP) {
        return Arrays.equals(sourceIP, this.src);
    }

    public boolean hasFlagFIN() {
        return flagFIN;
    }
}
