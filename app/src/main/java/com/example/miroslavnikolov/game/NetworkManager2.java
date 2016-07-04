package com.example.miroslavnikolov.game;

import android.content.Context;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by miroslavnikolov on 4.06.16.
 */
public class NetworkManager2 { //extends Thread {


    private static NetworkManager2 instance;
    public HashMap<String, PlayerInNetwork> playersInNetwork;
    private String name;
    private Context context;


    private final String host;
    private final int portNumber;
    private Socket socket;

    public BufferedReader br;
    public PrintWriter out;
    public Thread thread;



    private NetworkManager2(String name, Context context) {
        this.playersInNetwork = new HashMap<String, PlayerInNetwork>(); // KEY is IP + port
        this.context = context;
        this.name = name;
        host = "88.87.1.226";
        portNumber = 1337;




        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            socket = new Socket(host, portNumber);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        //Your code goes here
                        instance.Listen();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

//            thread.start();

//            this.execute("ddz");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Init(String name, Context context)
    {
        if(instance == null) {
            instance = new NetworkManager2(name, context);
        }
    }

    public static NetworkManager2 getInstance() { return instance; }




    public List<PlayerInNetwork> askForPlayers()
    {
        System.out.println("______0");
//        try {
//
//
//            if(thread.isAlive())
//                synchronized (thread)
//                {
//                    this.thread.wait();
//                }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("______0.1");
        this.out.println("return");
        System.out.println("______1");


        try {
            if(!thread.isAlive())
            {
                String json = br.readLine();
                updatePlayersFromJSON(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if(!thread.isAlive())
//            synchronized (thread)
//            {
//                this.thread.notify();®
//            }

        List<PlayerInNetwork> players = new ArrayList<>(playersInNetwork.values());

//        for (String  str: playersInNetwork.keySet()) {
//            PlayerInNetwork player = playersInNetwork.get(str);
//            System.out.println(str + ": " + player.name + " " + player.IP + " " + player.port);
//
//            players.add("Player " + player.name + " with IP: " + player.IP);
//        }

        return players;
    }


    private void updatePlayersFromJSON(String json)
    {

        JSONObject obj = null;
        try {;
            obj = new JSONObject(json);
            System.out.println("______4");
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
                boolean isInGame = arr.getJSONObject(i).getString("isInGame").equals("true");
                PlayerInNetwork pl = new PlayerInNetwork(name, ip, port, isInGame);

//                if(!name.equals(this.name)) playersInNetwork.put(ip + port, pl);
                playersInNetwork.put(ip + port, pl);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void Listen() throws IOException {
        String nextLine;

        System.out.println("here ____6");
        while(true)
        {

//            MainActivity.alertForGame("adassha", context);
            System.out.println("here ____6.0");
            nextLine = br.readLine();


            if(nextLine.equals("askForGame"))
            {
                System.out.println("server askForGame");
                nextLine = br.readLine();
                System.out.printf(nextLine);
                PlayerInNetwork player = playersInNetwork.get(nextLine);

                ListActivity.alertForGame(player.name, context);

                System.out.println("server askForGame finish");
            }
            if(nextLine.contains("players"))
            {
                updatePlayersFromJSON(nextLine);
            }
            System.out.println(nextLine);

            System.out.println("here ____6.1");
        }
    }

    public void alertForGame(String name, Context con)
    {
        ListActivity.alertForGame(name, con);
    }

    public void sendClickPosition(int row, int col)
    {
        String pointInJson = "{ \"point\": { \"row\": \"" + row + "\", \"col\": \"" + col + "\" }";
    }

    public boolean askPlayerForGame(String ipAndPort)
    {
        boolean accept = false;

        this.out.println("askPlayerForGame:");
        this.out.println(ipAndPort);

        try {
            String answer = br.readLine();
            if(answer.equals("true"))
                accept = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return accept;
    }

    public void answerForAsk(String answer)
    {
        out.println("answer");
        out.println(answer);
    }

    public GamePlay.Position askForPosition()
    {
        GamePlay.Position position = new GamePlay.Position(0, 0);

//        HashMap<>
        return position;
    }

//    @Override
//    public void run() {
//
//        System.out.println("here ____2");
//        try {
//            System.out.println("here ____3");
//            Listen();
//            System.out.println("here ____4");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("here ____5");
//    }

    public void setContext(Context context) { this.context = context; }

    public void runListener()
    {



        if(!thread.isAlive()) thread.start();
    }


    public static class PlayerInNetwork
    {
        public String port;
        public String IP;
        public String name;
        public boolean isInGame;

        public PlayerInNetwork(String name, String IP, String port, boolean isInGame)
        {
            this.name = name;
            this.IP = IP;
            this.port = port;
            this.isInGame = isInGame;
        }
    }
}
