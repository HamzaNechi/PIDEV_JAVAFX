/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import controller.QRcodeController;
import entite.Tickets;
import entite.course;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Datasource;


/**
 *
 * @author manaa
 */
public class TicketService implements IService<Tickets>{
    
    private final Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public TicketService() {
        conn = Datasource.getInstance().getCnx();
    }

    @Override
    public void insert(Tickets c) {
        String req;
        System.out.println("ticket insert service : "+ c.toString());
        try {
             req = " insert into tickets (course_id, user_id ,type) values (?,?,?)";
                  pst = conn.prepareStatement(req);
                  pst.setObject(1,c.getCourse().getCourse_id());
                  pst.setObject(2, c.getUser_id().getUser_id());
                  pst.setString(3,c.getType());
                  pst.executeUpdate();
                  
                  

        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }

    

    @Override
    public void update(Tickets c) {
       try {

            String req = "update tickets set type=?  where id =?";
            pst = conn.prepareStatement(req);
            pst.setString(1, c.getType());
            pst.setInt(2, c.getTickets_id());
            pst.executeUpdate();
           pst.close();
           

        } catch (SQLException ex) { Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void update(String c1 , Tickets c ) {
         
       try {

            String req = "update tickets set type=?  where id =?";
            pst = conn.prepareStatement(req);
            pst.setString(1, c1);
            pst.setInt(2, c.getTickets_id());
            pst.executeUpdate();
           
           

        } catch (SQLException ex) { Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Tickets> read() {
         String req = "select * from tickets";
        List<Tickets> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            CourseService sc=new CourseService();
            UserService us= new UserService();
            while (rs.next()) {
                list.add(new Tickets(rs.getInt("id"),sc.readById( rs.getInt("course_id")),us.readById(rs.getInt("user_id")),rs.getString("type")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public List<Tickets> readTickets(int id) {
         String req = "select * from tickets where user_id="+id;
        List<Tickets> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            CourseService sc=new CourseService();
            UserService us= new UserService();
            while (rs.next()) {
                list.add(new Tickets(rs.getInt("id"),sc.readById( rs.getInt("course_id")),us.readById(rs.getInt("user_id")),rs.getString("type")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
   
    public List<Tickets> read1() {
         String req = "select course_id  , type from tickets";
        List<Tickets> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            CourseService sc=new CourseService();
            UserService us= new UserService();
            while (rs.next()) {
                list.add(new Tickets(sc.readById( rs.getInt("course_id")),rs.getString("type")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Tickets readById(int tickets_id) {
        
        String req = "select * from tickets where id="+tickets_id;
        Tickets t = new Tickets();
        CourseService sc=new CourseService();
        UserService us= new UserService();

        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                t.setTickets_id(rs.getInt("id"));
                t.setCourse(sc.readById(rs.getInt("course_id")));
                t.setUser_id(us.readById(rs.getInt("user_id")));
                t.setType(rs.getString("type"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM tickets WHERE id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    public  Tickets     ReadbyUser(int userid)
    {
         String req = "select * from tickets where user_id="+userid;
        Tickets t = new Tickets();
        CourseService sc=new CourseService();
        UserService us= new UserService();

        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                t.setTickets_id(rs.getInt("id"));
                t.setCourse(sc.readById(rs.getInt("course_id")));
                t.setUser_id(us.readById(rs.getInt("user_id")));
                t.setType(rs.getString("type"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    
    }
    
    
  
public void readQR(int id){
    TicketService ts = new TicketService();
        
        Tickets t = ts.readById(id);
        
        
        String data = t.toString();
        String path = "C:\\wamp64_3.2\\www\\Soutenance\\web\\FormulaOneWeb_fini\\public\\images\\QRcode\\"+id+".jpg";
        
        BitMatrix mx = null;
        try {
            mx = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
                        
        } catch (WriterException ex) {
            Logger.getLogger(QRcodeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            MatrixToImageWriter.writeToFile(mx, "jpg", new File(path) );
        } catch (IOException ex) {
            Logger.getLogger(QRcodeController.class.getName()).log(Level.SEVERE, null, ex);
        }

}
 public Boolean countTicketsNTUser(int id,course t){
        Boolean v = true;
        int count =0;
        try {
            String requete = "SELECT COUNT(*) FROM tickets WHERE course_id=?";
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            rs = pst.executeQuery(requete);

             if (rs.next()) {
    count = rs.getInt(1); // access first column in result
}
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(count>=t.getCircuit_circuit_id().getCapacite()){
        v=false;
        }
        return v;
    
 }
 public String AfficherPrix(String prix){
     String res = null;
     if("VIP".equals(prix)){
            
            res="100DT";}
        else if("LOGUE".equals(prix)){
            
            res="90DT";}
        else if ("TRIBUNE".equals(prix)){
            
            res="80DT";}
        else if ("VIRAGE DROITE".equals(prix)){
            
            res="50DT";}
        else if("VIRAGE GAUCHE".equals(prix)) {
            
            res="50DT";}
        return res;
     
 
 }
    
    
}
