
package adriel.act_6_dosto;

import java.util.*;

class Vertice {
    private String etiqueta;
    private double peso;

    public Vertice(String etiqueta, double peso) {
        this.etiqueta = etiqueta;
        this.peso = peso;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getPeso() {
        return peso;
    }
}

public class Grafo {
    private Map<String, List<Vertice>> listaAdyacencia;

    public Grafo() {
        listaAdyacencia = new HashMap<>();
    }

    public void agregarVertice(String etiqueta) {
        listaAdyacencia.put(etiqueta, new ArrayList<>());
    }

    public void agregarArista(String origen, String destino, double peso) {
        listaAdyacencia.get(origen).add(new Vertice(destino, peso));
    }

    public Map<String, Double> buscarRutaCorta(String origen) {
        Map<String, Double> distancias = new HashMap<>();
        PriorityQueue<Vertice> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(Vertice::getPeso));
        Set<String> visitados = new HashSet<>();

        for (String vertice : listaAdyacencia.keySet()) {
            if (vertice.equals(origen)) {
                distancias.put(vertice, 0.0);
                colaPrioridad.offer(new Vertice(vertice, 0.0));
            } else {
                distancias.put(vertice, Double.POSITIVE_INFINITY);
                colaPrioridad.offer(new Vertice(vertice, Double.POSITIVE_INFINITY));
            }
        }

        while (!colaPrioridad.isEmpty()) {
            Vertice actual = colaPrioridad.poll();
            String etiquetaActual = actual.getEtiqueta();

            if (visitados.contains(etiquetaActual)) {
                continue;
            }

            visitados.add(etiquetaActual);

            for (Vertice vecino : listaAdyacencia.get(etiquetaActual)) {
                String etiquetaVecino = vecino.getEtiqueta();
                double pesoArista = vecino.getPeso();
                double distanciaActual = distancias.get(etiquetaActual);
                double distanciaVecino = distancias.get(etiquetaVecino);

                if (distanciaActual + pesoArista < distanciaVecino) {
                    distancias.put(etiquetaVecino, distanciaActual + pesoArista);
                    colaPrioridad.offer(new Vertice(etiquetaVecino, distanciaActual + pesoArista));
                }
            }
        }

        return distancias;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Grafo grafo = new Grafo();

        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Crear grafo ");
            System.out.println("2. Búsqueda de ruta más corta (Dijkstra)");
            System.out.println("3. Salir ");
            System.out.print("Selecciona una de las siguientes opciones: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
    System.out.print("Ingrese los nodos siguiendo el siguiente formato: x,y,z ");
    String nodos = sc.next();
    String[] etiquetas = nodos.split(",");
    for (String etiqueta : etiquetas) {
        grafo.agregarVertice(etiqueta);
    }

    System.out.print("Ingrese las aristas siguiendo el siguiente formato: 'origen-destino-peso' (separadas por comas): ");
    String aristas = sc.next();
    String[] aristasArray = aristas.split(",");
    for (String arista : aristasArray) {
        String[] datosArista = arista.split("-");
        String origen = datosArista[0];
        String destino = datosArista[1];
        double peso = Double.parseDouble(datosArista[2]);
        grafo.agregarArista(origen, destino, peso);
    }

    System.out.println("Grafo! ");
    break;
case 2:
    if (grafo.listaAdyacencia.isEmpty()) {
        System.out.println("Cree un grafo primero.");
    } else {
        System.out.print("Ingrese el nodo origen: ");
        String origenDijkstra = sc.next();
        Map<String, Double> distancias = grafo.buscarRutaCorta(origenDijkstra);

        for (Map.Entry<String, Double> entry : distancias.entrySet()) {
            System.out.println("Distancia más corta desde " + origenDijkstra + " a " + entry.getKey() + ": " + entry.getValue());
        }
    }
    break;
case 3:
    System.out.println("Gracias por usar mi programa! ");
    System.exit(0);
default:
    System.out.println("Opción no válida");
    break;
}
        }
    }
}


