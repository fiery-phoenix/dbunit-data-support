package org.dbunit.data.support.model;

import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.utils.Preconditions;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

public class VerifiableTableMetaData implements ITableMetaData {

    private final ITableMetaData tableMetaData;
    private final Set<String> columnsNames;

    public VerifiableTableMetaData(ITableMetaData tableMetaData) {
        this.tableMetaData = tableMetaData;
        columnsNames = getColumnsNames(tableMetaData);
    }

    private Set<String> getColumnsNames(ITableMetaData tableMetaData) {
        try {
            return stream(tableMetaData.getColumns()).map(Column::getColumnName)
                    .collect(toSet());
        } catch (DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    @Override
    public String getTableName() {
        return tableMetaData.getTableName();
    }

    @Override
    public Column[] getColumns() throws DataSetException {
        return tableMetaData.getColumns();
    }

    @Override
    public Column[] getPrimaryKeys() throws DataSetException {
        return tableMetaData.getPrimaryKeys();
    }

    @Override
    public int getColumnIndex(String columnName) throws DataSetException {
        return tableMetaData.getColumnIndex(columnName);
    }

    public void verifyColumnsNames(Set<String> checkedColumnsNames) {
        Preconditions.checkArgument(columnsNames.containsAll(checkedColumnsNames),
                () -> checkedColumnsNames.stream().filter(columnName -> !columnsNames.contains(columnName))
                        .collect(Collectors.toList()) + " are not registered column names");
    }
}
