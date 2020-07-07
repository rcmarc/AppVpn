package com.github.rcmarc.appvpn.services;

import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.Instances;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MOADatasetLoaderService {

    public Instances getCFSDataset(){
        Instances instances =new Instances("", attributes(), 0);
        instances.setClassIndex(2);
        return instances;
    }

    private Attribute[] attributes() {
        Attribute[] attrs = new Attribute[3];
        attrs[0] = new Attribute(" Flow Packets/s");
        attrs[1] = new Attribute(" Flow IAT Min");
        attrs[2] = new Attribute("label", List.of("NO-VPN", "VPN"));
        return attrs;
    }

}
