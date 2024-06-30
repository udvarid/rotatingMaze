package dt_maze_challenge.action;

public class Rotate {
    private final int district;
    private final int direction;

    public Rotate(int district, int direction) {
        this.district = district;
        this.direction = direction;
    }

    public int getDistrict() {
        return district;
    }

    public int getDirection() {
        return direction;
    }
}
