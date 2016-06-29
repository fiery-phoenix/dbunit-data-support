# dbunit-data-support [![Build Status](https://api.travis-ci.org/fiery-phoenix/dbunit-data-support.svg)](https://travis-ci.org/fiery-phoenix/dbunit-data-support)

Java API to build and populate tables data sets for dbunit (*not a unique implementation of the idea).

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

### Upcoming features

- Generated values
    - values generation present in table description
    - values generation passed to [template] row definition, before columns values definition
- For now there is support only for ConnectionAwareTable, need to change this
- Add check for using not existing columns in table definition

### Some ideas
* Snapshot of data sets in different formats
* Assertions?
* Provide API for JBehave
* Idea convenience plugin for formatting and so on
* Switch to more general table creation API (which could be effectively used not only for dbunit tables)?
