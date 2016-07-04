package com.example.miroslavnikolov.game;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private List<NetworkManager2.PlayerInNetwork> data = new ArrayList<>();
//    private NetworkManager networkManager = NetworkManager.getInstance();
    private NetworkManager2 networkManager = NetworkManager2.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("gain create:   1");
        super.onCreate(savedInstanceState);
        System.out.println("gain create:   2");
        setContentView(R.layout.activity_list);
        System.out.println("gain create:   3");

//        networkManager = NetworkManager.getInstance();
        networkManager.setContext(this);


        refreshListWithPlayers();
        System.out.println("gain create:   4");


        Button button = (Button) this.findViewById(R.id.refresh_button);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("gain create:   4.1");
                refreshListWithPlayers();
                System.out.println("gain create:   4.2");
            }
        });

        System.out.println("gain create:   5");

//        Thread thread = new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    //Your code goes here
//                    net.Listen();
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();


        networkManager.runListener();
        System.out.println("gain create:   6");

    }

    public void refreshListWithPlayers()
    {
        data = networkManager.askForPlayers();

        ListView lv = (ListView) findViewById(R.id.listview);

        if (lv != null) {
            lv.setAdapter(new MyListAdaper(this, R.layout.list_item, data));
        }
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }

    private void generateListContent() {
        for(int i = 0; i < 15; i++) {
//            data.add("This is row number " + i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyListAdaper extends ArrayAdapter<NetworkManager2.PlayerInNetwork> {
        private int layout;
        private List<NetworkManager2.PlayerInNetwork> mObjects;
        private MyListAdaper(Context context, int resource, List<NetworkManager2.PlayerInNetwork> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
//                convertView.setBackgroundColor(Color.RED);
                ViewHolder viewHolder = new ViewHolder();
//                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            final NetworkManager2.PlayerInNetwork player = getItem(position);
            if(player.isInGame)
            {
                convertView.setBackgroundColor(Color.RED);
                mainViewholder.button.setEnabled(false);
            }



            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    networkManager.askPlayerForGame(player.IP + player.port);

                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
//                    networkManager.alertForGame("ddz", getContext());

                }
            });
            String titleText = "Player " + player.name + " with IP: " + player.IP;
            mainViewholder.title.setText(titleText);

            return convertView;
        }
    }
    public class ViewHolder {

//        ImageView thumbnail;
        TextView title;
        Button button;
    }

    public static void alertForGame(final String name, final Context context)
    {

        final NetworkManager2 net = NetworkManager2.getInstance();

        ((ListActivity)context).runOnUiThread(new Runnable()
        {
            public void run()
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Question");
                builder.setMessage("Want to play with " + name + "?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        net.answerForAsk("YES");
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing

                        net.answerForAsk("NO");
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });


//        Looper.loop();
    }
//    private void alert()
//    {
//        System.out.println(getBase);
//    }
}
