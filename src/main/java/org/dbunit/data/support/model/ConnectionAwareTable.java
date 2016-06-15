package org.dbunit.data.support.model;

import org.dbunit.database.IDatabaseConnection;

public interface ConnectionAwareTable extends Table {

    IDatabaseConnection getConnection();

}
