package BattleshipsExamplePlayer;

import javafx.util.Pair;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by TEE on 11/09/2016.
 */
public class BucketPredictor {
    private final boolean returnConst;
    private List<Pair<Integer, Double>> boundaries = new ArrayList<>();

    public BucketPredictor(List<Pair<Integer, Integer>> scores, int numBuckets, Logger logger) {
        if (scores.size() < numBuckets) {
            returnConst = true;
            return;
        }
        returnConst = false;

        int numResults = scores.size();
        Collections.sort(scores, (score1, score2) -> compare(score1.getKey(), score2.getKey()));
        for (int i = 0; i < numBuckets; i++) {
            int bucketStart = (int) (i*numResults) / (numBuckets);
            int bucketEnd = (int) (i+1)*numResults / (numBuckets);
            double score = 0;
            for (int j = bucketStart; j < bucketEnd ; j++) {
                score += scores.get(j).getValue();
            }
            score /= (bucketEnd - bucketStart);
            Integer end = scores.get(bucketEnd - 1).getKey();
            boundaries.add(new Pair<>(end ,score));
            logger.log("Bucket - score " + score + ", end " + end);
        }
    }

    public double getScore(Integer score){
        if (returnConst) return 0;

        double result = boundaries.get(0).getValue();
        for (Pair<Integer, Double> boundary : boundaries) {
            result = boundary.getValue();
            if (score < boundary.getKey()) break;
        }
        return result;
    }

    private int compare(Integer key, Integer key1) {
        return key < key1 ? -1 : (key.equals(key1) ? 0 : 1);
    }
}
