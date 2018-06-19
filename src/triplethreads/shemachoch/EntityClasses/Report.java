package triplethreads.shemachoch.EntityClasses;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
public class Report {
    private String fileName , image;
    private  String[] monthsEng;
    private Path path;
    private int month;
    private PDFont fontBody= PDType1Font.HELVETICA;
    private Connection con=null;
    public ArrayList<TableContent> transaction= new ArrayList<>();


    public Report() {

        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        this.monthsEng = new String[]{"Meskerem","Tikemt","Hidar","Tahsas","Tir","Yekatit","Megabit","Miyazia","Gunbot","Sene","Hamle","Nehase","Pagumen"};
        this.con=ConnectionHandler.INSTANCE.getConnection();

    }

    public void generateReport(int month) throws SQLException{
        double salePercent=0, totalSale=0,price=0,total=0;
        String name="";
        this.month=month;
        Statement sqlStatement=con.createStatement();
        String s = "select * from soldItems ";
        PreparedStatement ps = con.prepareStatement(s);
       // ps.setString(1,"0"+month);
        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()){
            String itemID=resultSet.getString("itemID");
            double amount=resultSet.getDouble("amount");
            Date lastSale=resultSet.getDate("saleDate");

            String s1 = "select * from shemt.items where itemID = ?";
            PreparedStatement ps1 = con.prepareStatement(s1);
            ps1.setString(1,itemID);
            ResultSet resultSet1 = ps1.executeQuery();
            while(resultSet1.next()) {
                 total = resultSet1.getDouble("amountTotal");
                 name = resultSet1.getString("itemName");
                 salePercent = Math.round( (amount / total) * 100 );
                 totalSale = resultSet1.getDouble("itemPrice") * amount;
                 price = resultSet1.getDouble("itemPrice");

                transaction.add(new TableContent( name,  price,  salePercent,  totalSale,  lastSale));
            }



        }




    }

    private PDDocument generateDOC(int month){

        fileName = "Report_month_" + String.valueOf(month) +".pdf";
        image="Logo.png";
        PDDocument document = new PDDocument();
        return document;

    }
    private void addContent(int month) throws IOException{
        PDDocument doc = generateDOC(month);
        PDPage page = new PDPage();
        doc.addPage(page);
        PDImageXObject pdImage = PDImageXObject.createFromFile(image,doc);
        PDPageContentStream content=new PDPageContentStream(doc,page);

        //header part
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 23);
        content.moveTextPositionByAmount(150, 750);
        content.drawString("Shemachoch Stores,Addis Ababa Ethiopia" );
        content.endText();
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 26);
        content.moveTextPositionByAmount(250, 710);
        content.drawString("Report for month " +monthsEng[month-1]);
        content.endText();

        //content part
        content.beginText();

        content.setFont(PDType1Font.HELVETICA, 14);
        content.moveTextPositionByAmount(100, 650);
        content.drawString("Name      Price     SalePercent         TotalSale          LastSale");
        content.endText();
        int ty=620;
        //for(int i=0;i <9; i++){
        for (int i=0;i<transaction.size(); i++){
            content.beginText();

            content.setFont(PDType1Font.HELVETICA, 12);
            content.moveTextPositionByAmount(100, ty);
            content.newLine();
            TableContent tableContent=transaction.get(i);
            String data=tableContent.getName()+"            "+tableContent.getPrice()+"          "+tableContent.getSalePercent()+"%                "+
                    tableContent.getTotalSale()+"                  "+tableContent.getLastSale();
            content.drawString(data);
            content.endText();
            ty -= 20;
        }


        content.drawImage(pdImage, 50, 700);



        content.close();
        doc.save(fileName);

    }
    public static void main(String[] arg){
        Report r=new Report();
        try {
            r.generateReport(3);
            r.addContent(3);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    class TableContent{

        String name;
        double price;
        double salePercent;
        double totalSale;
        Date lastSale;


        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public double getSalePercent() {
            return salePercent;
        }

        public double getTotalSale() {
            return totalSale;
        }

        public Date getLastSale() {
            return lastSale;
        }

        public TableContent(String name, double price, double salePercent, double totalSale, Date lastSale) {
            this.name=name;
            this.price=price;
            this.salePercent=salePercent;
            this.lastSale=lastSale;
            this.totalSale=totalSale;

        }
    }


}
