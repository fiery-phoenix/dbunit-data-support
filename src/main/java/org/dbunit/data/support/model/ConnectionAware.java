package org.dbunit.data.support.model;

import org.dbunit.database.IDatabaseConnection;

public interface ConnectionAware {

    IDatabaseConnection getConnection();

}
