import javax.swing.*;

public class JuegoLucha {
    private final Personaje j1, j2;
    private final PantallaAscii gui = PantallaAscii.getInstancia();
    private boolean turnoJ1 = true, animando = false;

    public JuegoLucha(Personaje j1, Personaje j2) {
        this.j1 = j1;
        this.j2 = j2;
        configurarControles();
        gui.dibujar(j1, j2, 0, 0, false, false, true, ">> Turno de: " + j1.getNombre());
    }


    private void configurarControles() {
        JButton b1 = new JButton(" Ataque Rápido");
        JButton b2 = new JButton(" Ataque Pesado");
        JButton b3 = new JButton(" Sanación");

        b1.addActionListener(e -> turno(1));
        b2.addActionListener(e -> turno(2));
        b3.addActionListener(e -> turno(3));
        gui.setBotones(b1, b2, b3);
    }

    private void turno(int accion) {
        if (animando || !j1.estaVivo() || !j2.estaVivo()) return;
        gui.setBotonesHabilitados(false);

        new Thread(() -> {
            animando = true;
            Personaje atq = turnoJ1 ? j1 : j2, def = turnoJ1 ? j2 : j1;
            boolean esJ1 = turnoJ1;

            if (accion == 1) gui.animarAtaque(j1, j2, esJ1, atq.atacar(def), "Ataque Rápido");
            else if (accion == 2) gui.animarAtaque(j1, j2, esJ1, atq.atacarPesado(def), "Ataque Pesado");
            else if (accion == 3) gui.animarCuracion(j1, j2, esJ1, atq.curar());

            if (!def.estaVivo()) {
                gui.dibujar(j1, j2, 0, 0, false, false, esJ1, "🏆 ¡" + atq.getNombre().toUpperCase() + " HA GANADO LA PELEA! 🏆");
            } else {
                turnoJ1 = !turnoJ1;
                Personaje sig = turnoJ1 ? j1 : j2;
                gui.dibujar(j1, j2, 0, 0, false, false, turnoJ1, ">> Turno de: " + sig.getNombre() + " [" + sig.getClase() + "]");
                gui.setBotonesHabilitados(true);
            }
            animando = false;
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PersonajeFactory[] f = { new RamboFactory(), new JackieChanFactory(), new VanDammeFactory() };
            String[] ops = { "Rambo", "Jackie Chan", "Jean-Claude Van Damme" };

            String n1 = JOptionPane.showInputDialog(null, "Nombre Jugador 1:", "Jugador 1");
            int c1 = JOptionPane.showOptionDialog(null, "personaje para " + n1, "Clase J1", 0, 1, null, ops, ops[0]);
            String n2 = JOptionPane.showInputDialog(null, "Nombre Jugador 2:", "Jugador 2");
            int c2 = JOptionPane.showOptionDialog(null, "Personaje para " + n2, "Clase J2", 0, 1, null, ops, ops[1]);
            

            Personaje p1 = f[Math.max(0, c1)].crearPersonaje(n1 != null && !n1.isBlank() ? n1 : "Jugador 1");
            Personaje p2 = f[Math.max(0, c2)].crearPersonaje(n2 != null && !n2.isBlank() ? n2 : "Jugador 2");

            new JuegoLucha(p1, p2);
        });
    }
}
