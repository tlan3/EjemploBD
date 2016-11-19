package mx.com.microintel.tallermisantla.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fernandostone on 18/11/16.
 */

 public class Producto implements Parcelable {
    public int idProducto;
    public String nombre;
    public String descripcion;
    public String foto;
    public String categoria;
    public String precio;
    public String inventario;

   public Producto(){}

   protected Producto(Parcel in) {
      idProducto = in.readInt();
      nombre = in.readString();
      descripcion = in.readString();
      foto = in.readString();
      categoria = in.readString();
      precio = in.readString();
      inventario = in.readString();
   }

   public String[] getValues(){
      return new String[]{ String.valueOf(idProducto), nombre, descripcion, foto, categoria , precio, inventario};
   }

   public static final Creator<Producto> CREATOR = new Creator<Producto>() {
      @Override
      public Producto createFromParcel(Parcel in) {
         return new Producto(in);
      }

      @Override
      public Producto[] newArray(int size) {
         return new Producto[size];
      }
   };

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeInt(idProducto);
      parcel.writeString(nombre);
      parcel.writeString(descripcion);
      parcel.writeString(foto);
      parcel.writeString(categoria);
      parcel.writeString(precio);
      parcel.writeString(inventario);
   }
}
