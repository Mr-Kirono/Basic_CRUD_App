import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

//DBIT CRUD APP BY: 147121 MICHAEL KIRONO
public class Student {
    //Ui components
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtID;
    private JTextField txtCourse;
    private JButton saveButton;
    private JTable table2;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JScrollPane table;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student");
        frame.setContentPane(new Student().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    String name,idNo,course;
    public void connection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/schooldb","root","");
            System.out.println("Success!");
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SQLException ex) {
        }
    }
    //This block of code is responsible for viewing records
    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from student");
            ResultSet rs = pst.executeQuery();
            table2.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public Student() {
        connection();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,idNo,course;
                name = txtName.getText();
                idNo = txtID.getText();
                course = txtCourse.getText();

                //This inserts data into the 'student' table
                try {
                    pst = con.prepareStatement("insert into student(NAME,ID_NO,COURSE)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, idNo);
                    pst.setString(3, course);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record has been added!!!!!");
                    table_load();
                    txtName.setText("");
                    txtID.setText("");
                    txtCourse.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        //Edit records
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = txtName.getText();
                idNo = txtID.getText();
                course = txtCourse.getText();
                try {

                    pst = con.prepareStatement("update student set NAME = ?,COURSE = ? where ID_NO = ?");
                    pst.setString(1, name);
                    pst.setString(2, course);
                    pst.setString(3, idNo);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");
                    table_load();
                    txtName.setText("");
                    txtID.setText("");
                    txtCourse.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //Delete records
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = txtName.getText();
                idNo = txtID.getText();
                course = txtCourse.getText();
                try {
                pst = con.prepareStatement("delete from student  where ID_NO = ?");

                pst.setString(1, idNo);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Deleted!");
                table_load();
                txtName.setText("");
                txtID.setText("");
                txtCourse.setText("");
                txtName.requestFocus();
            }
            catch (SQLException e1)
            {

                e1.printStackTrace();
            }
        }
        });
    }
}
