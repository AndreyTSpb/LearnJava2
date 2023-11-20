package Part_7;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

import static Part_7.DBConnect.*;

public class JobWithSQL {

    public static final String[] tebles = new String[]{"department", "users", "components", "type_components", "computers", "items_computers"};
    public static final Connection conn;

    static {
        try {
            conn = DBConnect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод создания таблиц в БД,
     * если таблица уже существует новая не создается
     * @param tableName - имя будущей таблицы
     * @param columns - массив колонок с параметрами
     */
    public static void createTableSQLString(String tableName, String[] columns){
        StringBuilder questsColumns = new StringBuilder(); //собираем строку с полями таблицы
        for (int i= 0; i < columns.length; i++){
            questsColumns.append(columns[i]);
            if(i < columns.length-1){
                questsColumns.append(" ,"); //Добавляем разделитель
            }
        }
        String query = String.format("CREATE TABLE IF NOT EXISTS `%s`.`%s` (%s) ENGINE = InnoDB CHARSET=utf8 COLLATE utf8_general_ci;\n", BD_NAME, tableName, questsColumns);
        System.out.println(query);
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Создата таблица "+tableName+"!");
            System.out.println("Использован запрос: "+query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Колонки для таблицы Department
     * @return
     */
    public static String[] createTableDepartment(){
        return new String[]{
                "`id` INT(11) NOT NULL AUTO_INCREMENT",
                "name VARCHAR(255) NOT NULL",
                "del INT(1) NOT NULL DEFAULT '0'",
                "PRIMARY KEY (id)"
        };
    }

    /**
     * Колонки для таблицы TypeComponents
     * @return
     */
    public static String[] createTableTypeComponents(){
        return new String[]{
                "`id` INT(11) NOT NULL AUTO_INCREMENT",
                "`name` VARCHAR(255) NOT NULL",
                "`del` INT(1) NOT NULL DEFAULT '0'",
                "PRIMARY KEY (`id`)"
        };
    }

    /**
     * Колонки для таблицы Users
     * @return
     */
    public   static String[] createTableUsers(){
        return new String[]{
                "`id` INT(11) NOT NULL AUTO_INCREMENT",
                "`name` VARCHAR(255) NOT NULL",
                "`id_department` INT(11) NULL",
                "`del` INT(1) NOT NULL DEFAULT '0'",
                "PRIMARY KEY (`id`)"
        };
    }
    /**
     * Колонки для таблицы Computers
     * @return
     */
    public static String[] createTableComputers(){
        return new String[]{
                "`id` INT(11) NOT NULL AUTO_INCREMENT",
                "`code` VARCHAR(255) NOT NULL ",
                "`id_department` INT(11) NOT NULL",
                "`del` INT(1) NOT NULL DEFAULT '0'",
                "PRIMARY KEY (`id`)"
        };
    }

    /**
     * Колонки для таблицы ItemsComputers
     * @return
     */
    public static String[] createTableItemsComputers(){
        return new String[]{
                "`id` INT(11) NOT NULL AUTO_INCREMENT",
                "`id_items` INT(11) NOT NULL",
                "`del` INT(1) NOT NULL DEFAULT '0'",
                "PRIMARY KEY (`id`)"
        };
    }

    /**
     * Колонки для таблицы Components
     * @return
     */
    public static String[] createTableComponents(){
        return new String[]{
                "`id` INT(11) NOT NULL AUTO_INCREMENT",
                "`code` VARCHAR(255) NOT NULL",
                "`sn` VARCHAR(255) NOT NULL",
                "`del` INT(1) NOT NULL DEFAULT '0'",
                "PRIMARY KEY (`id`)"
        };
    }


    /**
     * Проверка сушествует ли таблицы
     */
    public static Boolean testAlreadyExists(String tableName){
            String query = "SHOW TABLES FROM `" + BD_NAME +"` LIKE ?;";
            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, tableName);
                ResultSet rez = stmt.executeQuery();
                rez.next();
                if(rez.getRow() > 0){
                    //если чтото найдется
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return false;
    }

    /**
     * Удаляем таблицу и базы данных по ее имени
     * @param tableName - имя таблицы
     */
    public static void deleteTable(String tableName){
        String query = String.format("DROP TABLE IF EXISTS `%s`.`%s`;\n", BD_NAME, tableName);
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
            stmt.close();
            System.out.println("****************************************");
            System.out.println("Удалена таблица "+tableName+"!");
            System.out.println("Использован запрос: "+query);
            System.out.println("****************************************");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление строки из таблици по ее уникальному идентификатору
     * @param tableName - имя таблицы
     * @param idRow - идентификатор
     */
    public static void deleteRow(String tableName, int idRow){
        String query = "DELETE FROM "+tableName+" WHERE `"+tableName+"`.`id` = ?";
        try (PreparedStatement stmt = JobWithSQL.conn.prepareStatement(query)) {
            //stmt.setInt(1, idDepartment); //если был id отдела
            stmt.setInt(1, idRow);
            if(stmt.executeUpdate()>0){
                System.out.println("*************************************************");
                System.out.println("Строка "+idRow+" из таблицы "+tableName+" удалена");
                System.out.println("*************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
