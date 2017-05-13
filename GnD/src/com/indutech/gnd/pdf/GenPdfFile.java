package com.indutech.gnd.pdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenPdfFile {

	private Document doc;
	private PdfWriter writer;
	private static Font catFont;
	private BufferedReader br;
	
	public void createPdf(String filename) throws DocumentException	{
		String line;
//		File file=new File("F:/gnd/WORK/REPORT/Appp.pdf");
		File file=new File(filename);
		catFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
		doc=new Document();	
			try {
			
				writer= PdfWriter.getInstance(doc, new FileOutputStream(file));
//				br=new BufferedReader(new FileReader(filename));
//					while((line=br.readLine())!=null)	{
//						writeToPdf(line);
//					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	public boolean writeToPdf(Paragraph par) throws DocumentException	{
		doc.open();
		try {
			doc.add(par);
		}
		finally{
 	  		doc.close();
 	  		writer.close();
         }	
		return true;
	}

	public boolean writeToPdf(PdfPTable par) throws DocumentException	{
		doc.open();
		try {
			doc.add(par);
		}
		finally{
 	  		doc.close();
 	  		writer.close();
         }	
		return true;
	}

	public void openPdf()	{
  		doc.open();
	}
	public boolean writeToPdf(String str) throws DocumentException	{
//		doc.open();
		try {
			Paragraph par = new Paragraph();
			par.add(new Paragraph(str,catFont));
			doc.add(par);
		}
		finally{
// 	  		doc.close();
// 	  		writer.close();
         }	
		return true;
	}
	
	public void closePdf()	{
	  		doc.close();
	  		writer.close();
	}
	
}
