package dnomyar.combo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import dnomyar.combo.models.Config;

/**
 * Created by Raymond on 2015-01-12.
 */
public class JsonHelper {

    public static void setConfig(Activity activity, Config c) {
        JSONObject jo = new JSONObject();
        try {
            JSONObject infoJo = new JSONObject();
            infoJo.put("score", c.getInfo().getScore());
            infoJo.put("level", c.getInfo().getLevel());
            infoJo.put("max_combo", c.getInfo().getMaxCombo());
            jo.put("info", infoJo);
            FileOutputStream fos = activity.openFileOutput(Constants.CONFIG_FILE_PATH, Context.MODE_PRIVATE);
            fos.write(jo.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("JsonHelper", e.getMessage());
        }
    }

    public static Config getConfig(Activity activity) {
        String path = Constants.CONFIG_FILE_PATH;
        String json = JsonHelper.readFileAsString(activity, path);
        JSONObject jo = null;

        try {
            jo = new JSONObject(json);
            JSONObject infoJo = (JSONObject) jo.get("info");

            Config c = new Config();
            Config.Info info = c.new Info();
            info.setScore(Integer.parseInt(infoJo.get("score").toString()));
            info.setLevel(Integer.parseInt(infoJo.get("level").toString()));
            info.setMaxCombo(Integer.parseInt(infoJo.get("max_combo").toString()));

            c.setInfo(info);
            return c;
        } catch (Exception e) {
            Log.e("JsonHelper", e.getMessage());
            return null;
        }
    }

    private static String readFileAsString(Activity activity, String path) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        String line;
        try {
            FileInputStream in = activity.openFileInput(path);
            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
            return sb.toString();
        } catch (FileNotFoundException fe) {
            Config c = new Config();
            Config.Info info = c.new Info();
            c.setInfo(info);
            JsonHelper.setConfig(activity, c);
            try { br.close(); } catch (Exception e) {};
            return c.toJsonString();
        } catch (IOException e) {
            Log.e("JsonHelper", e.getMessage());
            return "";
        }
    }
}
