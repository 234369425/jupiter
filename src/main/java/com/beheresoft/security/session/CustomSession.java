package com.beheresoft.security.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.StoppedSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.util.CollectionUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

/**
 * @author Aladi
 * @date 2018-06-12 19:09:30
 * copy from SimpleSession
 */
@Slf4j
public class CustomSession implements ValidatingSession, Serializable {


    private static final long serialVersionUID = -1L;

    protected static final long MILLIS_PER_SECOND = 1000;
    protected static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    protected static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

    static int bitIndexCounter = 0;
    private static final int ID_BIT_MASK = 1 << bitIndexCounter++;
    private static final int START_TIMESTAMP_BIT_MASK = 1 << bitIndexCounter++;
    private static final int STOP_TIMESTAMP_BIT_MASK = 1 << bitIndexCounter++;
    private static final int LAST_ACCESS_TIME_BIT_MASK = 1 << bitIndexCounter++;
    private static final int TIMEOUT_BIT_MASK = 1 << bitIndexCounter++;
    private static final int EXPIRED_BIT_MASK = 1 << bitIndexCounter++;
    private static final int HOST_BIT_MASK = 1 << bitIndexCounter++;
    private static final int ATTRIBUTES_BIT_MASK = 1 << bitIndexCounter++;


    private Serializable id;
    private Date startTimestamp;
    private Date stopTimestamp;
    private Date lastAccessTime;
    private long timeout;
    private boolean expired;
    private String host;
    private Map<Object, Object> attributes;

    public CustomSession() {
        this.timeout = DefaultSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT; //TODO - remove concrete reference to DefaultSessionManager
        this.startTimestamp = new Date();
        this.lastAccessTime = this.startTimestamp;
    }

    public CustomSession(String host) {
        this();
        this.host = host;
    }

    @Override
    public Serializable getId() {
        return this.id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    @Override
    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getStopTimestamp() {
        return stopTimestamp;
    }

    public void setStopTimestamp(Date stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }

    @Override
    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * Returns true if this session has expired, false otherwise.  If the session has
     * expired, no further user interaction with the system may be done under this session.
     *
     * @return true if this session has expired, false otherwise.
     */
    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Object, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void touch() {
        this.lastAccessTime = new Date();
    }

    @Override
    public void stop() {
        if (this.stopTimestamp == null) {
            this.stopTimestamp = new Date();
        }
    }

    private boolean isStopped() {
        return getStopTimestamp() != null;
    }

    private void expire() {
        stop();
        this.expired = true;
    }


    @Override
    public boolean isValid() {
        return !isStopped() && !isExpired();
    }

    private boolean isTimedOut() {

        if (isExpired()) {
            return true;
        }

        long timeout = getTimeout();

        if (timeout >= 0L) {

            Date lastAccessTime = getLastAccessTime();

            if (lastAccessTime == null) {
                String msg = "session.lastAccessTime for session with id [" +
                        getId() + "] is null.  This value must be set at " +
                        "least once, preferably at least upon instantiation.  Please check the " +
                        getClass().getName() + " implementation and ensure " +
                        "this value will be set (perhaps in the constructor?)";
                throw new IllegalStateException(msg);
            }

            // Calculate at what time a session would have been last accessed
            // for it to be expired at this point.  In other words, subtract
            // from the current time the amount of time that a session can
            // be inactive before expiring.  If the session was last accessed
            // before this time, it is expired.
            long expireTimeMillis = System.currentTimeMillis() - timeout;
            Date expireTime = new Date(expireTimeMillis);
            return lastAccessTime.before(expireTime);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("No timeout for session with id [" + getId() +
                        "].  Session is not considered expired.");
            }
        }

        return false;
    }

    @Override
    public void validate() throws InvalidSessionException {
        //check for stopped:
        if (isStopped()) {
            //timestamp is set, so the session is considered stopped:
            String msg = "Session with id [" + getId() + "] has been " +
                    "explicitly stopped.  No further interaction under this session is " +
                    "allowed.";
            throw new StoppedSessionException(msg);
        }

        //check for expiration
        if (isTimedOut()) {
            expire();

            //throw an exception explaining details of why it expired:
            Date lastAccessTime = getLastAccessTime();
            long timeout = getTimeout();

            Serializable sessionId = getId();

            DateFormat df = DateFormat.getInstance();
            String msg = "Session with id [" + sessionId + "] has expired. " +
                    "Last access time: " + df.format(lastAccessTime) +
                    ".  Current time: " + df.format(new Date()) +
                    ".  Session timeout is set to " + timeout / MILLIS_PER_SECOND + " seconds (" +
                    timeout / MILLIS_PER_MINUTE + " minutes)";
            if (log.isTraceEnabled()) {
                log.trace(msg);
            }
            throw new ExpiredSessionException(msg);
        }
    }

    private Map<Object, Object> getAttributesLazy() {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>(10);
            setAttributes(attributes);
        }
        return attributes;
    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            return Collections.emptySet();
        }
        return attributes.keySet();
    }

    @Override
    public Object getAttribute(Object key) {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.get(key);
    }


    @Override
    public void setAttribute(Object key, Object value) {
        if (value == null) {
            removeAttribute(key);
        } else {
            getAttributesLazy().put(key, value);
        }
    }


    @Override
    public Object removeAttribute(Object key) {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            return null;
        } else {
            return attributes.remove(key);
        }
    }

    /**
     * Returns {@code true} if the specified argument is an {@code instanceof} {@code SimpleSession} and both
     * {@link #getId() id}s are equal.  If the argument is a {@code CustomSession} and either 'this' or the argument
     * does not yet have an ID assigned, the value of {@link #onEquals(CustomSession) onEquals} is returned, which
     * does a necessary attribute-based comparison when IDs are not available.
     * <p/>
     * Do your best to ensure {@code SimpleSession} instances receive an ID very early in their lifecycle to
     * avoid the more expensive attributes-based comparison.
     *
     * @param obj the object to compare with this one for equality.
     * @return {@code true} if this object is equivalent to the specified argument, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CustomSession) {
            CustomSession other = (CustomSession) obj;
            Serializable thisId = getId();
            Serializable otherId = other.getId();
            if (thisId != null && otherId != null) {
                return thisId.equals(otherId);
            } else {
                //fall back to an attribute based comparison:
                return onEquals(other);
            }
        }
        return false;
    }

    private boolean onEquals(CustomSession ss) {
        return (getStartTimestamp() != null ? getStartTimestamp().equals(ss.getStartTimestamp()) : ss.getStartTimestamp() == null) &&
                (getStopTimestamp() != null ? getStopTimestamp().equals(ss.getStopTimestamp()) : ss.getStopTimestamp() == null) &&
                (getLastAccessTime() != null ? getLastAccessTime().equals(ss.getLastAccessTime()) : ss.getLastAccessTime() == null) &&
                (getTimeout() == ss.getTimeout()) &&
                (isExpired() == ss.isExpired()) &&
                (getHost() != null ? getHost().equals(ss.getHost()) : ss.getHost() == null) &&
                (getAttributes() != null ? getAttributes().equals(ss.getAttributes()) : ss.getAttributes() == null);
    }

    @Override
    public int hashCode() {
        Serializable id = getId();
        if (id != null) {
            return id.hashCode();
        }
        int hashCode = getStartTimestamp() != null ? getStartTimestamp().hashCode() : 0;
        hashCode = 31 * hashCode + (getStopTimestamp() != null ? getStopTimestamp().hashCode() : 0);
        hashCode = 31 * hashCode + (getLastAccessTime() != null ? getLastAccessTime().hashCode() : 0);
        hashCode = 31 * hashCode + Long.valueOf(Math.max(getTimeout(), 0)).hashCode();
        hashCode = 31 * hashCode + Boolean.valueOf(isExpired()).hashCode();
        hashCode = 31 * hashCode + (getHost() != null ? getHost().hashCode() : 0);
        hashCode = 31 * hashCode + (getAttributes() != null ? getAttributes().hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(",id=").append(getId());
        return sb.toString();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        short alteredFieldsBitMask = getAlteredFieldsBitMask();
        out.writeShort(alteredFieldsBitMask);
        if (id != null) {
            out.writeObject(id);
        }
        if (startTimestamp != null) {
            out.writeObject(startTimestamp);
        }
        if (stopTimestamp != null) {
            out.writeObject(stopTimestamp);
        }
        if (lastAccessTime != null) {
            out.writeObject(lastAccessTime);
        }
        if (timeout != 0L) {
            out.writeLong(timeout);
        }
        if (expired) {
            out.writeBoolean(expired);
        }
        if (host != null) {
            out.writeUTF(host);
        }
        if (!CollectionUtils.isEmpty(attributes)) {
            out.writeObject(attributes);
        }
    }

    /**
     * Reconstitutes this object based on the specified InputStream for JDK Serialization.
     *
     * @param in the input stream to use for reading data to populate this object.
     * @throws IOException            if the input stream cannot be used.
     * @throws ClassNotFoundException if a required class needed for instantiation is not available in the present JVM
     * @since 1.0
     */
    @SuppressWarnings({"unchecked"})
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        short bitMask = in.readShort();

        if (isFieldPresent(bitMask, ID_BIT_MASK)) {
            this.id = (Serializable) in.readObject();
        }
        if (isFieldPresent(bitMask, START_TIMESTAMP_BIT_MASK)) {
            this.startTimestamp = (Date) in.readObject();
        }
        if (isFieldPresent(bitMask, STOP_TIMESTAMP_BIT_MASK)) {
            this.stopTimestamp = (Date) in.readObject();
        }
        if (isFieldPresent(bitMask, LAST_ACCESS_TIME_BIT_MASK)) {
            this.lastAccessTime = (Date) in.readObject();
        }
        if (isFieldPresent(bitMask, TIMEOUT_BIT_MASK)) {
            this.timeout = in.readLong();
        }
        if (isFieldPresent(bitMask, EXPIRED_BIT_MASK)) {
            this.expired = in.readBoolean();
        }
        if (isFieldPresent(bitMask, HOST_BIT_MASK)) {
            this.host = in.readUTF();
        }
        if (isFieldPresent(bitMask, ATTRIBUTES_BIT_MASK)) {
            this.attributes = (Map<Object, Object>) in.readObject();
        }
    }

    /**
     * Returns a bit mask used during serialization indicating which fields have been serialized. Fields that have been
     * altered (not null and/or not retaining the class defaults) will be serialized and have 1 in their respective
     * index, fields that are null and/or retain class default values have 0.
     *
     * @return a bit mask used during serialization indicating which fields have been serialized.
     * @since 1.0
     */
    private short getAlteredFieldsBitMask() {
        int bitMask = 0;
        bitMask = id != null ? bitMask | ID_BIT_MASK : bitMask;
        bitMask = startTimestamp != null ? bitMask | START_TIMESTAMP_BIT_MASK : bitMask;
        bitMask = stopTimestamp != null ? bitMask | STOP_TIMESTAMP_BIT_MASK : bitMask;
        bitMask = lastAccessTime != null ? bitMask | LAST_ACCESS_TIME_BIT_MASK : bitMask;
        bitMask = timeout != 0L ? bitMask | TIMEOUT_BIT_MASK : bitMask;
        bitMask = expired ? bitMask | EXPIRED_BIT_MASK : bitMask;
        bitMask = host != null ? bitMask | HOST_BIT_MASK : bitMask;
        bitMask = !CollectionUtils.isEmpty(attributes) ? bitMask | ATTRIBUTES_BIT_MASK : bitMask;
        return (short) bitMask;
    }

    private static boolean isFieldPresent(short bitMask, int fieldBitMask) {
        return (bitMask & fieldBitMask) != 0;
    }

}
