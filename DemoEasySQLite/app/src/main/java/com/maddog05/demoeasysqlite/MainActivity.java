package com.maddog05.demoeasysqlite;

import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maddog05.demoeasysqlite.adapters.PlayerAdapter;
import com.maddog05.demoeasysqlite.database.PlayerDBManager;
import com.maddog05.demoeasysqlite.entities.Player;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPlayers;
    private PlayerDBManager playerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerManager = new PlayerDBManager(MainActivity.this);
        playerManager.open();
        recyclerPlayers = (RecyclerView) findViewById(R.id.recyclerPlayers);
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerPlayers.setLayoutManager(llm);
    }

    public void insertPlayer(final View v)
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_insert_player);
        final EditText txtName = (EditText) dialog.findViewById(R.id.txtInsertPlayerName);
        final EditText txtTeam = (EditText) dialog.findViewById(R.id.txtInsertPlayerTeam);
        final EditText txtValue = (EditText) dialog.findViewById(R.id.txtInsertPlayerValue);
        Button btnInsert = (Button) dialog.findViewById(R.id.btnInserPlayer);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player player = new Player();
                player.setName(txtName.getText().toString());
                player.setTeam(txtTeam.getText().toString());
                player.setMoneyValue(Double.parseDouble(txtValue.getText().toString()));
                playerManager.insertPlayer(player);
                Snackbar.make(v,"Player inserted",Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getPlayers(View v)
    {
        List<Player> players = playerManager.getPlayers();
        if(players == null || players.size() == 0)
            Snackbar.make(v,"Players is empty",Snackbar.LENGTH_SHORT).show();
        else
            recyclerPlayers.setAdapter(new PlayerAdapter(MainActivity.this, players));
    }

    public void deletePlayers(View v)
    {
        playerManager.deletePlayers();
        recyclerPlayers.setAdapter(new PlayerAdapter(MainActivity.this, null));
        Snackbar.make(v,"Players deleted", Snackbar.LENGTH_SHORT).show();
    }
}
