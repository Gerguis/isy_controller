package ck.isyhelper.nodes;

import ck.isyhelper.ISYClient;
import ck.isyhelper.MyApplication;

public abstract class ISYNode {
    int flag=0;
    String address = null;
    String type = null;
    boolean enabled = true;
    int deviceClass = 0;
    int wattage = 0;
    int dcPeriod = 0;
    int startDelay = 0;
    int endDelay = 0;
    String pnode = null;
    String elkId = null;

    void execute(String command){
        ISYClient client = ISYClient.getInstance();
        client.executeCommand(command);
    }
}
