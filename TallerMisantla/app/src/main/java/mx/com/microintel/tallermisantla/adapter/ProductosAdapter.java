package mx.com.microintel.tallermisantla.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.microintel.tallermisantla.CEProducto;
import mx.com.microintel.tallermisantla.MainActivity;
import mx.com.microintel.tallermisantla.R;
import mx.com.microintel.tallermisantla.events.EliminarProductoEvent;
import mx.com.microintel.tallermisantla.modelo.DAOProductos;
import mx.com.microintel.tallermisantla.modelo.Producto;

import static mx.com.microintel.tallermisantla.MainActivity.REQUEST_EDIT;

/**
 * Created by fernandostone on 17/11/16.
 */

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    DAOProductos daoProductos;
    ArrayList<Producto> productos;
    Context context;

    public ProductosAdapter(Context context){
        this.context = context;
        daoProductos = new DAOProductos(context);
        productos = daoProductos.getProductos();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.toolbar.getMenu().clear();
        holder.toolbar.inflateMenu(R.menu.menu_producto);
        holder.txtNombre.setText(productos.get(position).nombre);
        holder.producto = productos.get(position);

        Glide.with(holder.mView.getContext())
                .load(holder.producto.foto)
                //.signature(new MediaStoreSignature(mimeType, dateModified, orientation))
                .placeholder(R.drawable.mountain)
                .error(R.drawable.mountain)
                .into(holder.imgPersona);

        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_editar:
                        Intent intent  = new Intent(holder.itemView.getContext(), CEProducto.class);
                        intent.putExtra("isEditar", true);
                        intent.putExtra("producto", holder.producto);
                        intent.putExtra("position", ((MainActivity)holder.itemView.getContext()).rvModulo.getChildAdapterPosition(holder.itemView));
                        ((MainActivity)holder.itemView.getContext()).startActivityForResult(intent, REQUEST_EDIT);
                        return true;
                    case R.id.action_eliminar:
                        EventBus.getDefault().post(new EliminarProductoEvent(holder.itemView, holder.producto));
                        return true;
                }
                return false;
            }
        });
    }



    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void update() {
        if(daoProductos==null){
            daoProductos = new DAOProductos(context);
        }
        productos = daoProductos.getProductos();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPersona)
        public ImageView imgPersona;
        @BindView(R.id.txtNombre)
        public TextView txtNombre;
        @BindView(R.id.btnAddPerson)
        public ImageButton btnAddPerson;
        @BindView(R.id.btnLike)
        public ImageButton btnLike;
        @BindView(R.id.toolbar)
        public Toolbar toolbar;
        public View mView;
        public Producto producto;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            ButterKnife.bind(this, v);
        }
    }
}
