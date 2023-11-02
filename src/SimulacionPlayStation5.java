import java.util.Random;

public class SimulacionPlayStation5 {
    public static void main(String[] args) {
        int totalClientes = 200;
        int unidadesDisponibles = 20;

        Tienda tienda = new Tienda(unidadesDisponibles);

        for (int i = 1; i <= totalClientes; i++) {
            Cliente cliente = new Cliente(i, tienda);
            Thread thread = new Thread(cliente);
            thread.start();
        }
    }

    static class Tienda {
        private int unidadesDisponibles;
        private Object puerta = new Object();

        public Tienda(int unidadesDisponibles) {
            this.unidadesDisponibles = unidadesDisponibles;
        }

        public boolean entrar(int clienteId) {
            synchronized (puerta) {
                if (unidadesDisponibles > 0) {
                    unidadesDisponibles--;
                    System.out.println("Cliente " + clienteId + " entró y compró una PlayStation 5.");
                    return true;
                } else {
                    System.out.println("Cliente " + clienteId + " entró, pero no quedan PlayStation 5.");
                    return false;
                }
            }
        }
    }

    static class Cliente implements Runnable {
        private int clienteId;
        private Tienda tienda;
        private Random random = new Random();

        public Cliente(int clienteId, Tienda tienda) {
            this.clienteId = clienteId;
            this.tienda = tienda;
        }

        @Override
        public void run() {
            int intentos = 0;
            while (intentos < 10) {
                try {
                    Thread.sleep(random.nextInt(2000));  // Tiempo aleatorio de espera
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (tienda.entrar(clienteId)) {
                    return;
                } else {
                    intentos++;
                }
            }
            System.out.println("Cliente " + clienteId + " desistió y se fue sin comprar.");
        }
    }
}
