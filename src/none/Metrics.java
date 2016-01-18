package none;

import java.util.*;

/**
 * Created by j on 12/12/2015.
 */
public class Metrics {

    long startTime;

    Deque<String> tracker = new LinkedList<>();

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

        tracker.add(System.currentTimeMillis() - this.startTime +"   \t:  "+ note);
    }

    public long getElapsed(){
        return System.currentTimeMillis() - this.startTime;

    }

    public void resetTimer(){
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {

        String result = "";
        for(String note: this.tracker){
            result += "\n::> ["+note +"]";
        }
        return result;
    }
}
