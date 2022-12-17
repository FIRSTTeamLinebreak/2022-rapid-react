package frc.robot;

public class Utils {

    /**
     * Clamps a value between a min and a max, to limmit the range of a value
     * 
     * @param value the target value to return
     * @param min the minimum value the function can return
     * @param the maximum value the function can return
     */
    public static double clamp(double value, double min, double max) {
        if(value > max) return max;
        if(value < min) return min;
        return value;
    }
    
}
