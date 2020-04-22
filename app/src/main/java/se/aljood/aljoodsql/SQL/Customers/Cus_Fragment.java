package se.aljood.aljoodsql.SQL.Customers;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import se.aljood.aljoodsql.R;
import se.aljood.aljoodsql.SQL.Cart.Make_order_Fragment;
import se.aljood.aljoodsql.SQL.Cart.Order_Class;
import se.aljood.aljoodsql.SQL.Main_Menu_Lang.MainActivity;
import se.aljood.aljoodsql.SQL.Module.SharedPrefManager;

import static se.aljood.aljoodsql.SQL.Module.SharedPrefManager.DATA_INSERT_URL;

public class Cus_Fragment extends Fragment {
    Activity activity;
    RecyclerView rv_Customers;
    RecyclerView.LayoutManager lm_Customers;
    MyAdapter_Customers adapter;
    final ArrayList<Cus_Class> items = new ArrayList<>();
     ArrayList<Cus_Class>items_o=new ArrayList<>();
     public Order_Class Ord_class_1 = new Order_Class();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cus, container, false);
        rv_Customers = rootView.findViewById(R.id.rv_customers);
        EditText editText=(EditText)rootView.findViewById(R.id.search_text);
        initialize_Home_Items();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable);

            }
        });
        ((MainActivity) getActivity())
                .setActionBarTitle("Customers");

        return rootView;

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.kunder_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//    }

    private void initialize_Home_Items() {
        rv_Customers.setHasFixedSize(true);
        lm_Customers = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_Customers.setLayoutManager(lm_Customers);

        Retrieve_Customers_Items(rv_Customers);

    }

    private void Retrieve_Customers_Items(final RecyclerView rv_customers) {


        AndroidNetworking.get(DATA_INSERT_URL)
                .addQueryParameter("action", "select_customers")
                .setPriority(Priority.HIGH)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            JSONObject jo;
                            Cus_Class s;

                            try {
                                if (response.length() > 0) {
                                    for (int i = 0; i < response.length(); i++) {
                                        jo = response.getJSONObject(i);
                                        int cus_id = jo.getInt("cus_id");
                                        String cus_name = jo.getString("cus_name");
                                        String cus_org = jo.getString("cus_org");
                                        String cus_acc = jo.getString("cus_acc");
                                        String cus_address = jo.getString("cus_address");
                                        String cus_city = jo.getString("cus_city");

                                        s = new Cus_Class();
                                        s.setCus_id(cus_id);
                                        s.setCus_name(cus_name);
                                        s.setCus_org(cus_org);
                                        s.setCus_acc(cus_acc);
                                        s.setCus_address(cus_address);
                                        s.setCus_city(cus_city);
                                        items.add(s);
                                        items_o.add(s);
                                    }
                                    adapter=  new MyAdapter_Customers(activity,items,items_o);

                                    rv_customers.setAdapter(adapter);
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


    public class MyAdapter_Customers extends RecyclerView.Adapter<MyViewHolder_Customers>implements Filterable  {
        private Context c;
        private ArrayList<Cus_Class> items;
        private ArrayList<Cus_Class>items_o;
        ArrayList<Cus_Class> itemsFiltered;

        public MyAdapter_Customers(Context c, ArrayList<Cus_Class> items, ArrayList<Cus_Class>items_o) {
            this.c = c;
            this.items = items;
            this.itemsFiltered = items;
            this.items_o=items_o;
        }

        @Override
        public MyViewHolder_Customers onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(c).inflate(R.layout.listview_cus, parent, false);
            return new MyViewHolder_Customers(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder_Customers holder, final int position) {
            final Cus_Class itm = items.get(position);
            holder.txt_cus_name.setText(itm.getCus_name());
            holder.txt_cus_org.setText(itm.getCus_org());
            holder.txt_cus_acc.setText(itm.getCus_acc());
            holder.txt_cus_adress.setText(itm.getCus_address());
            holder.txt_cus_city.setText(itm.getCus_city());
            final PopupMenu popupMenu=new PopupMenu(c,holder.imv_menu_cus);
            final Menu menu=popupMenu.getMenu();
            popupMenu.getMenuInflater().inflate(R.menu.kunder_menu_2,menu);

            holder.imv_menu_cus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem itmm) {
                            SharedPrefManager.getInstance(v.getContext()).set_Cus_Name(itm.getCus_name());
                            SharedPrefManager.getInstance(v.getContext()).set_Cus_ID(String.valueOf(itm.getCus_id()));
                            showOptionsMenu(holder.imv_menu_cus,itm.getCus_name().toString());
                            return true;
                        }
                    });
                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent intent = new Intent (v.getContext(), Item_Card.class);
                    // intent.putExtra("ItemID",item_key);
                    // v.getContext().startActivity(intent);

                }
            });


        }
        private void save_order_sql(){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());

            SharedPrefManager.getInstance(c).set_ord_date(currentDateandTime);
            SharedPrefManager.getInstance(c).set_ord_sended(false);
            SharedPrefManager.getInstance(c).set_ord_packad(false);
            SharedPrefManager.getInstance(c).set_ord_shipped(false);
            SharedPrefManager.getInstance(c).set_cancelled(false);
            SharedPrefManager.getInstance(c).set_ord_total("0");



            AndroidNetworking.get(DATA_INSERT_URL)
                    .addQueryParameter("action", "add_order")
                    .addQueryParameter("ord_cus_id",SharedPrefManager.getInstance(c).get_Cus_ID())
                    .addQueryParameter("ord_cus_name",SharedPrefManager.getInstance(c).get_Cus_Name() )
                    .addQueryParameter("ord_date",currentDateandTime)
                    .addQueryParameter("ord_sended", "0")
                    .addQueryParameter("ord_packad",  "0")
                    .addQueryParameter("ord_shipped",  "0")
                    .addQueryParameter("ord_cancelled", "0")
                    .addQueryParameter("ord_total",  "0")
                    .addQueryParameter("user_created",SharedPrefManager.getInstance(c).get_user_Name())
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response !=null){
                                try{


                                    // Show Response From Server
                                    String responseString = response.get(0).toString();
                                    if( responseString.equals("Unsuccess")){
                                        final AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
                                        } else {
                                            builder = new AlertDialog.Builder(c);
                                        }
                                        builder.setTitle("Errorr")
                                                .setMessage("Please Call Eyad.")
                                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        Intent i=new Intent(CreateCusActivity.this,LoginActivity.class);
//                                                        startActivity(i);
                                                    }

                                                })
                                                .setIcon(android.R.drawable.ic_dialog_info)
                                                .show();

                                    }else {
                                        if (response.length() > 0) {
                                            JSONObject jo;
                                            try {
                                                if (response.length() > 0) {
                                                    for (int i = 0; i < response.length(); i++) {
                                                        jo = response.getJSONObject(i);
                                                        int ord_id=jo.getInt("ord_id");
                                                        SharedPrefManager.getInstance(c).set_ord_id(ord_id);

                                                    }
                                                }
                                            } catch (JSONException e) {

                                                e.printStackTrace();
                                                Toast.makeText(activity, "Goods Response But Java Can`t Parse Json It Received: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }

                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(c,"Goods Response But Java Can`t Parse Json It Received: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(c,"Unsuccessful : Error Is :" + anError.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        private void showOptionsMenu(final View view,String Cus_name) {
            Make_order_Fragment nextFrag= new Make_order_Fragment();
            //save_order_sql();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, nextFrag, Cus_name)
                    .addToBackStack(null)
                    .commit();

            }

        @Override
        public int getItemCount() {
            return items.size();
        }
        @Override
        public Filter getFilter() {
            return exampleFilter;
        }
        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                items = new ArrayList<>(items_o);
                List<Cus_Class> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(items_o);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Cus_Class item : items) {
                        if (item.getCus_acc().toLowerCase().contains(filterPattern) || item.getCus_city().toLowerCase().contains(filterPattern) || item.getCus_name().toLowerCase().contains(filterPattern)|| item.getCus_address().toLowerCase().contains(filterPattern)|| item.getCus_org().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items.clear();
                items.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

    }

    public static class MyViewHolder_Customers extends RecyclerView.ViewHolder {
        ImageView imageView,imv_menu_cus;
        TextView txt_cus_name, txt_cus_org, txt_cus_acc, txt_cus_adress, txt_cus_city;

        public MyViewHolder_Customers(View view) {
            super(view);
            imageView =  view.findViewById(R.id.img_cus);
            txt_cus_name = view.findViewById(R.id.lbl_cus_name);
            txt_cus_org =  view.findViewById(R.id.lbl_cus_org);
            txt_cus_acc =  view.findViewById(R.id.lbl_cus_acc);
            txt_cus_adress = view.findViewById(R.id.lbl_cus_address);
            txt_cus_city =  view.findViewById(R.id.lbl_cus_city);
            imv_menu_cus=view.findViewById(R.id.imv_menu_cus);

        }

    }


}





