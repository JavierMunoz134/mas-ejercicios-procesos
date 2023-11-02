import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProductorConsumidor {
    public static void main(String[] args) {
        BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(4);
        Random random = new Random();

        // Productor
        Thread productor = new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                try {
                    int elemento = i;
                    Thread.sleep(random.nextInt(2000));  // Tiempo aleatorio entre 0 y 2 segundos
                    System.out.println("Productor produjo " + elemento);
                    buffer.put(elemento); // Intentar poner el elemento en el buffer
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Consumidor
        Thread consumidor = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(random.nextInt(2000));  // Tiempo aleatorio entre 0 y 2 segundos
                    int elemento = buffer.take(); // Intentar obtener un elemento del buffer
                    System.out.println("Consumidor consumió " + elemento);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        productor.start();
        consumidor.start();

        try {
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

