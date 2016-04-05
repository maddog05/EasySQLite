package com.maddog05.demoeasysqlite;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
        getPlayers(recyclerPlayers);
    }

    public void insertPlayer(final View v)
    {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_insert_player);
        final EditText txtName = (EditText) dialog.findViewById(R.id.txtInsertPlayerName);
        final EditText txtTeam = (EditText) dialog.findViewById(R.id.txtInsertPlayerTeam);
        final EditText txtValue = (EditText) dialog.findViewById(R.id.txtInsertPlayerValue);
        Button btnInsert = (Button) dialog.findViewById(R.id.btnInserPlayer);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pName = txtName.getText().toString();
                String pTeam = txtTeam.getText().toString();
                String pValue = txtValue.getText().toString();
                if (pName.isEmpty() || pName.length() == 0 &&
                        pTeam.isEmpty() || pTeam.length() == 0 &&
                        pValue.isEmpty() || pValue.length() == 0) {
                    Toast.makeText(MainActivity.this, "Need data to save", Toast.LENGTH_SHORT).show();
                } else {
                    Player player = new Player();
                    player.setName(pName);
                    player.setTeam(pTeam);
                    player.setMoneyValue(Double.parseDouble(pValue));
                    playerManager.insertPlayer(player);
                    Snackbar.make(v, "Player inserted", Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();

                    getPlayers(view);
                }
            }
        });
        Button btnDismiss = (Button) dialog.findViewById(R.id.btnDismissDialog);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MainActivity.this, R.style.AlertTheme);
        dialogDelete.setTitle(R.string.app_name);
        dialogDelete.setMessage("Do you want to delete all players?");
        dialogDelete.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playerManager.deletePlayers();
                recyclerPlayers.setAdapter(new PlayerAdapter(MainActivity.this, null));
                Snackbar.make(recyclerPlayers, "Players deleted", Snackbar.LENGTH_SHORT).show();
            }
        });
        dialogDelete.show();
    }

    public void developerInfo(View v)
    {
        AlertDialog.Builder dialogInfo = new AlertDialog.Builder(MainActivity.this, R.style.AlertTheme);
        dialogInfo.setTitle(R.string.app_name);
        dialogInfo.setMessage(R.string.info_message);
        dialogInfo.setPositiveButton("MY GITHUB", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openGitHub();
            }
        });
        dialogInfo.setNeutralButton("Project", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openProject();
            }
        });
        dialogInfo.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogInfo.show();
    }

    private void openGitHub()
    {
        String url = "https://github.com/maddog05";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void openProject()
    {
        String url = "https://github.com/maddog05/EasySQLite";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
