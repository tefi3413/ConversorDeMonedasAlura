import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistorialConversiones {
    private static final String ARCHIVO_HISTORIAL = "historial.json";
    private List<Conversion> conversiones;

    public HistorialConversiones(){
        conversiones = cargarHistorial();
    }

    private List<Conversion>cargarHistorial(){
        try {
            String contenido = Files.readString(Paths.get(ARCHIVO_HISTORIAL));
            return  new Gson().fromJson(contenido, new TypeToken<List<HistorialConversiones.Conversion>>(){}.getType());
        } catch (IOException e){
            return new ArrayList<>();
        }
    }

    public void agregarConversion(double cantidad, String monedaOrigen, String monedaDestino, double resultado){
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = fechaHora.format(formatter);

        monedaOrigen = monedaOrigen.toUpperCase();
        monedaDestino = monedaDestino.toUpperCase();

        Conversion conversion = new Conversion(cantidad, monedaOrigen, monedaDestino, resultado, fechaHoraFormateada);
        conversiones.add(conversion);
        guardarHistorial();
    }

    private void  guardarHistorial(){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(conversiones);
            Files.writeString(Paths.get(ARCHIVO_HISTORIAL),json);
        }catch (IOException e){
            System.out.println("Error al guardar el historial de conversiones: "+ e.getMessage());
        }
    }

    public List<Conversion> getConversiones(){
        return conversiones;
    }

    public static  class  Conversion{
        private double cantidad;
        private String monedaOrigen;
        private String monedaDestino;
        private double resultado;
        private  String fechaHora;

        public Conversion(double cantidad, String monedaOrigen, String monedaDestino, double resultado, String fechaHora) {
            this.cantidad = cantidad;
            this.monedaOrigen = monedaOrigen;
            this.monedaDestino = monedaDestino;
            this.resultado = resultado;
            this.fechaHora = fechaHora;
        }

        public double getCantidad() {
            return cantidad;
        }

        public void setCantidad(double cantidad) {
            this.cantidad = cantidad;
        }

        public String getMonedaOrigen() {
            return monedaOrigen;
        }

        public void setMonedaOrigen(String monedaOrigen) {
            this.monedaOrigen = monedaOrigen;
        }

        public String getMonedaDestino() {
            return monedaDestino;
        }

        public void setMonedaDestino(String monedaDestino) {
            this.monedaDestino = monedaDestino;
        }

        public double getResultado() {
            return resultado;
        }

        public void setResultado(double resultado) {
            this.resultado = resultado;
        }

        public String getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(String fechaHora) {
            this.fechaHora = fechaHora;
        }

        public String getMonedaorigen() {
            return  monedaOrigen;
        }
    }


}