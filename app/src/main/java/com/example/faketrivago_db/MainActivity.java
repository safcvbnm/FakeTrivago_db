package com.example.faketrivago_db;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private MyDB db = null;
    private final static String _ID = "_id";
    private final static String HOTEL_NAME = "hotel_name";
    private final static String HOTEL_ADDRESS = "hotel_address";
    private final static String HOTEL_PHONE = "hotel_phone";
    private final static String NUM_ROOM = "num_room";
    private final static String PRICE = "price";
    private final static String RANK = "rank";
    private final static String IMAGE = "image";
    private final static String LATITUBE = "latitude";
    private final static String LONGITUBE = "longitube";
    private final static String DISCOUNT = "discount";
    private ListView listview;
    private EditText search_name,search_up_price,search_down_price;
    private Button search,search2;
    Cursor cursor1,cursor2,cursor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隱藏標籤列
        getSupportActionBar().hide();
        //隱藏狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView)findViewById(R.id.hotellist);
        search_name = (EditText)findViewById(R.id.search_name);
        search_up_price = (EditText)findViewById(R.id.upPrice);
        search_down_price = (EditText)findViewById(R.id.downPrice);
        search = (Button)findViewById(R.id.search);
        search2 = (Button)findViewById(R.id.search2);

        db = new MyDB(this);
        db.open();
        initialAdd();
        cursor1 = db.getAll();
        UpdateAdapter(cursor1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_name.getText().toString() != null){
                    cursor2 = db.get(search_name.getText().toString());
                    UpdateAdapter(cursor2);
                }
            }
        });
        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_up_price.getText().toString() != null || search_down_price.getText().toString() != null){
                    cursor3 = db.get(search_up_price.getText().toString(),search_down_price.getText().toString());
                    UpdateAdapter(cursor3);
                }
            }
        });

    }

    public void initialAdd(){
        db.append("康橋商旅覺民館","高雄市三民區覺民路291號807","073903377",25,2080,4,R.drawable.hotel01,22.640037f, 120.336411f,"7/1~8/31");
        db.append("天成文旅-繪日之丘","嘉義市東區大雅路一段888號嘉義市600","052759899",12,3699,4,R.drawable.hotel02,23.480277f,120.483279f,"7/1~8/31");
        db.append("台北商旅慶城館","台北市松山區慶城街12號庆城街12号105","0287127688",15,3588,5,R.drawable.hotel03,25.052770f,121.544991f,"10/1~12/31");
        db.append("兆品酒店嘉義MAISON DE CHINE","嘉義市文化路257號60044","052293998",35,1799,3,R.drawable.hotel04,23.483391f,120.448775f,"10/1~10/31");
        db.append("台北文華東方酒店","台北市松山區敦化北路158號10548","0227156888",19,12500,4,R.drawable.hotel05,25.055780f,121.548389f,"1/31~3/15");
        db.append("喜瑞飯店 Ambience Hotel!","台北市長安東路一段64號104","0225410077",20,7500,4,R.drawable.hotel06,25.047968f,121.529348f,"No Discount");
        db.append("新驛旅店(台北車站三館)","台北市大同區長安西路77號103","0277327777",8,4200,5,R.drawable.hotel07,25.051080f,121.516692f,"7/1~8/31");
        db.append("太魯閣晶英酒店","花蓮縣秀林鄉天祥路18號太魯閣國家公園內972","038691155",10,20000,5,R.drawable.hotel08,24.182005f,121.494201f,"7/1~8/31");
    }

    class listAdapter extends CursorAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        public listAdapter(Context context, Cursor c) {
            super(context, c, 0);
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return mInflater.inflate(R.layout.list_layout, parent, false);
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            final String _name,_address,_phone,_rank,_discount;
            final int _num_room,_price;
            final float _latitube,_longitube;
            _name = cursor.getString(cursor.getColumnIndexOrThrow(HOTEL_NAME));
            _address = cursor.getString(cursor.getColumnIndexOrThrow(HOTEL_ADDRESS));
            _phone = cursor.getString(cursor.getColumnIndexOrThrow(HOTEL_PHONE));
            _num_room = cursor.getInt(cursor.getColumnIndexOrThrow(NUM_ROOM));
            _latitube = cursor.getFloat(cursor.getColumnIndexOrThrow(LATITUBE));
            _longitube = cursor.getFloat(cursor.getColumnIndexOrThrow(LONGITUBE));
            _rank = cursor.getString(cursor.getColumnIndexOrThrow(RANK));
            _price = cursor.getInt(cursor.getColumnIndexOrThrow(PRICE));
            _discount = cursor.getString(cursor.getColumnIndexOrThrow(DISCOUNT));

            TextView name = (TextView) view.findViewById(R.id.hotelName);
            TextView price = (TextView) view.findViewById(R.id.money);
            TextView rank = (TextView) view.findViewById(R.id.stars);
            ImageView image = (ImageView) view.findViewById(R.id.imageView);
            ImageView star = (ImageView) view.findViewById(R.id.star);
            Button map_open = (Button) view.findViewById(R.id.map_open);
            //跑馬燈設定
            name.setSelected(true);

            //使用Glide載入圖片並顯示，以此降低listview捲動卡頓問題
            Glide.with(getApplicationContext()).load(cursor.getInt(cursor.getColumnIndexOrThrow(IMAGE))).into(image);
            Glide.with(getApplicationContext()).load(R.drawable.star).into(star);

            name.setText(_name);
            price.setText("\t"+"價格 : $"+_price);
            rank.setText("\t"+"評價 : "+_rank);

            map_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent map = new Intent(MainActivity.this,MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",_name);
                    bundle.putString("address",_address);
                    bundle.putString("phone",_phone);
                    bundle.putInt("room_num",_num_room);
                    bundle.putFloat("latitube",_latitube);
                    bundle.putFloat("longitube",_longitube);
                    bundle.putString("discount",_discount);
                    map.putExtras(bundle);
                    startActivity(map);
                }
            });
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent map = new Intent(MainActivity.this,MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",_name);
                    bundle.putString("address",_address);
                    bundle.putString("phone",_phone);
                    bundle.putInt("room_num",_num_room);
                    bundle.putFloat("latitube",_latitube);
                    bundle.putFloat("longitube",_longitube);
                    bundle.putString("discount",_discount);
                    map.putExtras(bundle);
                    startActivity(map);
                }
            });
        }
    }

    public void UpdateAdapter(Cursor cursor){
        if(cursor != null && cursor.getCount() > 0){
            listview.setAdapter(new listAdapter(this,cursor));
        }else{
            Toast.makeText(this,"未查詢到資料或沒有此資料",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.drop();
        db.close();
    }
}