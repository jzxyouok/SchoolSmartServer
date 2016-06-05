package tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by Administrator on 2016/5/11.
 */

public class TypeConverter {

        //鍥剧墖杞寲鎴恇ase64瀛楃涓�
        public static String GetImageStr(String path)
        {//灏嗗浘鐗囨枃浠惰浆鍖栦负瀛楄妭鏁扮粍瀛楃涓诧紝骞跺鍏惰繘琛孊ase64缂栫爜澶勭悊
            String imgFile = path;//寰呭鐞嗙殑鍥剧墖
            InputStream in = null;
            byte[] data = null;
            //璇诲彇鍥剧墖瀛楄妭鏁扮粍
            try
            {
                in = new FileInputStream(imgFile);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //瀵瑰瓧鑺傛暟缁凚ase64缂栫爜
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);//杩斿洖Base64缂栫爜杩囩殑瀛楄妭鏁扮粍瀛楃涓�
        }

        //base64瀛楃涓茶浆鍖栨垚鍥剧墖
        public static boolean GenerateImage(String imgStr,String imgName)
        {   //瀵瑰瓧鑺傛暟缁勫瓧绗︿覆杩涜Base64瑙ｇ爜骞剁敓鎴愬浘鐗�
            if (imgStr == null) //鍥惧儚鏁版嵁涓虹┖
                return false;
            BASE64Decoder decoder = new BASE64Decoder();
            try
            {
                //Base64瑙ｇ爜
                byte[] b = decoder.decodeBuffer(imgStr);
                for(int i=0;i<b.length;++i)
                {
                    if(b[i]<0)
                    {//璋冩暣寮傚父鏁版嵁
                        b[i]+=256;
                    }
                }
                //鐢熸垚jpeg鍥剧墖
                String imgFilePath = "C:/inetpub/wwwroot/school_mart/"+imgName;//鏂扮敓鎴愮殑鍥剧墖
                OutputStream out = new FileOutputStream(imgFilePath);
                out.write(b);
                out.flush();
                out.close();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }

}