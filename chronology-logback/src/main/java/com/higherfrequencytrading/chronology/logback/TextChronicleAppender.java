package com.higherfrequencytrading.chronology.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;
import com.higherfrequencytrading.chronology.Chronology;

import java.util.Date;

public abstract class TextChronicleAppender extends AbstractChronicleAppender {

    private String dateFormat;
    private Chronology.DateFormatCache dateFormatCache;

    protected TextChronicleAppender() {
        super();

        this.dateFormat = null;
        this.dateFormatCache = null;
    }

    // *************************************************************************
    // Custom logging options
    // *************************************************************************

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.dateFormatCache = new Chronology.DateFormatCache(dateFormat);
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    // *************************************************************************
    //
    // *************************************************************************

    @Override
    public void doAppend(final ILoggingEvent event) {
        if(getFilterChainDecision(event) != FilterReply.DENY) {
            appender.startExcerpt();
            appender.writeUTF(this.dateFormatCache.get().format(new Date(event.getTimeStamp())));
            appender.writeUTF(toStrChronologyLogLevel(event.getLevel()));
            appender.writeUTF(event.getThreadName());
            appender.writeUTF(event.getLoggerName());
            appender.writeUTF(event.getFormattedMessage());
            appender.finish();
        }
    }
}
