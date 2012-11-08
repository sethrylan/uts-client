package gov.va.iehr;

import java.util.List;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class TestUtils {
    
    
    public static String statSummary(DescriptiveStatistics stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("\tn = ").append(stats.getN()).append("; x-bar=").append(String.format("%1$,.2f", stats.getMean()))
        .append("; s=").append(String.format("%1$,.2f", stats.getStandardDeviation()))
        .append("; min:median:max=").append(stats.getMin())
        .append(" : ").append(stats.getPercentile(50)).append(" : ").append(stats.getMax())
        .append("\n\tkurtosis=").append(String.format("%1$,.2f",stats.getKurtosis()))
        .append("; skewness=").append(String.format("%1$,.2f",stats.getSkewness()));
        return sb.toString();
    }
    
    
    

    public static int sum(List<Integer> a) {
        if (a.size() > 0) {
            int sum = 0;

            for (Integer i : a) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    public static double mean(List<Integer> a) {
        int sum = sum(a);
        return sum / (a.size() * 1.0);
    }

    public static double median(List<Integer> a) {
        int middle = a.size() / 2;

        if (a.size() % 2 == 1) {
            return a.get(middle);
        } else {
            return (a.get(middle - 1) + a.get(middle)) / 2.0;
        }
    }

    public static double stdev(List<Integer> a) {
        int sum = 0;
        double mean = mean(a);

        for (Integer i : a) {
            sum += Math.pow((i - mean), 2);
        }
        return Math.sqrt(sum / (a.size() - 1)); // sample
    }
}
