package com.pdfread.standardreader.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloader {  
    private URL url=null;  
    FileUtils fileUtils=new FileUtils();  
    public int downfile(String urlStr,String path,String fileName)  
    {  
        if(fileUtils.isFileExist(path+fileName))  
            {return 1;}  
        else{  
          
        try {  
            InputStream input=null;  
            input = getInputStream(urlStr);  
            File resultFile=fileUtils.write2SDFromInput(path, fileName, input);  
            if(resultFile==null)  
            {  
                return -1;  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
        }  
        return 0;  
    }  
       public InputStream getInputStream(String urlStr) throws IOException
       {       
           InputStream is=null;  
            try {  
                url=new URL(urlStr);
                HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();  
                is=urlConn.getInputStream();
                  
            } catch (MalformedURLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
              
            return is;  
       }  
}  