package se.aljood.aljoodsql.SQL.Cart;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.Fragment;
        import android.content.Context;
        import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.ImageView;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
        import se.aljood.aljoodsql.R;
import se.aljood.aljoodsql.SQL.Main_Menu_Lang.MainActivity;
        import se.aljood.aljoodsql.SQL.Module.SharedPrefManager;

        import static se.aljood.aljoodsql.SQL.Module.SharedPrefManager.DATA_INSERT_URL;
        import static se.aljood.aljoodsql.SQL.Module.SharedPrefManager.DATA_ITEMS_URL;

public class Make_order_Fragment extends Fragment {
    Activity activity;
    RecyclerView rv_order_items;
    final ArrayList<Item_Class> items = new ArrayList<>();
    ArrayList<Item_Class>items_o=new ArrayList<>();
    MyAdapter_Create_Order adapter;
    Button btn_save_order;
    CheckBox chk_show_prices;
    EditText txt_search;
    boolean sh_prices;
    ArrayList<Item_Class> cart_items=new ArrayList<>();
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_make_order, container, false);
        rv_order_items = rootView.findViewById(R.id.rv_fragment_cart);
        btn_save_order=rootView.findViewById(R.id.btn_show_order);
        chk_show_prices=rootView.findViewById(R.id.ch_show_prices);
        txt_search=rootView.findViewById(R.id.txt_search_item);

        ((MainActivity) getActivity())
                .setActionBarTitle("Make order for :"+ getTag().toString() );

        initialize_Items();
        txt_search.addTextChangedListener(new TextWatcher() {
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
        btn_save_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart_items.size()==0){
                    final AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(activity);
                    }
                    builder.setTitle("Error")
                            .setMessage("Du kan inte spara order tomt!!!.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                                        Intent i=new Intent(CreateCusActivity.this,LoginActivity.class);
//                                                        startActivity(i);
                                }

                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }else {
                Save_Order(cart_items);
                }
                Toast.makeText(activity, SharedPrefManager.getInstance(activity).get_Cus_ID(), Toast.LENGTH_SHORT).show();
//                kund_order_Fragment nextFrag= new kund_order_Fragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.content, nextFrag, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        chk_show_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    for (int x = rv_order_items.getChildCount(), i = 0; i < x; ++i) {
                        RecyclerView.ViewHolder holder = rv_order_items.getChildViewHolder(rv_order_items.getChildAt(i));
                        holder.itemView.findViewById(R.id.lbl_pur_plus_price).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.lbl_pur_price).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.lbl_sale_price).setVisibility(View.VISIBLE);
                    }
                }
                else{
                    for (int x = rv_order_items.getChildCount(), i = 0; i < x; ++i) {
                        RecyclerView.ViewHolder holder = rv_order_items.getChildViewHolder(rv_order_items.getChildAt(i));
                        holder.itemView.findViewById(R.id.lbl_pur_plus_price).setVisibility(View.GONE);
                        holder.itemView.findViewById(R.id.lbl_pur_price).setVisibility(View.GONE);
                        holder.itemView.findViewById(R.id.lbl_sale_price).setVisibility(View.GONE);
                    }
                }
            }
        });
        return rootView;
    }

    private void Save_Order(ArrayList<Item_Class>  item_class) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        SharedPrefManager.getInstance(activity).set_ord_date(currentDateandTime);
        SharedPrefManager.getInstance(activity).set_ord_sended(false);
        SharedPrefManager.getInstance(activity).set_ord_packad(false);
        SharedPrefManager.getInstance(activity).set_ord_shipped(false);
        SharedPrefManager.getInstance(activity).set_cancelled(false);
        SharedPrefManager.getInstance(activity).set_ord_total("0");

        for(int i = 0; i < item_class.size(); i++){

        AndroidNetworking.get(DATA_INSERT_URL)
                .addQueryParameter("action", "add_order")
                .addQueryParameter("ord_cus_id",SharedPrefManager.getInstance(activity).get_Cus_ID())
                .addQueryParameter("ord_cus_name",SharedPrefManager.getInstance(activity).get_Cus_Name() )
                .addQueryParameter("ord_date",currentDateandTime)
                .addQueryParameter("ord_sended", "0")
                .addQueryParameter("ord_packad",  "0")
                .addQueryParameter("ord_shipped",  "0")
                .addQueryParameter("ord_cancelled", "0")
                .addQueryParameter("ord_total",  "0")
                .addQueryParameter("user_created",SharedPrefManager.getInstance(activity).get_user_Name())
                .addQueryParameter("item_id",String.valueOf(item_class.get(i).getItem_id()))
                .addQueryParameter("item_name_ar",item_class.get(i).getItem_name_ar())
                .addQueryParameter("item_weight",item_class.get(i).getItem_weight())
                .addQueryParameter("def_item_unit", String.valueOf(item_class.get(i).getDef_item_unit()))
                .addQueryParameter("unit1",item_class.get(i).getUnit1())
                .addQueryParameter("unit2",item_class.get(i).getUnit2())
                .addQueryParameter("unit3",item_class.get(i).getUnit3())
                .addQueryParameter("item_moms", String.valueOf(item_class.get(i).getItem_moms()))
                .addQueryParameter("item_p_sale_price", String.valueOf(item_class.get(i).isItem_type_price()))
                .addQueryParameter("item_sale_price", String.valueOf(item_class.get(i).getItem_sale_price()))
                .addQueryParameter("item_img",item_class.get(i).getItem_img())

                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        if (response !=null){
//                            try{
//
//
//                                // Show Response From Server
//                                String responseString = response.get(0).toString();
//                                if( responseString.equals("Unsuccess")){
//                                    final AlertDialog.Builder builder;
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                        builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
//                                    } else {
//                                        builder = new AlertDialog.Builder(activity);
//                                    }
//                                    builder.setTitle("Errorr")
//                                            .setMessage("Please Call Eyad.")
//                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
////                                                        Intent i=new Intent(CreateCusActivity.this,LoginActivity.class);
////                                                        startActivity(i);
//                                                }
//
//                                            })
//                                            .setIcon(android.R.drawable.ic_dialog_info)
//                                            .show();
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                Toast.makeText(activity,"Goods Response But Java Can`t Parse Json It Received: "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(activity,"Unsuccessful : Error Is :" + anError.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
        }
    }

    private void initialize_Items() {
        rv_order_items.setHasFixedSize(true);
        rv_order_items.setLayoutManager(new LinearLayoutManager(getActivity()));
//        String cus_id = String.valueOf(SharedPrefManager.getInstance(getActivity()).get_user_ID());
        Retrieve_Items(rv_order_items,sh_prices);
    }

    private void Retrieve_Items(final RecyclerView rv_customers, final boolean show_prices) {
        items.clear();
        items_o.clear();

            AndroidNetworking.get(DATA_INSERT_URL)
                    .addQueryParameter("action", "select_items")
                    .setPriority(Priority.HIGH)

                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response.length() > 0) {
                                JSONObject jo;
                                Item_Class s;

                                try {
                                    if (response.length() > 0) {
                                        for (int i = 0; i < response.length(); i++) {
                                            jo = response.getJSONObject(i);
                                            int item_id = jo.getInt("item_id");
                                            String item_name_ar = jo.getString("item_name_ar");
                                            String item_weight = jo.getString("item_weight");
                                            int def_item_unit = jo.getInt("def_item_unit");
                                            String item_unit1 = jo.getString("unit1");
                                            String item_unit2= jo.getString("unit2");
                                            String item_unit3 = jo.getString("unit3");
                                            Double item_pur_price_plus = jo.getDouble("item_pur_price_plus");
                                            Double item_pur_price= Double.valueOf(jo.getString("item_pur_price"));
                                            Double item_sale_price= Double.valueOf(jo.getString("item_sale_price"));
                                            String item_img=jo.getString("item_img");
                                            Double item_moms=jo.getDouble("item_moms");
                                            s = new Item_Class();
                                            s.setItem_id(item_id);
                                            s.setItem_name_ar(item_name_ar);
                                            s.setItem_weight(item_weight);
                                            s.setItem_pur_price_plus(item_pur_price_plus);
                                            s.setItem_pur_price(item_pur_price);
                                            s.setItem_sale_price(item_sale_price);
                                            s.setDef_item_unit(def_item_unit);
                                            s.setUnit1(item_unit1);
                                            s.setUnit2(item_unit2);
                                            s.setUnit3(item_unit3);
                                            s.setItem_img(item_img);
                                            s.setItem_moms(item_moms);
                                            items.add(s);
                                            items_o.add(s);
                                        }
                                        adapter=  new MyAdapter_Create_Order(activity,items,items_o,show_prices);

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

    public class MyAdapter_Create_Order extends RecyclerView.Adapter<MyViewHolder_Create_Order>implements Filterable {
        private Context c;
        private ArrayList<Item_Class> items;
        private ArrayList<Item_Class> items_o;
        ArrayList<Item_Class> itemsFiltered;
        boolean show_prices;
        public MyAdapter_Create_Order(Context c, ArrayList<Item_Class> items, ArrayList<Item_Class>items_o, boolean show_prices) {
            this.c = c;
            this.items = items;
            this.itemsFiltered = items;
            this.items_o=items_o;
            this.show_prices=show_prices;
        }

        @Override
        public MyViewHolder_Create_Order onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(c).inflate(R.layout.listview_item,parent,false);

            return new MyViewHolder_Create_Order(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder_Create_Order holder, final int position) {

            final Item_Class itm = items.get(position);
            holder.txt_item_name.setText(itm.getItem_name_ar());
            holder.imageView.setImageUrl(DATA_ITEMS_URL + itm.getItem_img());
            holder.txt_item_weight.setText(itm.getItem_weight());
            switch (itm.getDef_item_unit()){
                case 1:
                    holder.rb_st.setChecked(true);
                    break;
                case 2:
                    holder.rb_lada.setChecked(true);
                    break;
                default:
                    holder.rb_box.setChecked(true);
                    break;
            }
            if (itm.getUnit1().isEmpty()){
                holder.rb_st.setText("st");
            }else {
                holder.rb_st.setText(itm.getUnit1());
            }
            if (itm.getUnit2().isEmpty()){
                holder.rb_lada.setVisibility(View.GONE);
            }else {
                holder.rb_lada.setVisibility(View.VISIBLE);
                holder.rb_lada.setText(itm.getUnit2());
            }
            if (itm.getUnit3().isEmpty()){
                holder.rb_box.setVisibility(View.GONE);
            }else {
                holder.rb_box.setVisibility(View.VISIBLE);
                holder.rb_box.setText(itm.getUnit3());
            }


            if (itm.getItem_qty()== 0){
                holder.sp.setSelection(0);
                holder.img_fav.setImageResource(R.drawable.ic_fav_des);
            }else {
                holder.sp.setSelection(itm.getItem_qty());
                holder.img_fav.setImageResource(R.drawable.ic_fav_sel);

            }

            holder.sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapterView.getSelectedItem().toString()=="0") {
                        holder.img_fav.setImageResource(R.drawable.ic_fav_des);
                        itm.setItem_qty(0);
                        for (Item_Class ss:cart_items){
                            if (ss.equals(itm)){
                                cart_items.remove(itm);
                                break;
                            }
                        }
                    }else {
                        holder.img_fav.setImageResource(R.drawable.ic_fav_sel);
                        itm.setItem_qty(Integer.valueOf(adapterView.getSelectedItem().toString()));
                        boolean flags=false;
                        for (Item_Class ss:cart_items){
                            if (ss.equals(itm)){
//                                cart_items.remove(ss);
                                flags=true;
                                break;

                            }
                        }
                        if (!flags){
                            cart_items.add(itm);
                        }
                              //  Toast.makeText(c,"added item:"+ item.getItem_id() +" //"+item.getItem_name_ar()+" //"+item.getItem_qty() , Toast.LENGTH_SHORT).show();


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if (show_prices){
                holder.txt_pur_plus.setVisibility(View.VISIBLE);
                holder.txt_pur.setVisibility(View.VISIBLE);
                holder.txt_sale.setVisibility(View.VISIBLE);
            }else {
                holder.txt_pur_plus.setVisibility(View.GONE);
                holder.txt_pur.setVisibility(View.GONE);
                holder.txt_sale.setVisibility(View.GONE);
            }
            holder.txt_pur_plus.setText("Pur Plus: "+itm.getItem_pur_price_plus());
            holder.txt_pur.setText("//Pur : "+itm.getItem_pur_price());
            holder.txt_sale.setText("//Sale: "+itm.getItem_sale_price());
            holder.rb_st.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itm.setDef_item_unit(1);
                }
            });
            holder.rb_lada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itm.setDef_item_unit(2);
                }
            });
            holder.rb_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itm.setDef_item_unit(3);
                }
            });

            holder.rb_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.price_type_moms=false;
                    itm.setItem_type_price(holder.price_type_moms);
                }
            });

            holder.rb_net.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.price_type_moms=true;
                    itm.setItem_type_price(holder.price_type_moms);
                }
            });
            holder.txt_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itm.setItem_type_price(holder.price_type_moms);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    itm.setItem_price(Double.valueOf(editable.toString()));
                }

            });

        }

        @Override
        public int getItemCount() {return items.size();}

        @Override
        public Filter getFilter() {return exampleFilter;}
        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                items = new ArrayList<>(items_o);
                List<Item_Class> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(items_o);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Item_Class item : items) {
                        if (item.getItem_name_ar().toLowerCase().contains(filterPattern) || item.getItem_weight().toLowerCase().contains(filterPattern)) {
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

    public static class MyViewHolder_Create_Order extends RecyclerView.ViewHolder {
        ANImageView imageView;
        ImageView img_fav;
        TextView txt_item_name, txt_item_weight,txt_pur_plus,txt_pur,txt_sale;
        RadioGroup rb_grp,rb_grp1;
        RadioButton rb_box,rb_lada,rb_st,rb_plus,rb_net;
        EditText txt_price;
        Spinner sp;
        int tagItem;
        CardView cardView;
        boolean price_type_moms;

        public MyViewHolder_Create_Order(final View view) {
            super(view);
            tagItem = 0;
            imageView = view.findViewById(R.id.img_item);
            txt_item_name=view.findViewById(R.id.lbl_item_name);
            txt_item_weight=view.findViewById(R.id.lbl_item_weight);
            rb_box=view.findViewById(R.id.rb_box);
            rb_lada=view.findViewById(R.id.rb_lada);
            rb_st=view.findViewById(R.id.rb_st);
            sp = view.findViewById(R.id.spnr_qty_normal_cart_item);
            imageView.setDefaultImageResId(R.mipmap.loading);
            rb_grp=view.findViewById(R.id.rb_grp);
            cardView=view.findViewById(R.id.cardView_items);
            String[] ii =new String[201];
            for(int i = 0; i < 201; i++){
                ii[i]=String.valueOf(i);
            }
            ArrayAdapter<String> sp_adapter = new ArrayAdapter<String >(view.getContext(),android.R.layout.simple_list_item_1, ii);
            sp.setAdapter(sp_adapter);
            img_fav=view.findViewById(R.id.img_fav);
            txt_pur_plus=view.findViewById(R.id.lbl_pur_plus_price);
            txt_pur=view.findViewById(R.id.lbl_pur_price);
            txt_sale=view.findViewById(R.id.lbl_sale_price);

            rb_grp1=view.findViewById(R.id.rb_grp1);
            rb_plus=view.findViewById(R.id.rb_plus);
            rb_net=view.findViewById(R.id.rb_net);
            txt_price=view.findViewById(R.id.txt_item_price);

        }

    }

}