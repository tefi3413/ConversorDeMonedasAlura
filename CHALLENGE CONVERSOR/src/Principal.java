
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        ConversorMonedas conversor = new ConversorMonedas();

        HistorialConversiones historial = new HistorialConversiones();

        Scanner scanner = new Scanner(System.in);

        //mapeo de los codigos de monedas
        Map<String, String> codigosMoneda = new HashMap<>();
        codigosMoneda.put("USD", "Dólar Estadounidense  -ESTADOS UNIDOS-");
        codigosMoneda.put("ARS", "Peso Argentino   -ARGENTINA-");
        codigosMoneda.put("COP", "Peso Colombiano  -COLOMBIA-");
        codigosMoneda.put("BRL", "Real Brasileño   -BRASIL-");


        while (true) {

            System.out.println("****************** CONVERSOR DE MONEDAS ***************");
            System.out.println("**************************Bienvenidos al conversor de monedas ***********************");
            System.out.println("Seleccione una opcion: ");
            System.out.println("1)Convertir monedas.\n"+
                    "2)Ingresar al historial de conversiones.\n"+
                    "3)Salir.\n");



            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Conversiones de monedas disponibles:");
                    System.out.println("|CODIGO|  MONEDA  |    PAIS   | ");
                    for (Map.Entry<String, String> entry : codigosMoneda.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }

                    System.out.println("Ingrese el código de moneda de origen: ");
                    String monedaOrigen = scanner.nextLine();
                    monedaOrigen = monedaOrigen.toUpperCase();

                    System.out.println("Ingrese el código de la moneda a  convertir: ");
                    String monedaDestino = scanner.nextLine();
                    monedaDestino = monedaDestino.toUpperCase();

                    System.out.println("Ingrese la cantidad a convertir: ");
                    double cantidad = scanner.nextDouble();
                    scanner.nextLine();

                    try {
                        RespuestaAPI respuesta = conversor.obtenerTasasCambio(monedaOrigen);
                        if (respuesta != null) {
                            double resultado = conversor.convertir(cantidad, monedaOrigen, monedaDestino, respuesta.getConversion_rates());
                            System.out.println();
                            System.out.println("Resultado: "+cantidad + " " + monedaOrigen + " es equivalente a : " + resultado + " " + monedaDestino);
                            System.out.println("CONVERSIÓN EXITOSA");

                            // Agregando la conversión  al historial
                            historial.agregarConversion(cantidad, monedaOrigen, monedaDestino, resultado);
                        }
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Error al obtener las tasas de cambio: " + e.getMessage());
                    }

                    break;
                case 2:

                    List<HistorialConversiones.Conversion> conversions = historial.getConversiones();
                    if (conversions.isEmpty()){
                        System.out.println("No hay conversiones en su historial");
                    }else {
                        System.out.println("-_-_-_-_-_-_-_-_HISTORIAL DE CONVERSIONES-_-_-_-_-_-_-_-");
                        for (HistorialConversiones.Conversion conversion : conversions) {
                            System.out.println(conversion.getFechaHora() + ": " +
                                    conversion.getCantidad() + " " +
                                    conversion.getMonedaorigen() + " -> " +
                                    conversion.getResultado() + " " +
                                    conversion.getMonedaDestino());
                        }
                        System.out.println();
                    }

                case 3:

                    System.out.println("GRACIAS POR UTILIZAR NUESTROS SERVICIOS, ¡HASTA LA PRÓXIMA!");
                    return;
                default:
                    System.out.println("OpciÓn inválida, seleccione una opciÓn valida del menú.");
            }
        }
    }
}
