import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
public class ReportCardGenerator
{
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    static Scanner sc = new Scanner(System.in);
    BufferedImage img;
    GetPass gp = new GetPass();
    public void addStudentDetails()
    {
        String stuname, fathname, mothname, q;
        long mobno, rollno;
        float dcmarks, javamarks, algomarks, immarks, swmarks, cspmarks;
        int ok;
        do {
            System.out.println("Enter Student Data:");
            stuname = sc.nextLine();
            System.out.println("Enter Student Name: ");
            stuname = sc.nextLine();
            System.out.println("Enter "+stuname+"'s Father's Name: ");
            fathname = sc.nextLine();
            System.out.println("Enter "+stuname+"'s Mother's Name: ");
            mothname = sc.nextLine();
            System.out.println("Enter "+stuname+"'s Roll Number: ");
            rollno = sc.nextLong();
            System.out.println("Enter Father's or Mother's Mobile Number: ");
            mobno = sc.nextLong();
            System.out.println("Enter "+stuname+"'s marks in following subjects(out of 100): ");
            System.out.print("Algorithm Design and Application: ");
            algomarks = sc.nextFloat();
            System.out.print("Digital Communication: ");
            dcmarks = sc.nextFloat();
            System.out.print("Java Programming: ");
            javamarks = sc.nextFloat();
            System.out.print("Industrial Management: ");
            immarks = sc.nextFloat();
            System.out.print("Software Engineering: ");
            swmarks = sc.nextFloat();
            System.out.print("Communication Skills for Professionals: ");
            cspmarks = sc.nextFloat();
            System.out.println("\nYou Entered Following Information: ");
            System.out.println("Student's Name: "+stuname+"\nFather's Name: "+fathname+"\nMother's Name: "+mothname);
            System.out.println("Roll Number: "+rollno+"\nMobile Number: "+mobno+"\nMarks in each Subject:");
            System.out.println("Algorithm Design and Analysis: "+algomarks+"\nDigital Communication: "+dcmarks);
            System.out.println("Java Programming: "+javamarks+"\nIndustrial Management: "+immarks);
            System.out.println("Software Engineering: "+swmarks+"\nCommunication Skills for Professionals: "+cspmarks);
            System.out.println("Press 1 if you want to change the data else press 0 to save the data");
            ok = sc.nextInt();
        }while(ok==1);
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root",gp.pass);
            st = conn.createStatement();
            q = "insert into studata values('"+stuname+"','"+fathname+"','"+mothname+"',"+rollno+","+mobno+","+algomarks+","+dcmarks+","+javamarks+","+immarks+","+swmarks+","+cspmarks+")";
            st.execute(q);
        }catch(Exception ee){System.out.println("Error saving data: "+ee);}
    }
    public void getReportCard()
    {
        long rollno;
        String stuname="", fathname="", mothname="", sturollno="", mobno="", algomarks="", dcmarks="", javamarks="", immarks="", swmarks="", cspmarks="";
        System.out.println("Enter the Roll Number of the student whose Report Card is to be generated: ");
        rollno = sc.nextLong();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root",gp.pass);
            st = conn.createStatement();
            String q = "select * from studata where rollno ="+rollno+"";
            rs = st.executeQuery(q);
            while(rs.next()){
                stuname = rs.getString("stuname");
                fathname = rs.getString("fathname");
                mothname = rs.getString("mothname");
                sturollno = rs.getString("rollno");
                mobno = rs.getString("mobno");
                algomarks = rs.getString("algomarks");
                dcmarks = rs.getString("dcmarks");
                javamarks = rs.getString("javamarks");
                immarks = rs.getString("immarks");
                swmarks = rs.getString("swmarks");
                cspmarks = rs.getString("cspmarks");
            }
        }catch (Exception ee){System.out.println("Cannot get Report card: "+ee);}
        if(stuname=="")
        {
            System.out.println("Cannot get Report Card");
        }
        else
        {
            Frame card = new Frame(stuname+"'s Report Card");
            card.setLayout(null);
            card.setVisible(true);
            card.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    card.setVisible(false);
                }
            });
            card.setBounds(460, 10,1005, 1030);
            try
            {
                img = ImageIO.read(new File("reportcardimg.png"));
                Graphics g = card.getGraphics();
                g.drawImage(img, 5, 30, 1000, 1000, null);
            }catch (Exception ee){}
            Label stname = new Label("Student's Name:");
            Font font = new Font("Times New Roman", Font.BOLD, 30);
            Font foont = new Font("Times New Roman", Font.BOLD, 20);
            stname.setFont(font);
            card.add(stname);
            Color col = new Color(180,230,5);
            stname.setBackground(col);
            stname.setBounds(150,180, 250, 35);
            Label name = new Label(stuname);
            name.setFont(font);
            card.add(name);
            name.setBackground(col);
            name.setBounds(420,180, 450, 35);
            Label fathername = new Label("Father's Name:");
            fathername.setFont(foont);
            fathername.setBackground(col);
            card.add(fathername);
            fathername.setBounds(80,255,145,25);
            Label namefath = new Label(fathname);
            namefath.setFont(foont);
            namefath.setBackground(col);
            card.add(namefath);
            namefath.setBounds(230,255, 250, 25);
            Label mothername = new Label("Mother's Name:");
            mothername.setFont(foont);
            mothername.setBackground(col);
            card.add(mothername);
            mothername.setBounds(515,255,150,25);
            Label namemoth = new Label(mothname);
            namemoth.setFont(foont);
            namemoth.setBackground(col);
            card.add(namemoth);
            namemoth.setBounds(670,255, 250, 25);
            Label rolllno = new Label("Roll Number: ");
            rolllno.setFont(foont);
            rolllno.setBackground(col);
            card.add(rolllno);
            rolllno.setBounds(80,325,145,25);
            Label noroll = new Label("0"+sturollno);
            noroll.setFont(foont);
            noroll.setBackground(col);
            card.add(noroll);
            noroll.setBounds(230,325,250,25);
            Label mobnum = new Label("Mobile Number:");
            mobnum.setFont(foont);
            mobnum.setBackground(col);
            card.add(mobnum);
            mobnum.setBounds(515, 325,150,25);
            Label nummob = new Label(mobno);
            nummob.setFont(foont);
            nummob.setBackground(col);
            card.add(nummob);
            nummob.setBounds(670,325,250,25);
            Label marks[] = new Label[6];
            marks[0] = new Label(algomarks);
            marks[1] = new Label(dcmarks);
            marks[2] = new Label(javamarks);
            marks[3] = new Label(immarks);
            marks[4] = new Label(swmarks);
            marks[5] = new Label(cspmarks);
            int y = 380;
            for(int i=0; i<6; i++)
            {
                marks[i].setFont(new Font("Showcard Gothic", Font.BOLD, 30));
                marks[i].setBackground(col);
                card.add(marks[i]);
                marks[i].setBounds(680, y+83,100,35);
                y=y+83;
            }
            float total = Float.parseFloat(algomarks)+Float.parseFloat(dcmarks)+Float.parseFloat(javamarks);
            total = total + Float.parseFloat(immarks)+Float.parseFloat(swmarks)+Float.parseFloat(cspmarks);
            Label totalmarks = new Label(Float.toString(total)+"/600");
            totalmarks.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
            totalmarks.setBackground(col);
            card.add(totalmarks);
            totalmarks.setBounds(320,950,180,35);
            Label percen = new Label(String.format("%.2f", total/6)+" %");
            percen.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
            percen.setBackground(col);
            card.add(percen);
            percen.setBounds(760, 950,150,35);
        }
    }
    public void show()
    {
        System.out.println("Enter the roll number of the student: ");
        long rollno;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        rollno = sc.nextLong();
        try
        {
            String stuname="", fathname="", mothname="", mobno="";
            float algomarks=0, dcmarks=0, javamarks=0, immarks=0, swmarks=0, cspmarks=0;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", gp.pass);
            st = conn.createStatement();
            String q = "select * from studata where rollno = "+rollno;
            rs = st.executeQuery(q);
            while(rs.next())
            {
                stuname = rs.getString("stuname");
                fathname = rs.getString("fathname");
                mothname = rs.getString("mothname");
                mobno = rs.getString("mobno");
                algomarks = rs.getFloat("algomarks");
                dcmarks = rs.getFloat("dcmarks");
                javamarks = rs.getFloat("javamarks");
                immarks = rs.getFloat("immarks");
                swmarks = rs.getFloat("swmarks");
                cspmarks = rs.getFloat("cspmarks");
            }
            if(stuname=="")
            {
                System.out.println("No Record Present");
            }
            else
            {
                   Frame detail = new Frame("Studnet Details");
                   detail.setVisible(true);
                   detail.setLayout(null);
                   detail.setBounds(d.width/2-300, d.height/2-300,600,600);
                   detail.addWindowListener(new WindowAdapter() {
                       @Override
                       public void windowClosing(WindowEvent e) {
                           detail.setVisible(false);
                        }
                   });
                   try
                   {
                       BufferedImage image = ImageIO.read(new File("back.png"));
                       Graphics g = detail.getGraphics();
                       g.drawImage(image, 0, 0, 600, 600, null);
                   }catch (Exception ee){System.out.println("HaHa");}
                   Label stunamee = new Label("Student Name: "+stuname);
                   Label fathnamee = new Label("Father's Name: "+fathname);
                   Label mothnamee = new Label("Mother's Name: "+mothname);
                   Label rollnoo = new Label("Roll Number: 0"+String.valueOf(rollno));
                   Label mobnoo = new Label("Mobile Number: "+mobno);
                   Label marks = new Label(stuname+"'s Marks: ");
                   Label algo = new Label("Algorith Design and Analysis: "+algomarks);
                   Label dc = new Label("Digital Communication: "+dcmarks);
                   Label java = new Label("Java Programming: "+javamarks);
                   Label im = new Label("Industrila Management: "+immarks);
                   Label sw = new Label("Software Engineering: "+swmarks);
                   Label csp = new Label("Comm. Skills For Professionals: "+cspmarks);
                   detail.add(stunamee);
                   detail.add(fathnamee);
                   detail.add(mothnamee);
                   detail.add(rollnoo);
                   detail.add(mobnoo);
                   detail.add(marks);
                   detail.add(algo);
                   detail.add(dc);
                   detail.add(java);
                   detail.add(im);
                   detail.add(sw);
                   detail.add(csp);
                   Font f = new Font("Segoe Print", Font.BOLD, 25);
                   stunamee.setFont(f);
                   mothnamee.setFont(f);
                   fathnamee.setFont(f);
                   rollnoo.setFont(f);
                   mobnoo.setFont(f);
                   marks.setFont(f);
                   algo.setFont(f);
                   dc.setFont(f);
                   java.setFont(f);
                   im.setFont(f);
                   sw.setFont(f);
                   csp.setFont(f);
                   Color colo = Color.yellow;
                   stunamee.setBackground(colo);
                   fathnamee.setBackground(colo);
                   mothnamee.setBackground(colo);
                   rollnoo.setBackground(colo);
                   mobnoo.setBackground(colo);
                   marks.setBackground(colo);
                   algo.setBackground(colo);
                   dc.setBackground(colo);
                   java.setBackground(colo);
                   im.setBackground(colo);
                   sw.setBackground(colo);
                   csp.setBackground(colo);
                    stunamee.setBounds( 20,40,560,35);
                    fathnamee.setBounds( 20,80,560,35);
                    mothnamee.setBounds( 20,120,560,35);
                    rollnoo.setBounds( 20,160,560,35);
                    mobnoo.setBounds( 20,200,560,35);
                    marks.setBounds(20,260,560,35);
                    algo.setBounds( 20,320,560,35);
                    dc.setBounds( 20,360,560,35);
                    java.setBounds( 20,400,560,35);
                    im.setBounds( 20,440,560,35);
                    sw.setBounds( 20,480,560,35);
                    csp.setBounds( 20,520,560,35);
            }
        }catch(Exception ee){System.out.println("Cannot Show Details: "+ee);}
    }
    public static void main(String sp[])
    {
        int choice;
        ReportCardGenerator rp = new ReportCardGenerator();
        do {
            System.out.println("This is Student Report Card Generator");
            System.out.println("1.) Add Student Details\n2.) Get Student Report Card");
            System.out.println("3.) Get Student Details\n4.) Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice)
            {
                case 1:    rp.addStudentDetails();
                           break;
                case 2:    rp.getReportCard();
                           break;
                case 3:    rp.show();
                            break;
                case 4:    System.out.println("Exit!!");
                           break;
                default:    System.out.println("Enter Valid Choice!");
                            break;
            }
        }while(choice != 4);
    }
}
