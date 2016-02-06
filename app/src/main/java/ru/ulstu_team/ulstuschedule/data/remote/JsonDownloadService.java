package ru.ulstu_team.ulstuschedule.data.remote;

import android.app.IntentService;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public  class JsonDownloadService extends IntentService {

    public JsonDownloadService() {
        super("JsonDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        String response = getContent(url);

        Intent broadcastIntent = new Intent(intent.getStringExtra("broadcast_action"));
        broadcastIntent.putExtra("json", response);
        sendBroadcast(broadcastIntent);
    }

    private String getContent(String path) {
        BufferedReader reader = null;
        String result = "";

        try {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Accept-Encoding", "gzip");
            c.setReadTimeout(3000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(c.getInputStream())));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            result = buf.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }
}
