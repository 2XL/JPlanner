package none;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by j on 12/12/2015.
 */
public class Metrics {

    long startTime;

    Map<Long, String> tracker = new HashMap<>();

    /**
     * Instance the invocation time of the metric collector
     */
    public Metrics() {
        this.resetTimer();
    }

    /**
     *
     * @param note
     */
    public void keepTrack(String note) {
        tracker.put(System.currentTimeMillis() - this.startTime, note);
    }

    /**
     *
     * @return
     */
    public Collection getTracker(){
        return this.tracker.values();
    }


    public long getElapsed(){
        return System.currentTimeMillis() - this.startTime;

    }

    public void resetTimer(){
        this.startTime = System.currentTimeMillis();
    }

}
