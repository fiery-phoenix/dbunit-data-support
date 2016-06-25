package org.dbunit.data.support.model;

import org.dbunit.data.support.generators.ValueGenerator;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.datatype.DataType;


public class GeneratableColumn<T> extends Column {

    private final ValueGenerator<T> valueGenerator;

    public GeneratableColumn(String columnName, DataType dataType, ValueGenerator<T> valueGenerator) {
        super(columnName, dataType);
        this.valueGenerator = valueGenerator;
    }

    @Override
    public String getDefaultValue() {
        T generatedValue = valueGenerator.next();

        return generatedValue == null ? null : generatedValue.toString();
    }
}
