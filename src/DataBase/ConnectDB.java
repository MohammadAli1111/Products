/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import Model.ModelTable;
import Model.Product;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mohammad
 */
public class ConnectDB {

    private final String url = "jdbc:sqlserver://DESKTOP-SMQKCNT:1433;databaseName=product_db";
    private final String password = "12345";
    private final String user = "sa";
    private final String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static Connection connection;
    private static Statement statement;
    private String Sql;
    private PreparedStatement ps;

    public ConnectDB() {
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            System.out.println("Connecting DB");
        } catch (Exception e) {
            throw new RuntimeException("Error in Connect DB");
        }
    }

    public ObservableList getAll() throws Exception {
        List<ModelTable> list = new ArrayList<>();
        Sql = "select * from product";
        ResultSet resultSet = statement.executeQuery(Sql);
        while (resultSet.next()) {
            ImageView imageView = new ImageView(Images_converter.convertByteArrayToImage(resultSet.getBytes(4)));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            list.add(new ModelTable(resultSet.getInt(1), resultSet.getString(2),
                     resultSet.getString(3),
                     imageView, resultSet.getFloat(5)));
        }
        ObservableList data = FXCollections.observableList(list);

        return data;
    }

    public String save(Product product) {

        try {
            Sql = "insert into product (name,type,photo,price)values(?,?,?,?)";
            ps = connection.prepareStatement(Sql);
            ps.setString(1, product.getName());
            ps.setString(2, product.getType());
            ps.setBytes(3, product.getPhoto());
            ps.setFloat(4, product.getPrice());
            ps.executeUpdate();
        } catch (SQLException ex) {
            return ex.getMessage();
        }
        return "Save successfully";

    }

    public String update(Product product) {

        try {

            Sql = "update product set name=? , type=? , photo=?,price=?  where id=?";

            ps = connection.prepareStatement(Sql);
            ps.setString(1, product.getName());
            ps.setString(2, product.getType());
            ps.setBytes(3, product.getPhoto());
            ps.setFloat(4, product.getPrice());
            ps.setInt(5, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            return ex.getMessage();
        }
        return "Update successfully";

    }

    public void delete(String value, String key) throws SQLException {
        Sql = "delete from product where " + key + "=?";
        ps = connection.prepareStatement(Sql);
        switch (key) {
            case "id":
                ps.setInt(1, Integer.valueOf(value));
                break;
            case "name":
                ps.setString(1, value);
                break;
            case "type":
                ps.setString(1, value);
                break;
            case "price":
                ps.setFloat(1, Float.valueOf(value));
                break;

        }
        
        ps.executeUpdate();
    }
    
    public ObservableList select(String value, String key) throws Exception {
        List<ModelTable> list = new ArrayList<>();
        Sql = "select * from product where "+key+"='"+value+"'";
        ResultSet resultSet = statement.executeQuery(Sql);
        while (resultSet.next()) {
            ImageView imageView = new ImageView(Images_converter.convertByteArrayToImage(resultSet.getBytes(4)));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            list.add(new ModelTable(resultSet.getInt(1), resultSet.getString(2),
                     resultSet.getString(3),
                     imageView, resultSet.getFloat(5)));
        }
        ObservableList data = FXCollections.observableList(list);

        return data;
    }

  
}
