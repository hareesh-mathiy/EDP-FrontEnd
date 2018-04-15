package com.edp.hareesh.smarthomenetwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Hareesh on 2018-03-27.
 */

public class ExploreActivity extends AppCompatActivity {

    private Socket mSocket;
    private TextView dayText, leaveText, returnText;
    private Button sendButton;
    public static boolean logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSocket = SocketManager.getSocket();
        mSocket.on("sentTime", onSendTime);

        dayText = (TextView) findViewById(R.id.dayText);
        leaveText = (TextView) findViewById(R.id.leaveText);
        returnText = (TextView) findViewById(R.id.returnText);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Getting Current Time", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Toast.makeText(getApplicationContext(),
                        "Sending Time.", Toast.LENGTH_SHORT).show();

                String str = dayText.getText() + "|" + leaveText.getText() + "|" +returnText.getText();
                mSocket.emit("sendTime", str);
            }
        });
    }

    private Emitter.Listener onSendTime = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Sent Time.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
