package com.github.rcmarc.appvpn.config;
import com.github.rcmarc.appvpn.repositories.AdminRepository;
import com.github.rcmarc.appvpn.services.AdminService;
import com.github.rcmarc.appvpn.services.DeviceService;
import com.github.rcmarc.appvpn.services.PropertiesService;
import com.github.rcmarc.appvpn.services.VpnService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnetpcap.PcapIf;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class InitialConfig implements InitializingBean {
    private final AdminRepository adminRepository;
    private final VpnService vpnService;
    private final PropertiesService propertiesService;
    private final DeviceService deviceService;
    private final AdminService adminService;

    @Override
    public void afterPropertiesSet() {

        // Check for the admin user
        long count = adminRepository.count();
        if (count == 0)
            adminService.createNewAdminInteractive();

        // Read the devices.selected property
        Long deviceSelected = getSelectedDeviceProperty();

        // If no device selected, do nothing
        if (deviceSelected == null || deviceSelected == -1) {
            log.warn("There is not interface selected");
            return;
        }

        // Select the pcap interface from the device.selected property
        PcapIf pcapIf = deviceService.select(deviceSelected);

        // Log the interface name
        log.info("The selected interface is: " + pcapIf.getName());

        // Listen to network packages
        vpnService.loop(pcapIf);
    }

    private Long getSelectedDeviceProperty() {
        String str = propertiesService.getProperty("devices.selected");
        if (str == null) {
            log.error("Error reading device selected property");
            return null;
        }
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException ex) {
            log.error("device selected property is not a number");
            return null;
        }
    }
}
