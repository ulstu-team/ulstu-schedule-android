package ulstu.schedule.api;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

class JsonDownloadTask extends AsyncTask<String, Void, String> {

    private String mUrl;
    private OnDownloadedListener mListener;

    public interface OnDownloadedListener {
        void onDownloaded(String response);
    }

    public JsonDownloadTask(String url, OnDownloadedListener listener) {
        mUrl = url;
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        return  getContent(mUrl);
    }

    @Override
    protected void onPostExecute(String response) {
        mListener.onDownloaded(response);
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
