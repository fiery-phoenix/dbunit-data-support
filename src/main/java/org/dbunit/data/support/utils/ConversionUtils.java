package org.dbunit.data.support.utils;

import org.dbunit.dataset.Column;

import static java.util.Arrays.stream;

public final class ConversionUtils {

    private ConversionUtils() {
    }

    public static String[] toColumnsNames(Column[] columns) {
        return stream(columns).map(Column::getColumnName).toArray(String[]::new);
    }

}
