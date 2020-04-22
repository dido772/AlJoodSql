package se.aljood.aljoodsql.SQL.Splash_Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.widget.ANImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.aljood.aljoodsql.R;
import se.aljood.aljoodsql.SQL.Main_Menu_Lang.MainActivity;
import se.aljood.aljoodsql.SQL.Module.SharedPrefManager;
import se.aljood.aljoodsql.SQL.Module.Users_Class;

import static se.aljood.aljoodsql.SQL.Module.SharedPrefManager.DATA_INSERT_URL;

public class Users_Activity extends AppCompatActivity {

    Activity activity;
    RecyclerView rv_items_users;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        activity = this;

        rv_items_users = findViewById(R.id.rv_users);
        swiper = findViewById(R.id.Swipe_users);
        swiper.setRefreshing(true);

        initialize_users_Items();

    }

    private void initialize_users_Items() {
        rv_items_users.setHasFixedSize(true);
        rv_items_users.setLayoutManager(new LinearLayoutManager(this));
        Retrive_users_Items(rv_items_users);


    }

    public void Retrive_users_Items(final RecyclerView recyclerView) {
        final ArrayList<Users_Class> items = new ArrayList<>();

        AndroidNetworking.get(DATA_INSERT_URL)
                .addQueryParameter("action", "select_users")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            JSONObject jo;
                            Users_Class s;

                            try {
                                if (response.length() > 0) {
                                    for (int i = 0; i < response.length(); i++) {
                                        jo = response.getJSONObject(i);
                                        int product_id = jo.getInt("User_ID");
                                        String product_model = jo.getString("User_Name");
                                        s = new Users_Class();
                                        s.setUser_id(product_id);
                                        s.setUser_name(product_model);
                                        items.add(s);

                                    }
                                    recyclerView.setAdapter(new MyAdapter_users_Items(activity, items, swiper));

                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                                Toast.makeText(activity, "Goods Response But Java Can`t Parse Json It Received: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(activity, "Unsuccessful : Error Is :" + anError.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("tag", anError.getMessage());
                    }

                });

    }

    public class MyAdapter_users_Items extends RecyclerView.Adapter<MyViewHolder_users_Items> {
        private Context c;
        private ArrayList<Users_Class> items;
        private SwipeRefreshLayout swiper;

        public MyAdapter_users_Items(Context c, ArrayList<Users_Class> items, SwipeRefreshLayout swiper) {
            this.c = c;
            this.items = items;
            this.swiper = swiper;
        }

        @Override
        public MyViewHolder_users_Items onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
           v = LayoutInflater.from(c).inflate(R.layout.listview_user, parent, false);
            return new MyViewHolder_users_Items(v, viewType);

        }



        @Override
        public void onBindViewHolder(final MyViewHolder_users_Items holder, final int position) {

            final Users_Class item = items.get(position);
            holder.txt_user_name.setText(String.valueOf(item.getUser_name()));
//            holder.iv_user_icon.setImageResource(R.drawable.p1);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPrefManager.getInstance(getApplicationContext()).set_User_Name(item.getUser_name().toString());
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
            swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchTimelineAsync(0);

                }
            });
            if (getItemCount() == position + 1) {
                swiper.setRefreshing(false);
            }
        }


        @Override
        public int getItemCount() {
            return items.size();
        }

        public void clear() {
            items.clear();
            notifyDataSetChanged();
        }

        public void fetchTimelineAsync(int page) {
            clear();
            initialize_users_Items();
        }
    }

    public static class MyViewHolder_users_Items extends RecyclerView.ViewHolder {
       ImageView iv_user_icon;
        TextView txt_user_name;

        public MyViewHolder_users_Items(View view, int viewType) {
            super(view);
            iv_user_icon = view.findViewById(R.id.iv_usr_icon);
            txt_user_name = (TextView)view.findViewById(R.id.txt_usr_name);
        }

    }

}
