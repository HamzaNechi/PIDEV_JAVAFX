package service;

import entite.Qualifying;
import entite.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Datasource;

public class QualifyingService implements IServiceA<Qualifying> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public QualifyingService() {
        conn = Datasource.getInstance().getCnx();
    }

    @Override
    public void insert(Qualifying q) {
        String req = "insert into qualifying (q1, q2, q3, position, pilote_id,course_id) values (?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(req, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, q.getQ1());
            pst.setString(2, q.getQ2());
            pst.setString(3, q.getQ3());
            pst.setInt(4, q.getPosition());
            pst.setInt(5, q.getPilote().getPilote_id());
            pst.setInt(6, q.getCourse().getCourse_id());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                q.setQualifying_id(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(QualifyingService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void delete(Qualifying q) {
        String req = "DELETE FROM qualifying WHERE id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, q.getQualifying_id());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public List<Qualifying> filter(Integer year, Integer course_id, Integer pilote_id) {

        String req = ("select * from qualifying q "
                + "join courses c on q.course_id=c.id "
                + "join pilotes pi on pi.id=q.pilote_id "
                + "join membres m on m.id=pi.id where 1=1");

        if (year != null) {
            req = req + (" AND c.saison_year=" + year);
        }
        if (course_id != null) {
            req = req + (" AND c.id =" + course_id);
        }
        if (pilote_id != null) {
            req = req + (" AND pi.id =" + pilote_id);
        }

        List<Qualifying> list = new ArrayList<>();
        PiloteService ps = new PiloteService();
        CourseService cs = new CourseService();
        try {
            ste = conn.createStatement();

            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Qualifying(
                        rs.getInt("id"),
                        rs.getString("q1"),
                        rs.getString("q2"),
                        rs.getString("q3"),
                        rs.getInt("position"),
                        ps.readById1(rs.getInt("pilote_id"))
                )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(QualifyingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void update(Qualifying q) {
        String req = "update qualifying set q1=?, q2=?, q3=?, position=?, pilote_id=?, course_id=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, q.getQ1());
            pst.setString(2, q.getQ2());
            pst.setString(3, q.getQ3());
            pst.setInt(4, q.getPosition());
            pst.setInt(5, q.getPilote().getPilote_id());
            pst.setInt(6, q.getCourse().getCourse_id());
            pst.setInt(7, q.getQualifying_id());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public List<Qualifying> read() {
        String req = "select * from qualifying";
        List<Qualifying> list = new ArrayList<>();
        try {
            PiloteService ps = new PiloteService();
            CourseService cs = new CourseService();
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Qualifying(
                        rs.getInt("id"),
                        rs.getString("q1"),
                        rs.getString("q2"),
                        rs.getString("q3"),
                        rs.getInt("position"),
                        ps.readById1(rs.getInt("pilote_id"))
//                        cs.readById(rs.getInt("course_id"))
                )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(QualifyingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean hasData(course c, Pilote p) throws SQLException {
        String req = "select * from qualifying where pilote_id=" + p.getPilote_id();
        ste = conn.createStatement();
        rs = ste.executeQuery(req);
        return rs.isBeforeFirst();
    }
    
            
    public Qualifying readByCP(course c, Pilote p) {
        String req = "select * from qualifying where pilote_id=" + p.getPilote_id();
        Qualifying q = new Qualifying();
        PiloteService ps = new PiloteService();
        CourseService cs = new CourseService();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                q.setQualifying_id(rs.getInt("id"));
                q.setQ1(rs.getString("q1"));
                q.setQ2(rs.getString("q2"));
                q.setQ3(rs.getString("q3"));
                q.setPilote(ps.readById1(rs.getInt("pilote_id")));
               // q.setCourse(cs.readById(rs.getInt("course_id")));
                q.setPosition(rs.getInt("position"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

    @Override
    public Qualifying readById(int id) {
        String req = "select * from qualifying where id=" + id;
        Qualifying q = new Qualifying();
        PiloteService ps = new PiloteService();
        CourseService cs = new CourseService();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                q.setQualifying_id(rs.getInt("id"));
                q.setQ1(rs.getString("q1"));
                q.setQ2(rs.getString("q2"));
                q.setQ3(rs.getString("q3"));
                q.setPilote(ps.readById1(rs.getInt("pilote_id")));
                q.setPosition(rs.getInt("position"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

}
