package client_services;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.util.Matrix;

import application.Notifycation;
import model.ItemBienLai;

public class BillPrinter {
	public void exportBill(ItemBienLai info) {
		 PDDocument doc = new PDDocument();
         try {
        	 float POINTS_PER_INCH = 72;
        	 float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
        	 PDPage page=new PDPage(new PDRectangle(150 * POINTS_PER_MM, 100 * POINTS_PER_MM));
        	 
             doc.addPage(page);

             PDType0Font font = PDType0Font.load(doc,this.getClass().getResourceAsStream("OpenSansCondensed-Light.ttf"), true);

             PDPageContentStream contents = new PDPageContentStream(doc, page);

//             
             contents.setFont(font,16);
             contents.beginText();
             contents.newLineAtOffset(170, 250);
             contents.showText("BIÊN LAI NỘP TIỀN");
             contents.endText();
//             
//             
             contents.setFont(font, 8);
             
             contents.beginText();
             contents.newLineAtOffset(190,235);
             contents.showText("QD-XPHC số:");
             contents.endText();
             
       
//             
             contents.beginText();
             contents.newLineAtOffset(15, 255);
             contents.showText("Số hóa đơn:");
             contents.endText();
             contents.beginText();
             contents.newLineAtOffset(15,240);
             contents.showText("Ngày lập hóa đơn:");
             contents.endText();
             
             contents.beginText();
             contents.newLineAtOffset(230,235);
             contents.showText(info.getSoQDXPHC()+"");
             contents.endText();
             contents.beginText();
             contents.newLineAtOffset(70, 255);
             contents.showText(info.getMaBienLai()+"");
             contents.endText();
             contents.beginText();
             contents.newLineAtOffset(70,240);
             contents.showText(info.getNgayLapBienLai().toString());
             contents.endText();
             
             
             contents.setFont(font, 9);
             
             
             contents.beginText();
             contents.newLineAtOffset(55,170);
             contents.showText("Tên người nộp tiền:");
             contents.endText();
             
             contents.beginText();
             contents.newLineAtOffset(55,150);
             contents.showText("Số tiền nộp:");
             contents.endText();
             
             contents.beginText();
             contents.newLineAtOffset(55,130);
             contents.showText("Lý do nộp tiền:");
             contents.endText();
             
             
             contents.beginText();
             contents.newLineAtOffset(130,170);
             contents.showText(info.getHoTenNguoiNopTien());
             contents.endText();
             
             contents.beginText();
             contents.newLineAtOffset(130,150);
             contents.showText(info.getSoTienNop()+"");
             contents.endText();
             
             contents.beginText();
             contents.newLineAtOffset(130,130);
             contents.showText(info.getLyDoNopPhat());
             contents.endText();
             
             
             
             
             contents.beginText();
             contents.newLineAtOffset(100,60);
             contents.showText("Người đóng tiền");
             contents.endText();
             
             contents.beginText();
             contents.newLineAtOffset(280,60);
             contents.showText("Nhân viên lập hóa đơn");
             contents.endText();
             
             
             contents.beginText();
             contents.newLineAtOffset(290,20);
             contents.showText(info.getHoTenCanBo());
             contents.endText();
             
             
             
             
//             contents.beginText();
//             contents.newLineAtOffset(50,210);
//             contents.showText("Nhân viên thanh toán:");
//             contents.endText();
//             
//             contents.beginText();
//             contents.newLineAtOffset(200, 700);
//             contents.showText(invoiceTable.getValueAt(row,1).toString());
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(200,680);
//             contents.showText(invoiceTable.getValueAt(row,0).toString());
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(200,660);
//             contents.showText(invoiceTable.getValueAt(row,2).toString());
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(200,640);
//             contents.showText(invoiceTable.getValueAt(row,3).toString());
//             contents.endText();
//             
//             contents.setFont(font,11);
//             
            contents.setLineWidth(0.15f);
            contents.drawLine(30, 200, 400, 200);
            contents.drawLine(30,80,400,80);
//            
            contents.drawLine(30, 200,30, 80);
            contents.drawLine(400,200,400,80);
//            contents.drawLine(30,100,570,100);
//            
//            contents.drawLine(85, 600, 85, 130);
//            contents.drawLine(255, 600, 255, 130);
//            contents.drawLine(355, 600, 355, 130);
//            contents.drawLine(425,600,425,130);
//            contents.drawLine(485,600,485,130);
//            
//            contents.drawLine(30,130,570,130);
//             
//             contents.beginText();
//             contents.newLineAtOffset(35,580);
//             contents.showText("Mã vật tư");
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(90,580);
//             contents.showText("Tên vật tư");
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(260,580);
//             contents.showText("Đơn vị tính");
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(360,580);
//             contents.showText("Số luọng");
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(430,580);
//             contents.showText("Đơn giá");
//             contents.endText();
//             contents.beginText();
//             contents.newLineAtOffset(490,580);
//             contents.showText("Thành tiền");
//             contents.endText();
//             
//             contents.beginText();
//             contents.newLineAtOffset(420,110);
//             contents.showText("Tổng:");
//             contents.endText();
//             
//             contents.setFont(font, 9);
//              DefaultTableModel mode=(DefaultTableModel) this.table.getModel();
//             try{
//                  Connection connection=User.getInstance().getConnection();
//                  PreparedStatement query=connection.prepareStatement("select * from CTPX,VATTU where CTPX.MAPX=? AND CTPX.MAVT=VATTU.MAVT");
//                  query.setString(1,invoiceTable.getValueAt(row,1).toString());
//                  ResultSet re=query.executeQuery();
//                  int cnt=-1;
//                
//                  String s;
//                  while(re.next()){
//                     Vector v=new Vector();
//                     contents.beginText();
//                     contents.newLineAtOffset(40,570+cnt*20);
//                     s=re.getString("MAVT");
//                     contents.showText(s);
//                     v.add(s);
//                     contents.endText();
//                     contents.beginText();
//                     contents.newLineAtOffset(90,570+cnt*20);
//                     contents.showText(s=re.getString("TENVT"));
//                     v.add(s);
//                     contents.endText();
//                     contents.beginText();
//                     contents.newLineAtOffset(260,570+cnt*20);
//                     contents.showText(s=re.getString("DVT"));
//                     v.add(s);
//                     contents.endText();
//                     contents.beginText();
//                     contents.newLineAtOffset(360,570+cnt*20);
//                     contents.showText(s=re.getString("SOLUONG"));
//                     v.add(s);
//                     contents.endText();
//                     contents.beginText();
//                     contents.newLineAtOffset(430,570+cnt*20);
//                     contents.showText(s=re.getString("DONGIA"));
//                     v.add(s);
//                     contents.endText();
//                     contents.beginText();
//                     contents.newLineAtOffset(490,570+cnt*20);
//                     contents.showText(s=Float.toString(re.getInt("SOLUONG")*re.getFloat("DONGIA")));
//                     v.add(s);
//                     contents.endText();
//                    
//                     mode.addRow(v);
//                     --cnt;
//                  }
//                  table.setModel(mode);
//             }catch(SQLException ex){
//                 JOptionPane.showMessageDialog(this,"Lấy dữ liệu thất bại");
//             }
              contents.setFont(font,11);
             contents.beginText();
             contents.newLineAtOffset(490,110);
//             contents.showText(invoiceTable.getValueAt(row,4).toString());
             contents.endText();
             
             
             
             contents.close();

             doc.save("Invoice.pdf");
             doc.close();
             
             
             
             File file = new File("Invoice.pdf");
             
             //first check if Desktop is supported by Platform or not
             if(!Desktop.isDesktopSupported()){
            	 Notifycation.error("Không hỗ trợ file này");
                 return;
             }
             Desktop desktop = Desktop.getDesktop();
             if(file.exists()) desktop.open(file);

         }
         catch(IOException ex){
        	 ex.printStackTrace();
         }

	}
}
