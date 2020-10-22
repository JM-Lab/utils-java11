package kr.jm.utils.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Last timestamp data manager.
 *
 * @param <D> the type parameter
 */
public class LastTimestampDataManager<D> {
    private final int maxDataSize;
    private final List<TimestampData<D>> timestampDataList;

    /**
     * Instantiates a new Last timestamp data manager.
     */
    public LastTimestampDataManager() {
        this(1024);
    }

    /**
     * Instantiates a new Last timestamp data manager.
     *
     * @param maxDataSize the max data size
     */
    public LastTimestampDataManager(int maxDataSize) {
        this.maxDataSize = maxDataSize;
        this.timestampDataList = new LinkedList<>();
    }

    /**
     * Add data.
     *
     * @param data the data
     */
    public void addData(D data) {
        synchronized (timestampDataList) {
            timestampDataList.add(new TimestampData<>(data));
            if (timestampDataList.size() > maxDataSize)
                timestampDataList.remove(0);
        }
    }

    /**
     * Clear timestamp data list.
     */
    public void clearTimestampDataList() {
        synchronized (timestampDataList) {
            timestampDataList.clear();
        }
    }

    /**
     * Gets max data size.
     *
     * @return the max data size
     */
    public int getMaxDataSize() {
        return maxDataSize;
    }

    /**
     * Gets timestamp data list.
     *
     * @return the timestamp data list
     */
    public List<TimestampData<D>> getTimestampDataList() {
        return new ArrayList<>(timestampDataList);
    }

    /**
     * Gets revers timestamp data list.
     *
     * @return the revers timestamp data list
     */
    public List<TimestampData<D>> getReversTimestampDataList() {
        List<TimestampData<D>> reverseTimestampDataList = getTimestampDataList();
        Collections.reverse(reverseTimestampDataList);
        return reverseTimestampDataList;
    }
}