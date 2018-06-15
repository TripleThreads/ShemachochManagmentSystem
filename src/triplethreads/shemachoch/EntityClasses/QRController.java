package triplethreads.shemachoch.EntityClasses;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
//This is QRController class it will generate qr image from user data
//I renamed it to QRGenerator since it has no other job to do except generating qr code
//we are not gonna scan qr code here so I left it

public class QRController {
    public static void generateQRCodeImage(ArrayList<String> userInfo){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String Qr_code_image_path = "./"+userInfo.get(0);
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(userInfo.get(1), BarcodeFormat.QR_CODE, 100,100);
            Path path = FileSystems.getDefault().getPath(Qr_code_image_path);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println("Image is generated :) "+ new File(Qr_code_image_path+".png").getAbsoluteFile().getCanonicalFile());
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

