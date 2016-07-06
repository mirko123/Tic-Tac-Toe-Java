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
    public PlayerInNetwork withInGame;

    public String getName() {
        return name;
    }



    private NetworkManager2(String name, Context context) {
        this.playersInNetwork = new HashMap<String, PlayerInNetwork>(); // KEY is IP + port
        this.context = context;
        this.name = name;
        host = "88.87.1.226";
        portNumber = 1336;




        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            socket = new Socket(host, portNumber);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(this.name);


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
//        System.out.println("______0");
//
//        Thread th = Thread.currentThread();
//        System.out.println("1_ There name: " + th.getName());
//
//        try {
//
//            System.out.println("2_ There name: " + th.getName());
//
//            if(thread.isAlive())
//            {
//                System.out.println("3_ There name: " + th.getName());
//                synchronized (thread)
//                {
//                    System.out.println("4_ There name: " + th.getName());
//                    System.out.println("Thread name: " + thread.getName());
//                    this.thread.wait();
//                    System.out.println("5_ There name: " + th.getName());
//                    this.thread.notify();
//                    System.out.println("5.1_ There name: " + th.getName());
//                }
//                System.out.println("6_ There name: " + th.getName());
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("7_ There name: " + th.getName());
//
//


        System.out.println("NAME:::: " + this.name );
        System.out.println("______0.1");
        this.out.println("return");
        System.out.println("______1");


        System.out.println("There state " + thread.getState());
        System.out.println("There isAlive:  " + thread.isAlive());

        String json;

        try {
//            if( !thread.isAlive() && (json = br.readLine()) != null)
            if(!thread.isAlive())
            {
                json = br.readLine();
                updatePlayersFromJSON(json);
            }
            else
            {

                synchronized (thread)
                {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
//            else return;
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
            System.out.println("current thread: " + Thread.currentThread().getName());
            System.out.println("thread name: " + thread.getName());
            nextLine = br.readLine();
            System.out.println("nextline: " + nextLine);

            if(nextLine.equals("askForGame"))
            {
                nextLine = br.readLine();
                System.out.println(nextLine);
                PlayerInNetwork player = playersInNetwork.get(nextLine);

                for (String  key : playersInNetwork.keySet()) {

                    PlayerInNetwork pl = playersInNetwork.get(key);

//                    System.out.println("pl key: " + key);
//                    System.out.println("pl name: " + pl.name);
//                    System.out.println("pl ip: " + pl.IP);
//                    System.out.println("pl port: " + pl.port);
//                    System.out.println(context);

                }

//                System.out.println("server askForGame 3");
//                System.out.println("name: " + player.name);
//                System.out.println("ip: " + player.IP);
//                System.out.println("port: " + player.port);
//                System.out.println(context);
                withInGame = player;
                ((ListActivity)context).alertForGame(player.name, context);

//                System.out.println("server askForGame finish");
            }
            if(nextLine.contains("players"))
            {
                updatePlayersFromJSON(nextLine);
                synchronized (thread)
                {
                    thread.notify();
                }
            }
            if(nextLine.equals("answer"))
            {
                synchronized (thread)
                {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String answer = br.readLine();
            }
            if(nextLine.equals("sendPosition"))
            {
                synchronized (thread)
                {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("here ____6.1");
        }
    }

    public void alertForGame(String name, Context con)
    {
        ((ListActivity)con).alertForGame(name, con);
    }

    public void sendClickPosition(int row, int col)
    {
        String pointInJson = "{ \"point\": { \"row\": \"" + row + "\", \"col\": \"" + col + "\" }}";
        this.out.println("sendPosition");
        this.out.println(pointInJson);
    }

    public boolean askPlayerForGame(String ipAndPort)
    {

        boolean accept = false;

        this.out.println("askPlayerForGame:");
        System.out.println("ask player 0: ");
        this.out.println(ipAndPort);
        System.out.println("ask player 1: ");

        try {
            System.out.println("ask player 2: ");
            String answer = br.readLine();
            synchronized (thread)
            {
                thread.notify();
            }


            System.out.println("ask player 3: ");
            System.out.println("ask player 4: " + answer);
            if(answer.equals("true"))
            {
                accept = true;
                withInGame = playersInNetwork.get(ipAndPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return accept;
    }

    public void answerForAsk(String answer)
    {
        out.println("answer");
        if(answer.equals("NO")) withInGame = null;
        out.println(answer);
    }

    public GamePlay.Position askForPosition()
    {
        GamePlay.Position position = new GamePlay.Position(0, 0);
        System.out.println("____ 0");

        this.out.println("askForPosition");
        System.out.println("____ 1");
        GamePlay gamePlay = GamePlay.getInstance();
        System.out.println("____ 2");


        synchronized (gamePlay) {
            gamePlay.notify();
        }
        String json = null;
        try {
            json = this.br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized (thread)
        {
            thread.notify();
        }

        System.out.println("____ 3");
        position = Parser.fromJsonToPosition(json);
        System.out.println("row: " + position.getRow());
        System.out.println("col: " + position.getCol());
        System.out.println("____ 4");

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

    private static class Parser {
        public static GamePlay.Position fromJsonToPosition(String json) {

            JSONObject obj = null;
            try {
                obj = new JSONObject(json);
                obj = obj.getJSONObject("point");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int row = 0;
            int col = 0;

            try {
                row = Integer.parseInt(obj.getString("row"));
                col = Integer.parseInt(obj.getString("col"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            GamePlay.Position position = new GamePlay.Position(row, col);

            return position;

        }
    }

    public static class PlayerInNetwork // extends OtherPlayer
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
