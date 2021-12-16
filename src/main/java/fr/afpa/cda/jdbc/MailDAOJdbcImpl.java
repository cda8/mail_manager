package fr.afpa.cda.jdbc;

import fr.afpa.cda.metier.LocalMail;

import javax.mail.Flags;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MailDAOJdbcImpl {

    public MailDAOJdbcImpl() {
    }

    public List<LocalMail> selectAll(){

        List<LocalMail> listMails = new ArrayList<>();
        Connection connection = connection();
        String sql = "SELECT * FROM Mail";

        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                LocalMail mail = new LocalMail();

                mail.setIdMail(rs.getInt("idMail"));
                mail.setSujet(rs.getString("sujet"));
                mail.setExpediteur(rs.getString("expediteur"));
                mail.setDateReception(rs.getDate("dateReception"));
                mail.setFlag(rs.getString("status").charAt(0));
                listMails.add(mail);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return listMails;
    }

    public List<LocalMail> selectByFlag(String flag){
        List<LocalMail> listMails = new ArrayList<>();
        Connection connection = connection();
        String sql = "SELECT * FROM Mail WHERE status=?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, flag);
            ResultSet rs = stmt.executeQuery();
            if(rs != null){
                while (rs.next()){
                    LocalMail mail = new LocalMail();

                    mail.setIdMail(rs.getInt("idMail"));
                    mail.setSujet(rs.getString("sujet"));
                    mail.setExpediteur(rs.getString("expediteur"));
                    mail.setDateReception(rs.getDate("dateReception"));
                    mail.setFlag(rs.getString("status").charAt(0));
                    listMails.add(mail);
                }
            } else {
                System.err.println("le resultat est vide");

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }


        return listMails;
    }

    public void insert(LocalMail mail){

        Connection connection = connection();
        String sql = "INSERT INTO Mail VALUES( ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, mail.getIdMail());
            stmt.setString(2, mail.getSujet());
            stmt.setString(3, mail.getExpediteur());
            stmt.setDate(4, formattedDate(mail.getDateReception()));
            stmt.setString(5, mail.getFlag());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Date formattedDate(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }

    public Connection connection() {

        String url = "jdbc:postgresql://localhost:5433/MAILS_DB";
        String user = "entreprise";
        String password = "afpa123";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }


}
