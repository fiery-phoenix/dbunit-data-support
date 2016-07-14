# dbunit-data-support [![Build Status](https://api.travis-ci.org/fiery-phoenix/dbunit-data-support.svg)](https://travis-ci.org/fiery-phoenix/dbunit-data-support)

Java API to build and populate tables data sets for dbunit (*not a unique implementation of the idea).

The starting point for tables data creation and storing is `DbUnitDataUtils` class.

To work with tables it is required to implement `ConnectionAwareTable` interface, which returns information about table name,
columns and connection required to update data in database:

    String getName();
    Column[] getColumns();
    IDatabaseConnection getConnection();

There could be used enum for this, for example:

    public enum TasksTables implements ConnectionAwareTable {

        USERS("USERS", Users.getColumns()),
        LISTS("LISTS", Lists.getColumns()),
        ...;

        private final String name;
        private final Column[] columns;

        TasksTables(String name, Column[] columns) {
            this.name = name;
            this.columns = columns;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Column[] getColumns() {
            return Arrays.copyOf(columns, columns.length);
        }


        @Override
        public IDatabaseConnection getConnection() {
            return ConnectionUtils.getConnection();
        }

    }

    public interface Users {

        Column ID = new Column("ID", BIGINT);
        Column LOGIN = new GeneratableColumn<>("LOGIN", VARCHAR, sequence("test"));
        Column NAME = new GeneratableColumn<>("NAME", VARCHAR, constant("Test"));

        static Column[] getColumns() {
            return new Column[]{ID, LOGIN, NAME};
        }

    }

### Supported features

- Clean tables using

    ```java
    deleteFrom(USERS, LISTS, TASKS, ...);
    ```

- Create and populate data sets using insert and cleanInsert methods with
    - row builder notation
        ```java
        insert(USERS, row().with(ID, 1).with(LOGIN, "kit").with(NAME, "Sophi"),
                row().with(ID, 2).with(LOGIN, "gray").with(NAME, "Shellena"),
                row().with(ID, 3).with(LOGIN, "pawel").with(NAME, "Pawel Dou"));
        ```

    - columns builder notation
        ```java
        insert(USERS, columns(ID, LOGIN, NAME)
                .values(1, "kit", "Sophi")
                .values(2, "gray", "Shellena")
                .values(3, "pawel", "Pawel Dou"));
        ```

- Minor features
    - template row

        ```java
        RowBuilder template = row().with(NAME, "Test");
        insert(USERS, row(template).with(ID, 1).with(LOGIN, "kit");
        ```

    - repeated row
        - row builder notation

            ```java
            cleanInsert(USERS, row().with(NAME, "Shellena").times(4));
            ```

        - columns builder notation

            ```java
            cleanInsert(USERS, columns(NAME).repeatingValues("Shellena").times(4));
            ```

- Generated values

    Quite often there are mandatory columns, which are irrelevant for tests, or can be populated by some rule.
    To avoid specifying value for each row, there could be used auto generated values.
    `ValueGenerator` implementations are responsible for returning next column value as required.
    For default implementations please refer to `ValueGenerators` class.

    - values generation present in table description

    `GeneratableColumn` class extends `Column` with `ValueGenerator` which will be used when column value is not specified.
    LOGIN column from the example below will be populated with values "test1", "test2", "test3" and so on, whenever it is not set directly,
    and will also meet unique constraint.

        Column LOGIN = new GeneratableColumn<>("LOGIN", VARCHAR, ValueGenerators.sequence("test"));

    - values generation passed to [template] row definition or columns definition

        ```java
        RowBuilder template = row().withGenerated(ID, sequence()).withGenerated(LOGIN, sequence("login"));

        row().withGenerated(ID, sequence(5, 5)).withGenerated(LOGIN, sequence("login")).times(10);

        columns(NAME).repeatingValues("Sophi").times(20)
        .withGenerated(ID, sequence(5, 2))
        .withGenerated(LOGIN, sequence("login"));
        ```

- Assertions

    To check table data there could be used table assertions from `org.dbunit.data.support.DbUnitAssertions`.
    For now there are available table size and comparison with another table assertions.

    ```java
    ssertThat(USERS).hasSize(2);

    TableBuilder expectedTable = table(
                    row().with(LOGIN, "l2"),
                    row().with(LOGIN, "l1")
            );
    assertThat(USERS).ignoring(ID).andOrder().isEqualTo(expectedTable);
     ```

### Upcoming features
- More options for values generators (add random value generators, for example).
- For now there is support only for ConnectionAwareTable, need to change this

### Some ideas
* Snapshot of data sets in different formats
* Assertions?
* Provide API for JBehave
* Idea convenience plugin for formatting and so on
* Switch to more general table creation API (which could be effectively used not only for dbunit tables)?
