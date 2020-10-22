package kr.jm.utils.time;

/**
 * The type Timestamp data.
 *
 * @param <D> the type parameter
 */
public class TimestampData<D> {

    private long timestamp;
    private D data;

    /**
     * Instantiates a new Timestamp data.
     *
     * @param data the data
     */
    public TimestampData(D data) {
        this(System.currentTimeMillis(), data);
    }

    /**
     * Instantiates a new Timestamp data.
     *
     * @param timestamp the timestamp
     * @param data      the data
     */
    public TimestampData(long timestamp, D data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public D getData() {
        return this.data;
    }

}
