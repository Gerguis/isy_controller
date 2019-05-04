package ck.isyhelper.nodes;

public class DimmerNode extends OnOffNode implements DimmerInterface {

    public void setLevel(int level) {
        execute("/" + address + "/cmd/DOF/" + level);
    }

    public void setLevel(float percent) {
        int level = (int) (255 * percent);
        setLevel(level);
    }

    public void increaseBrightness() {
        execute("/" + address + "/cmd/BRT");
    }

    public void decreaseBrightness() {
        execute("/" + address + "/cmd/DIM");
    }
}
