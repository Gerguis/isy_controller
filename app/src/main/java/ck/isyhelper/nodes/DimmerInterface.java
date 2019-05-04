package ck.isyhelper.nodes;

public interface DimmerInterface extends OnOffInterface {
    void setLevel(int level);
    void setLevel(float percent);
    void increaseBrightness();
    void decreaseBrightness();
}
