package com.indutech.gnd.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PinMailerReport {

	private Paragraph par;	
	private Paragraph par1;
	private static Font catFont;
	private PdfPTable table;
	private PdfPCell c1,c2,c3,c4,c5;
	private GenPdfFile pd;
	
	public void generateReport()	{
		 pd=new GenPdfFile();
		try {
			pd.createPdf("F:/GNDPdf/Information.pdf");
			header( );
			bankAddress( );
			bodyOfContent( );
			taliOfContent();
		} 
		catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void header() throws DocumentException	{
		par=new Paragraph();
		par.add(new Paragraph("Page:2001..1",catFont));
		par.add(Chunk.NEWLINE);
		par.add(new Paragraph("LCBC: SBT     BRANCH CODE : 50894",catFont));
		par.add(new Paragraph("AWD NO.: PA37361688IN",catFont));
		par.add(Chunk.NEWLINE);
		pd.writeToPdf(par);
		
	}
	public void bankAddress()  throws DocumentException	{
		par=new Paragraph();
		par.add(new Paragraph("The Bank Manager\n"
				   +"State Bank Of Travancore\n"
				   +"UKLANA\n"
				   +"CHABIL DASS COLONY, OPPOSITE OLD MARVALE FACTORY UKLANA-125113",catFont));
		par.add(Chunk.NEWLINE);
		pd.writeToPdf(par);
	}
	
	public void bodyOfContent()	throws DocumentException {
		par=new Paragraph();
		par1=new Paragraph();
	
		par.add(new Paragraph("125113\n\n"
				  +"Subject : PIN Dispatch for branch UKLANA Date : 15 oct 2015\n\n"
				  +"Dear Sir,\n"
				  +"Please find enclosed 1-2 of 2 records pertaining to debit card PINs of your\n"
				  +"branch from file TB010911 dated 11/01/2009.\n"
				  +"The coustomer details are as below:\n\n"	
				  +"Product Type : DOMESTIC",catFont));
	   par.add("---------------------------------------------------------------------------------------------------------------------------");
	   pd.writeToPdf(par);	
	   
	   createTable();	
	   par1.add("---------------------------------------------------------------------------------------------------------------------------");
	   par1.add(new Paragraph("The cards for these accounts have already been dispatched. we would request you" 
					+"to please handover the same to the custmer at the earliest. In case of future "
					+"clarifications, please get in touch with us on the below mentioned numbers. "
					+"For dispatch related queries on Card/PIN and Re-PIN, kindly check the details on "
					+"our web page:http://10.0.1.218.8080/SBI-Query/index.jsp ",catFont));
	   par1.add(Chunk.NEWLINE);
	   pd.writeToPdf(par1);	
	}
	
	public void createTable() throws DocumentException	{
			table=new PdfPTable(5);	
			float[] columnWidths = {1f, 2f, 2f,2f,2f};
			
			table.setWidthPercentage(100);
			table.setWidths(columnWidths);
			
			c1=new PdfPCell(new Phrase("SerNo",catFont));
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
			c2=new PdfPCell(new Phrase("Emboss Name",catFont));
			c2.setBorder(Rectangle.NO_BORDER);
			table.addCell(c2);

			c3=new PdfPCell(new Phrase("Contact No",catFont));
			c3.setBorder(Rectangle.NO_BORDER);
			table.addCell(c3);

			c4=new PdfPCell(new Phrase("Account No",catFont));
			c4.setBorder(Rectangle.NO_BORDER);
			table.addCell(c4);

			c5=new PdfPCell(new Phrase("Cust Signature",catFont));
			c5.setBorder(Rectangle.NO_BORDER);
			table.addCell(c5);
			pd.writeToPdf(table);
		
	}
	public void taliOfContent()	throws DocumentException {
		par1=new Paragraph();	
		par1.add(new Paragraph("Thank You,\n"
			+"SBI ATM - Team\n"
			+"MORPHO INDIA PVT. LTD.\n"
			+"WORKS: State Bank Global IT Center,\n"
			+"Plot No.8, 9 & 10,Sector 11,\n"
			+"C B D Belapur, Navi Mumbai 400014.\n"
			+"For queries please contact: 0120-4344923 Or\n"
			+"                   E-mail us at: xyz@morpho.com",catFont));
		pd.writeToPdf(par);
	}
	
	public static void main(String[] args)
	{

	}
}
