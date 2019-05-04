package ck.isyhelper.nodes;

public class OnOffNode extends ISYNode implements OnOffInterface {


    public void turnOff(){
        execute("/" + address + "/cmd/DOF");
    }


    public void turnOn(){
        execute("/" + address + "/cmd/DON");
    }


    public void fastOff() {
        execute("/" + address + "/cmd/DFOF");
    }


    public void fastOn() {
        execute("/" + address + "/cmd/DFON");
    }
}
