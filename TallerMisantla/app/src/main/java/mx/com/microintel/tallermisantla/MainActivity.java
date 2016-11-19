package mx.com.microintel.tallermisantla;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.microintel.tallermisantla.adapter.ProductosAdapter;
import mx.com.microintel.tallermisantla.events.EliminarProductoEvent;
import mx.com.microintel.tallermisantla.modelo.DAOProductos;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvModulo)
    public RecyclerView rvModulo;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    ProductosAdapter mTabAdapter;
    DAOProductos daoProductos;

    public static final int REQUEST_EDIT = 10, REQUEST_INSERT = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Productos");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CEProducto.class);
                startActivityForResult(intent, REQUEST_INSERT);
            }
        });
        mTabAdapter = new ProductosAdapter(this);
        // use this setting to improve perform  ance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvModulo.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvModulo.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        rvModulo.setAdapter(mTabAdapter);

        daoProductos = new DAOProductos(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_INSERT && resultCode!= -1){
            if (mTabAdapter != null) {
                mTabAdapter.update();
                int position = mTabAdapter.getItemCount() - 1;
                if(position == 0){
                    mTabAdapter.notifyDataSetChanged();
                }
                else{
                    mTabAdapter.notifyItemInserted(mTabAdapter.getItemCount() - 1);
                }
            }
        }
        else if(requestCode == REQUEST_EDIT && resultCode!= -1){
            if (mTabAdapter != null) {
                mTabAdapter.update();
                mTabAdapter.notifyItemChanged(data.getIntExtra("position", 0));
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEliminarProductoEvent(EliminarProductoEvent event) {
        if(daoProductos.deleteProducto(event.producto.idProducto)!=0){
            Toast.makeText(this, R.string.delete_db, Toast.LENGTH_LONG).show();
            if (mTabAdapter != null) {
                mTabAdapter.update();
                mTabAdapter.notifyItemRemoved(rvModulo.getChildAdapterPosition(event.view));
            }
        }
        else{
            Toast.makeText(this, R.string.error_db, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
