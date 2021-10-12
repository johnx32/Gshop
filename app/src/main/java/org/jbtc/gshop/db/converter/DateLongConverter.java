package org.jbtc.gshop.db.converter;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateLongConverter {
    @TypeConverter
    public static Date fromTimestamp(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
