package org.aptech.t2208e.jpaRepository.consts;

public class SqlQueryConstants {
    public static final String SQL_SELECT_ALL = "select * from %table_name% limit 1000 offset 1;";
    public static final String SQL_SEARCH_BY_FIRST_NAME = "select * from %table_name% where first_name like '%first_name%%';";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM %table_name% WHERE id = ?;";
    public static final String SQL_INSERT_DATA = "INSERT INTO %table_name% (first_name, last_name, address, age) VALUES (?, ?, ?, ?)";
}
