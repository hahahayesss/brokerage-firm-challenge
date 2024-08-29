package io.atk.brokeragefirmchallenge.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateTimeUtils {

    public static LocalDateTime parse(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }
}
