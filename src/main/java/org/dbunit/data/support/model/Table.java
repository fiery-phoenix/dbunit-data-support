package org.dbunit.data.support.model;

import org.dbunit.dataset.Column;

public interface Table {

    String getName();

    Column[] getColumns();

}
