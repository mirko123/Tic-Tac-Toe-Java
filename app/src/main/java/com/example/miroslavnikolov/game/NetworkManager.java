package com.example.miroslavnikolov.game;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by miroslavnikolov on 4.06.16.
 */
public class NetworkManager {
    private static NetworkManager instance;
    public HashMap<String, PlayerInNetwork> playersInNetwork;
    private String name;
    private String ip;


    private NetworkManager(String name, String ip) {
        playersInNetwork = new HashMap<String, PlayerInNetwork>();
        this.name = name;
        this.ip = ip;
    }

    public static void Init(String name, String ip)
    {
        if(instance == null)
            instance = new NetworkManager(name, ip);
    }

    public static NetworkManager getInstance() { return instance; }



    private static void sender() throws Exception {
//        URL url = new URL("192.168.0.101:1337");
//        System.out.println(url.getDefaultPort());
//        System.out.println(url.getPort());
////        URL url2 = new URL();
////        URL url3 = new URL()
//        Map<String,Object> params = new LinkedHashMap<>();
//        params.put("name", "Freddie the Fish");
//        params.put("email", "fishie@seamail.example.com");
//        params.put("reply_to_thread", 10394);
//        params.put("message", "Shark attacks in Botany Bay have gotten out of control. We need more defensive dolphins to protect the schools here, but Mayor Porpoise is too busy stuffing his snout with lobsters. He's so shellfish.");
//
//        StringBuilder postData = new StringBuilder();
//        for (Map.Entry<String,Object> param : params.entrySet()) {
//            if (postData.length() != 0) postData.append('&');
//            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//            postData.append('=');
//            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//        }
//        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
//
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//        conn.setDoOutput(true);
//        conn.getOutputStream().write(postDataBytes);
//
//        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//
//        for (int c; (c = in.read()) >= 0;)
//            System.out.print((char)c);

        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("88.87.1.226", 1337);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
    }

    public String performPostCall(String requestURL) {
        return performPostCall(requestURL, null);
    }


    public void askForPlayers()
    {
//        HashMap<String, String> params = new HashMap<>();
//        String response = performPostCall("1337");


        JSONObject obj = null;
        try {
            obj = new JSONObject(performPostCall("1337"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONArray arr = null;
        try {
            arr = obj.getJSONArray("players");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < arr.length(); i++)
        {
            try {
                String name = arr.getJSONObject(i).getString("name");
                String ip = arr.getJSONObject(i).getString("ip");
                String port = arr.getJSONObject(i).getString("port");
                PlayerInNetwork pl = new PlayerInNetwork(name, ip, port);

                if(!name.equals(this.name)) playersInNetwork.put(name, pl);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (String  str: playersInNetwork.keySet()) {
            PlayerInNetwork player = playersInNetwork.get(str);
            System.out.println(str + ": " + player.name + " " + player.IP + " " + player.port);
        }

    }

    public GamePlay.Position askForPosition()
    {
        GamePlay.Position position = new GamePlay.Position(0, 0);

//        HashMap<>
        return position;
    }
    public String performPostCall(String port, HashMap<String, String> postDataParams) {

        String requestURL = "http://" + ip + ":" + port + "/";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if(postDataParams != null)
                for (String item : postDataParams.keySet()) {
                    conn.setRequestProperty(item, postDataParams.get(item));
            }


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

//            System.out.println(conn.get);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(response);
        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private class PlayerInNetwork
    {
        public String port;
        public String IP;
        public String name;

        public PlayerInNetwork(String name, String IP, String port)
        {
            this.name = name;
            this.IP = IP;
            this.port = port;
        }
    }
}
