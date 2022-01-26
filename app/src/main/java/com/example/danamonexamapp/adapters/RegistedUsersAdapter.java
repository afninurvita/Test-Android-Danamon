package com.example.danamonexamapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danamonexamapp.DatabaseHelper;
import com.example.danamonexamapp.R;
import com.example.danamonexamapp.activities.EditDataUsersActivity;
import com.example.danamonexamapp.activities.RegisteredUsersActivity;
import com.example.danamonexamapp.models.Users;

import java.util.List;

public class RegistedUsersAdapter extends RecyclerView.Adapter<RegistedUsersAdapter.UsersViewHolder> {
    private Context context;
    private List<Users> usersList;
    public DatabaseHelper databaseHelper;

    public RegistedUsersAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.detail_list_users,
                parent,
                false
        );
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        if (usersList.size() > 0) {
            String nama = usersList.get(position).getUsername();
            String email = usersList.get(position).getEmail();
            String role = usersList.get(position).getRole();

            Users users = new Users();
            users.setUsername(nama);
            users.setEmail(email);
            users.setRole(role);

            holder.setData(context, users, position);

            final CharSequence[] listPilihan = {
                    "Update Data",
                    "Delete Data"
            };

            final AlertDialog.Builder optionChooser = new AlertDialog.Builder(context);
            optionChooser.setTitle("Choose Action");
            optionChooser.setItems(listPilihan, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    switch (i) {
                        case 0:
                            Intent intentEdit = new Intent(context, EditDataUsersActivity.class);
                            intentEdit.putExtra("usr", users.getUsername());
                            context.startActivity(intentEdit);
                            break;
                        case 1:
                            SQLiteDatabase db = databaseHelper.getWritableDatabase();
                            db.execSQL("DELETE FROM users WHERE username = '" + users.getUsername() + "'");
                            RegisteredUsersActivity.registeredUsersActivity.refreshList();
                            break;

                    }
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionChooser.create().show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (usersList == null) {
            return 0;
        } else {
            return usersList.size();
        }
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvEmail, tvRole;
        public LinearLayout linearLayout;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvUsername);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvRole = itemView.findViewById(R.id.tvRole);
            linearLayout = itemView.findViewById(R.id.llListUsers);
        }

        public void setData(Context context, Users users, int position) {
            if (users != null) {
                String username = users.getUsername() != null ? users.getUsername() : "-";
                tvNama.setText("Username: " + username);

                String email = users.getEmail() != null ? users.getEmail() : "-";
                tvEmail.setText("Email: " + email);

                String role = users.getRole() != null ? users.getRole() : "-";
                tvRole.setText("Role: " + role);
            }
        }
    }
}
