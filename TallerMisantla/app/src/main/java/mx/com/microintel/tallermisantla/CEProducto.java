package mx.com.microintel.tallermisantla;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.com.microintel.tallermisantla.modelo.DAOProductos;
import mx.com.microintel.tallermisantla.modelo.Producto;

public class CEProducto extends AppCompatActivity {


    @BindView(R.id.imgProducto)
    ImageView imgProducto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.txtNombre)
    EditText txtNombre;
    @BindView(R.id.tilNombre)
    TextInputLayout tilNombre;
    @BindView(R.id.txtDescripcion)
    EditText txtDescripcion;
    @BindView(R.id.tilDescripcion)
    TextInputLayout tilDescripcion;
    @BindView(R.id.txtPrecio)
    EditText txtPrecio;
    @BindView(R.id.tilPrecio)
    TextInputLayout tilPrecio;
    @BindView(R.id.txtInventario)
    EditText txtInventario;
    @BindView(R.id.tilInventario)
    TextInputLayout tilInventario;
    @BindView(R.id.txtGenero)
    TextView txtGenero;
    @BindView(R.id.spnGenero)
    Spinner spnGenero;
    @BindView(R.id.activity_ceproducto)
    LinearLayout activityCeproducto;
    @BindView(R.id.btnFab)
    FloatingActionButton btnFab;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;
    DAOProductos daoProductos;
    boolean isEditar = false;
    Producto producto;
    int position = -1;
    @BindView(R.id.txtFoto)
    EditText txtFoto;
    @BindView(R.id.tilFoto)
    TextInputLayout tilFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceproducto);
        ButterKnife.bind(this);

        daoProductos = new DAOProductos(this);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            isEditar = mBundle.getBoolean("isEditar");
            producto = mBundle.getParcelable("producto");
            position = mBundle.getInt("position");
        } else {
            producto = new Producto();
        }

        setupAppBar();

        if (isEditar) {
            setProducto();
        }

        //Sí se regresa a la actividad anterior sin realizar cambios,
        //enviamos un resultado erroneo para no afectar la lista principal
        setResult(-1);
    }

    /**
     * Configura el titulo y acciones de la activity de acuerdo a la bandera @isEditar
     */
    private void setupAppBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (isEditar) {
            collapseToolbar.setTitle("Editar producto");
        } else {
            collapseToolbar.setTitle("Producto Nuevo");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setProducto() {
        txtNombre.setText(producto.nombre);
        txtDescripcion.setText(producto.descripcion);
        txtPrecio.setText(producto.precio);
        txtInventario.setText(producto.inventario);
        txtFoto.setText(producto.foto);
        Glide.with(this)
                .load(producto.foto)
                .placeholder(R.drawable.mountain)
                .error(R.drawable.mountain)
                .into(imgProducto);

        String[] categorias = getResources().getStringArray(R.array.categoria);
        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].contains(producto.categoria)) {
                spnGenero.setSelection(i);
                break;
            }
        }
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }

    @OnClick(R.id.btnGuardar)
    public void onGuardar(View view) {
        String categoria = getResources().getStringArray(R.array.categoria)[spnGenero.getSelectedItemPosition()];
        producto.nombre = getText(txtNombre);
        producto.descripcion = getText(txtDescripcion);
        producto.foto = getText(txtFoto);
        producto.categoria = categoria;
        producto.precio = getText(txtPrecio);
        producto.inventario = getText(txtInventario);
        producto.foto = getText(txtFoto);
        int result;
        Intent intent = new Intent();
        if (isEditar) {
            result = daoProductos.updateProducto(producto);
            intent.putExtra("position", position);
        } else {
            result = daoProductos.insertProducto(producto);
        }
        if (result == -1) {
            Toast.makeText(this, R.string.error_db, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.succes_db, Toast.LENGTH_LONG).show();
        }
        setResult(result, intent);
        finish();
    }
}
