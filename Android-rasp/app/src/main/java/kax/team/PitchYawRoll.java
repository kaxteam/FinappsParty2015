package kax.team;

/**
 * Created by zenbook on 7/11/15.
 */
public class PitchYawRoll {
    private double pitch, yaw, roll;
    private boolean fall;

    public PitchYawRoll(double pitch, double yaw, double roll, boolean fall) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.fall = fall;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public boolean isFall() {
        return fall;
    }

    public void setFall(boolean fall) {
        this.fall = fall;
    }

    @Override
    public String toString() {
        return "PitchYawRoll{" +
                "pitch=" + pitch +
                ", yaw=" + yaw +
                ", roll=" + roll +
                ", fall=" + fall +
                '}';
    }
}
