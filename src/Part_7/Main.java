package Part_7;


import com.mysql.jdbc.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;

import Part_7.JobWithSQL.*;

/**
 * Учет комплектующих компьютеров
 * */
public class Main {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /**
         * 0) Удаляем старые таблицы если есть в БД
         */
        for (String tableName: JobWithSQL.tebles) {
            if(JobWithSQL.testAlreadyExists(tableName)){
                JobWithSQL.deleteTable(tableName);
            }
        }
        /**
         * 1) Создание реляционных таблиц,
         *      перебираем список таблиц
         *      и отправляем запросы на их создание
         */
        for(String s: JobWithSQL.tebles){
            //Получаем метод с помощью Java рефлексии
            Method method = JobWithSQL.class.getMethod("createTable"+capitalize(s));
            //Вызов метода с помощью Java рефлексии
            JobWithSQL.createTableSQLString(s, (String[]) method.invoke(null, null));
        }

        /**
         * 2) добавление нового объекта;
         */
        String[] departmntList = new String[]{"Бухгалтерия", "Склад", "Автохозяйство"};
        for (String department : departmntList){
            addDepartment(department); //Заполним новыми объектами отделы
        }
        String[] usersList = new String[]{"Васько Пупкин", "Тарас Бульба", "Себастьян Перейро"};
        for (String user : usersList){
            addUser(user); //Заполним новыми объектами пользователей
        }

        updateUser("Васько Пупкин", "Бухгалтерия");
        updateUser("Тарас Бульба", "Склад");
        updateUser("Себастьян Перейро", "Автохозяйство");

        viewAllUserInfo();

        deleteUser("Тарас Бульба");

        viewAllUserInfo();
    }

    /**
     * Для преобразования имен таблиц в имена методов
     * Удаляет подчерк и первая буква слова заглавная
     * @param str - Строка
     * @return
     */
    public static String capitalize(String str){
        String[] parts = str.split("_");
        String nameMethod = "";
        for (String part : parts){
            nameMethod += part.substring(0, 1).toUpperCase() + part.substring(1);
        }
        return nameMethod;
    }

    /**
     * Создание новой записи в таблице Users
     * @param name - имя пользователя
     */
    public static void addUser(String name){
        String query = "INSERT INTO `users` (`name`) VALUES (?)";
        try (PreparedStatement stmt = JobWithSQL.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Пользователь "+name+" добавлен!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создание новой записи в таблице Department
     * @param name - название депортамента
     */
    public static void addDepartment(String name){
        String query = "INSERT INTO `department` (`name`) VALUES (?)";
        try (PreparedStatement stmt = JobWithSQL.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Департамент "+name+" добавлен!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обновления записи пользователя
     * по имени пользователя
     * @param nameUser
     * @param departmentName
     */
    public static void updateUser(String nameUser, String departmentName){
        //int idDepartment = selectIdDepartment(departmentName); //id отдела через его название
        //String queryUpdate = "UPDATE `users` SET `id_department` = ? WHERE `users`.`name` LIKE ?;";
        String queryUpdate = "UPDATE `users` " +
                "SET `id_department` = (SELECT id FROM `department` WHERE name LIKE ? LIMIT 1) " +
                "WHERE `users`.`name` LIKE ?;"; //через вложенный запрос
        try (PreparedStatement stmt = JobWithSQL.conn.prepareStatement(queryUpdate)) {
            //stmt.setInt(1, idDepartment); //если был id отдела
            stmt.setString(1, departmentName);
            stmt.setString(2, nameUser);
            if(stmt.executeUpdate()>0){
                System.out.println("Пользователь "+nameUser+" обновлен!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение уникального идентификатора отдела
     * @param departmentName
     * @return
     */
    public static int selectIdDepartment(String departmentName){
        String query = "SELECT id FROM `department` WHERE name LIKE ? LIMIT 1;";
        try (PreparedStatement stmt = JobWithSQL.conn.prepareStatement(query)) {
            stmt.setString(1, departmentName);
            ResultSet rez = stmt.executeQuery();
            rez.next();
            if(rez.getRow() > 0){
                return rez.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * Отобразить всех пользователей
     * с указанием отделов
     * через LEFT JOIN
     */
    public static void viewAllUserInfo() {
        String query = "SELECT u.id AS id_user, " +
                "u.name AS name_user, " +
                "d.name AS name_depart " +
                "FROM `users` AS u LEFT JOIN department AS d ON u.id_department = d.id;";
        try (Statement stmt = JobWithSQL.conn.createStatement()){
            ResultSet rez = stmt.executeQuery(query);
            System.out.println("-------------------------------------------------");
            System.out.println("| ID_USER | NAME USER | DEPARTMENT |");
            while (rez.next()){
                System.out.println("-------------------------------------------------");
                System.out.println("|"+rez.getInt("id_user")+" | "+
                        rez.getString("name_user")+" | "+
                        rez.getString("name_depart")+" |");
                System.out.println("-------------------------------------------------");
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление пользователя через имя
     * @param nameUser
     */
    public static void deleteUser(String nameUser){
        String query = "SELECT id FROM `users` WHERE name = ? LIMIT 1;";
        try (PreparedStatement stmt = JobWithSQL.conn.prepareStatement(query)){
            stmt.setString(1, nameUser);
            ResultSet rez = stmt.executeQuery();
            rez.next();
            int idUser = rez.getInt("id");
            if(idUser>0){
                JobWithSQL.deleteRow("users", idUser);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
