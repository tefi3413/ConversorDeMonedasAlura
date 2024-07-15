import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ConversorMonedas {
    private static final String API_KEY = "ae481ce36c10654bd4952f92";
    private static final String URL_BASE = "https://v6.exchangerate-api.com/v6/";

    public RespuestaAPI obtenerTasasCambio(String monedaOrigen) throws IOException, InterruptedException {
        String Direccion = URL_BASE + API_KEY + "/latest/" + monedaOrigen;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Direccion))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();

        return gson.fromJson(response.body(), RespuestaAPI.class);
    }

    public double convertir(double cantidad, String monedaOrigen, String monedaDestino, Map<String, Double> tasas) {
        Double tasaOrigenObj = tasas.get(monedaOrigen);
        Double tasaDestinoObj = tasas.get(monedaDestino);

        if (tasaOrigenObj != null && tasaDestinoObj != null) {
            double tasaOrigen = tasaOrigenObj;
            double tasaDestino = tasaDestinoObj;
            return (cantidad / tasaOrigen) * tasaDestino;
        } else {
            System.out.println("No se encontraron las tasas de cambio para las monedas seleccionadas, por favor seleccione otras monedas disponibles en el menú.");
            return 0.0;
        }
    }
}