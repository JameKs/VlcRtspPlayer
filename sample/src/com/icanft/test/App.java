//package com.icanft.test;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//
///** 
// *  
// * @author LJS 
// *  
// */  
//public class App {  
//    public void createPdf() throws Exception {  
//        // step 1  
//        String inputFile = "index.html";  
//        String url = new File(inputFile).toURI().toURL().toString();  
//        String outputFile = "index.pdf";  
//        System.out.println(url);  
//        // step 2  
//        OutputStream os = new FileOutputStream(outputFile);  
//        org.xhtmlrenderer.pdf.ITextRenderer renderer = new ITextRenderer();  
//        renderer.setDocument(url);  
//  
//        // step 3 解决中文支持  
//        org.xhtmlrenderer.pdf.ITextFontResolver fontResolver = renderer  
//                .getFontResolver();  
//        fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H,     
//                BaseFont.NOT_EMBEDDED);  
//  
//        renderer.layout();  
//        renderer.createPDF(os);  
//        os.close();  
//          
//        System.out.println("create pdf done!!");  
//    }  
//          
//  
//    public static void main(String[] args) throws Exception {  
//        App app = new App();  
//        app.createPdf();  
//    }  
//  
//}  