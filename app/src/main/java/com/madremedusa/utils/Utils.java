package com.madremedusa.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.madremedusa.R;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public final class Utils {

    // constructor
    private Utils(Context context) {}
 
    // Reading file paths from SDCard
    public ArrayList<String> getFilePathsCovers(boolean isCover, String magazinePath, Context context) {
        ArrayList<String> filePaths = new ArrayList<String>();
        File directory;
        if(isCover){
        	directory = new File(android.os.Environment.getExternalStorageDirectory() + File.separator + context.getString(R.string.image_directory));
        }else{
        	directory = new File(android.os.Environment.getExternalStorageDirectory() + File.separator + context.getString(R.string.image_directory) + File.separator + magazinePath);
        }
        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();
            // Check for count
            if (listFiles.length > 0) {
                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {
                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();
                    // check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                       filePaths.add(filePath);
                   }
                }
            } else {
                // image directory is empty
                Toast.makeText(context,
                        context.getString(R.string.image_directory) + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }
 
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Error!");
            alert.setMessage(context.getString(R.string.image_directory)+ " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
 
        return filePaths;
    }
 
    // Check supported file extensions
    private boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),filePath.length());
        if(AppConstant.EXT.equalsIgnoreCase(ext))
            return true;
        else
            return false;
    }
 
    /*
     * getting screen width
     */
/*    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
 
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }*/
    
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

    public static JSONObject readJsonFromUrl(String urlString) {
        JSONObject json = null;
        URL url;
        HttpURLConnection urlConnection = null;
        String line,jsonText;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                jsonText = response.toString();
                json = new JSONObject(jsonText);
            }else{
                json = new JSONObject();
            }
        } catch (MalformedURLException e) {
            json = new JSONObject();
            e.printStackTrace();
        } catch (IOException e) {
            json = new JSONObject();
            e.printStackTrace();
        } catch (JSONException e) {
            json = new JSONObject();
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return json;
    }

    public static void CopyStream(InputStream is, OutputStream os){
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static boolean checkConn(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null) {
            return false;
        }
        if (!i.isConnected()) {
            return false;
        }
        if (!i.isAvailable()) {
            return false;
        }
        return true;
    }
}
