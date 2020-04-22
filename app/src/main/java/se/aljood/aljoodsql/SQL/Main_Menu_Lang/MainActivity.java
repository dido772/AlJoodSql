package se.aljood.aljoodsql.SQL.Main_Menu_Lang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import se.aljood.aljoodsql.R;
import se.aljood.aljoodsql.SQL.Cart.Make_order_Fragment;
import se.aljood.aljoodsql.SQL.Customers.Cus_Fragment;
import se.aljood.aljoodsql.SQL.Module.SharedPrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    AlertDialog.Builder builder;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_kunder:
                    mTextMessage.setText("Kundeeer");
                    fragmentTransaction.replace(R.id.content,new Cus_Fragment()).commit();
                    return true;
               // case R.id.navigation_cart:
//                    mTextMessage.setText(R.string.title_cart);
                 //   fragmentTransaction.replace(R.id.content,new Cart_Fragment()).commit();
                   // return true;
                //case R.id.navigation_notifications:
                  //  mTextMessage.setText(R.string.title_notifications);
                    //return true;
            }
            return false;
        }
    };
    TextView nav_user_name,nav_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content,new Cus_Fragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menuNav=navigationView.getMenu();

        //if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
          //  MenuItem nav_signin = menuNav.findItem(R.id.nav_sign_in);
            //MenuItem nav_signout = menuNav.findItem(R.id.nav_signout);
            //nav_signin.setVisible(false);
            //nav_signout.setVisible(true);

     //   }else {
       //     MenuItem nav_signin = menuNav.findItem(R.id.nav_sign_in);
         //   MenuItem nav_signout = menuNav.findItem(R.id.nav_signout);
          //  nav_signin.setVisible(true);
           // nav_signout.setVisible(false);
        //}

        View hView =  navigationView.getHeaderView(0);
        nav_user_name = (TextView)hView.findViewById(R.id.nav_name);
        nav_email = (TextView) hView.findViewById(R.id.nav_email);
        ImageView imageView = (ImageView)hView.findViewById(R.id.iv_cus_icon);
        setText_Image_Cus(imageView);




    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void setText_Image_Cus(ImageView imageView) {


            nav_user_name.setText(SharedPrefManager.getInstance(getApplicationContext()).get_user_Name());
            nav_email.setText("Aljood AB");
            switch (nav_user_name.getText().toString().substring(0,1).toLowerCase()){
                case "a":
                    imageView.setImageResource(R.drawable.a);
                    return;
                case "b":
                    imageView.setImageResource(R.drawable.b);
                    return;
                case "c":
                    imageView.setImageResource(R.drawable.c);
                    return;
                case "d":
                    imageView.setImageResource(R.drawable.d);
                    return;
                case "e":
                    imageView.setImageResource(R.drawable.e);
                    return;
                case "f":
                    imageView.setImageResource(R.drawable.f);
                    return;
                case "g":
                    imageView.setImageResource(R.drawable.g);
                    return;
                case "h":
                    imageView.setImageResource(R.drawable.h);
                    return;
                case "i":
                    imageView.setImageResource(R.drawable.i);
                    return;
                case "j":
                    imageView.setImageResource(R.drawable.j);
                    return;
                case "k":
                    imageView.setImageResource(R.drawable.k);
                    return;
                case "l":
                    imageView.setImageResource(R.drawable.l);
                    return;
                case "m":
                    imageView.setImageResource(R.drawable.m);
                    return;
                case "n":
                    imageView.setImageResource(R.drawable.n);
                    return;
                case "o":
                    imageView.setImageResource(R.drawable.o);
                    return;
                case "p":
                    imageView.setImageResource(R.drawable.p);
                    return;
                case "q":
                    imageView.setImageResource(R.drawable.q);
                    return;
                case "r":
                    imageView.setImageResource(R.drawable.r);return;
                case "s":
                    imageView.setImageResource(R.drawable.s);return;
                case "t":
                    imageView.setImageResource(R.drawable.t);return;
                case "u":
                    imageView.setImageResource(R.drawable.u);return;
                case "v":
                    imageView.setImageResource(R.drawable.v);return;
                case "w":
                    imageView.setImageResource(R.drawable.w);return;
                case "x":
                    imageView.setImageResource(R.drawable.x);return;
                case "y":
                    imageView.setImageResource(R.drawable.y);return;
                default:
                        imageView.setImageResource(R.drawable.z);return;
            }
        }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.kunder_menu,menu);
//        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
//        SearchView searchView=(SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

    private void displaySelectedScreen(int id){
        Fragment fragment = null;
        switch (id) {
            //case R.id.nav_category:
                //fragment = new Categories_Fragment();
              //  break;
            //case R.id.nav_all_products:
              //  fragment = new Items_Fragment();
               // break;
            case R.id.nav_home:
                fragment = new Cus_Fragment();
                break;
            case R.id.nav_cart:
                fragment=new Make_order_Fragment();

        }

        if (fragment !=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment);
            ft.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sign_in) {
         //   Intent i = new Intent(MainActivity.this, LoginActivity.class);
          //  startActivity(i);

        } else if (id == R.id.nav_signout) {
            builder.setTitle(R.string.title_confirm_signout)
                    .setMessage(R.string.Message_confirm_signout)
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // SharedPrefManager.getInstance(getApplicationContext()).logout();
                            //Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            //startActivity(i);
                        }
                    });
            builder.show();

//        } else if (id == R.id.nav_all_products) {
//            Intent i = new Intent(MainActivity.this, Items_Fragment.class);
//            startActivity(i);


        } else if (id == R.id.nav_new_arrivals) {

        }else if (id==R.id.nav_cart) {

        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else  if (id== R.id.nav_language){
         //   Intent i = new Intent(MainActivity.this, LanguageActivity.class);
           // startActivity(i);
        }

        displaySelectedScreen(id);

        return true;
    }
}
