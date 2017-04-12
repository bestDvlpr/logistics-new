//package uz.hasan.invoice;
//
//import fr.opensagres.xdocreport.core.XDocReportException;
//import fr.opensagres.xdocreport.template.TemplateEngineKind;
//import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
//import uz.hasan.service.dto.ClientDTO;
//import uz.hasan.service.dto.ProductEntryDTO;
//import uz.hasan.service.dto.ShopDTO;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
///**
// * Created by admmp on 06.04.2017.
// */
//public class GenerateXMLFields {
//
//    public static void main (String[]args) throws XDocReportException,IOException {
//
//        // 1) Create FieldsMetadata by setting Velocity as template engine
//        FieldsMetadata fieldsMetadata = new FieldsMetadata(TemplateEngineKind.Velocity.name());
//
//        // 2) Load fields metadata from Java Class
////        fieldsMetadata.load("invoice", Invoice.class);
////        fieldsMetadata.load("shopDTO", ShopDTO.class);
////        fieldsMetadata.load("clientDTO", ClientDTO.class);
//        /* add shop fields*/
//        fieldsMetadata.addField("shopAddress", false, "","", false);
//        fieldsMetadata.addField("shopBankAccountNumber", false, "","", false);
//        fieldsMetadata.addField("shopBankBranchRegion", false, "","", false);
//        fieldsMetadata.addField("shopBankName", false, "","", false);
//        fieldsMetadata.addField("shopBankMfo", false, "","", false);
//        fieldsMetadata.addField("shopName", false, "","", false);
//        fieldsMetadata.addField("shopOkonx", false, "","", false);
//        fieldsMetadata.addField("shopOked", false, "","", false);
//        fieldsMetadata.addField("shopTin", false, "","", false);
//        fieldsMetadata.addField("shopPhoneNumbers", false, "","", false);
//        /* add client fields*/
//        fieldsMetadata.addField("clientAddress", false, "","", false);
//        fieldsMetadata.addField("clientBankAccountNumber", false, "","", false);
//        fieldsMetadata.addField("clientBankBranchRegion", false, "","", false);
//        fieldsMetadata.addField("clientBankName", false, "","", false);
//        fieldsMetadata.addField("clientBankMfo", false, "","", false);
//        fieldsMetadata.addField("clientName", false, "","", false);
//        fieldsMetadata.addField("clientOkonx", false, "","", false);
//        fieldsMetadata.addField("clientOked", false, "","", false);
//        fieldsMetadata.addField("clientTin", false, "","", false);
//        fieldsMetadata.addField("clientFirstName", false, "","", false);
//        fieldsMetadata.addField("clientLastName", false, "","", false);
//        fieldsMetadata.addField("clientPhoneNumbers", true, "","", false);
//        fieldsMetadata.addField("sumPrice", false, "","", false);
//        fieldsMetadata.addField("receiptDocId", false, "","", false);
//        fieldsMetadata.addField("receiptId", false, "","", false);
//        fieldsMetadata.addField("deliveryStartTime", false, "","", false);
//        fieldsMetadata.addField("fromTime", false, "","", false);
//        fieldsMetadata.addField("toTime", false, "","", false);
//        fieldsMetadata.addField("deliveryDate", false, "","", false);
//        /* add product entry fields*/
//        fieldsMetadata.load("product", Product.class, true);
//            /*
//	*/
//
//        // Here load is called with true because model is a list of Developer.
//        // 3) Generate XML fields in the file "project.fields.xml".
//        // Extension *.fields.xml is very important to use it with MS Macro XDocReport.dotm
//        // FieldsMetadata#saveXML is called with true to indent the XML.
//        File xmlFieldsFile = new File("project.fields.xml");
//        fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);
//        xmlFieldsFile = new File("docs\\project.fields.xml");
//        fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);
//    }
//}
