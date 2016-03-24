package com.maddog05.demoeasysqlite.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maddog05.demoeasysqlite.R;
import com.maddog05.demoeasysqlite.entities.Player;

import java.util.List;

/*
 * Created by maddog05 on 17/03/16.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerVH> {

    private Context ctx;
    private List<Player> players;

    public PlayerAdapter(Context ctx, List<Player> players)
    {
        this.ctx = ctx;
        this.players = players;
    }

    @Override
    public PlayerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayerVH(View.inflate(ctx, R.layout.item_player, null));
    }

    @Override
    public void onBindViewHolder(PlayerVH holder, int position) {
        Player player = players.get(position);
        holder.name.setText(player.getName());
        holder.team.setText("Play in " + player.getTeam());
        holder.value.setText("Value: " + String.valueOf(player.getMoneyValue()));
    }

    @Override
    public int getItemCount() {
        return players != null ? players.size() : 0;
    }

    public class PlayerVH extends RecyclerView.ViewHolder {
        protected TextView name, team, value;
        public PlayerVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.lblItemPlayerName);
            team = (TextView) itemView.findViewById(R.id.lblItemPlayerTeam);
            value = (TextView) itemView.findViewById(R.id.lblItemPlayerValue);
        }
    }
}
