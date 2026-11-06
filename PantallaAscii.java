import java.awt.*;
import javax.swing.*;

public class PantallaAscii extends JFrame {
    private static PantallaAscii instancia;
    private final JTextArea area = new JTextArea();
    private final JLabel estado = new JLabel("", SwingConstants.CENTER);
    private final JPanel pnlBotones = new JPanel();

    // Cada mitad de la cabecera mide 34 caracteres -> ancho total interno = 68
    private static final int COL = 34;
    private static final int BARRA = 10;

    private PantallaAscii() {
        setTitle("Arena de Lucha POO - Acción Clásica");
        setSize(900, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Área de texto monospaciada sin saltos ni desbordes
        area.setEditable(false);
        area.setBackground(Color.BLACK);
        area.setForeground(new Color(50, 255, 50));
        area.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
        area.setMargin(new Insets(10, 15, 10, 15));
        
        // Deshabilitar scroll horizontal para asegurar que todo encaje a la perfección
        JScrollPane scroll = new JScrollPane(area);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        // Barra inferior de botones y estado
        JPanel sur = new JPanel(new BorderLayout());
        sur.setBackground(new Color(25, 25, 25));
        estado.setForeground(Color.WHITE);
        estado.setFont(new Font("SansSerif", Font.BOLD, 14));
        estado.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        pnlBotones.setBackground(new Color(25, 25, 25));
        pnlBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        sur.add(estado, BorderLayout.NORTH);
        sur.add(pnlBotones, BorderLayout.SOUTH);
        add(sur, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static PantallaAscii getInstancia() {
        if (instancia == null) {
            instancia = new PantallaAscii();
        }
        return instancia;
    }

    public void setBotones(JButton... btns) {
        pnlBotones.removeAll();
        for (JButton b : btns) {
            b.setFont(new Font("SansSerif", Font.BOLD, 13));
            b.setFocusPainted(false);
            pnlBotones.add(b);
        }
        pnlBotones.revalidate();
        pnlBotones.repaint();
    }

    public void setBotonesHabilitados(boolean ok) {
        for (Component c : pnlBotones.getComponents()) {
            c.setEnabled(ok);
        }
    }

    public void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    // Dibuja el marco superior con estadísticas y el área de combate central con los personajes
    public void dibujar(Personaje p1, Personaje p2, int off1, int off2, boolean impacto, boolean curando, boolean esJ1, String msg) {
        StringBuilder sb = new StringBuilder();
        String borde = "+" + "-".repeat(COL * 2) + "+\n";
        String sepSeccion = "+" + "=".repeat(COL * 2) + "+\n";

        // --- 1. CABECERA: Nombre, Vida y Arma perfectamente balanceados ---
        sb.append(borde);
        sb.append(fila(" " + p1.getNombre() + " [" + p1.getClase() + "]", 
                       " " + p2.getNombre() + " [" + p2.getClase() + "]"));
        sb.append(fila(" HP: " + barra(p1) + " " + p1.getPuntosDeVida() + "/" + p1.getPuntosDeVidaMax(), 
                       " HP: " + barra(p2) + " " + p2.getPuntosDeVida() + "/" + p2.getPuntosDeVidaMax()));
        sb.append(fila(" Arma: " + p1.getArma().getNombre() + " (" + p1.getArma().getDanoMin() + "-" + p1.getArma().getDanoMax() + ")", 
                       " Arma: " + p2.getArma().getNombre() + " (" + p2.getArma().getDanoMin() + "-" + p2.getArma().getDanoMax() + ")"));
        sb.append(borde);

        // --- 2. ARENA DE PELEA: Personajes en combate (ancho matemático fijo = 66 chars) ---
        String[] d1 = p1.getDibujo();
        String[] d2 = p2.getDibujo();

        // Base entre personajes = 10 espacios. Al avanzar uno, la distancia se reduce.
        int sep = Math.max(0, 10 - off1 - off2);

        for (int i = 0; i < d1.length; i++) {
            String l1 = d1[i];
            String l2 = d2[i];

            // Efecto visual de curación en el personaje correspondiente
            if (curando && esJ1) {
                l1 = "*" + l1.substring(1, l1.length() - 1) + "*";
            } else if (curando && !esJ1) {
                l2 = "*" + l2.substring(1, l2.length() - 1) + "*";
            }

            // Efecto de impacto en el centro sin deformar el ancho de línea
            String espacioCentro;
            if (impacto && (i == 4 || i == 5)) {
                espacioCentro = "*POW!*".substring(0, Math.min(6, Math.max(1, sep + 1)));
                if (espacioCentro.length() < sep) {
                    espacioCentro = espacioCentro + " ".repeat(sep - espacioCentro.length());
                }
            } else {
                espacioCentro = " ".repeat(sep);
            }

            // Construcción con longitud estrictamente controlada (off1 + 28 + sep + 28 + off2 = 66)
            String cuerpo = " ".repeat(off1) + l1 + espacioCentro + l2 + " ".repeat(off2);
            sb.append("| ").append(ajustar(cuerpo, COL * 2 - 2)).append(" |\n");
        }

        // --- 3. PANEL DE SUCESOS ---
        sb.append(sepSeccion);
        sb.append("| ").append(centrar(msg, COL * 2 - 2)).append(" |\n");
        sb.append(sepSeccion);

        SwingUtilities.invokeLater(() -> {
            area.setText(sb.toString());
            estado.setText(msg);
        });
    }

    // Animación completa de avance, impacto y retroceso
    public void animarAtaque(Personaje p1, Personaje p2, boolean esJ1, int dano, String tipo) {
        Personaje atq = esJ1 ? p1 : p2;
        Personaje def = esJ1 ? p2 : p1;
        int maxDesplazamiento = 8;

        // Fase 1: Avance
        for (int i = 0; i <= maxDesplazamiento; i += 2) {
            int o1 = esJ1 ? i : 0;
            int o2 = esJ1 ? 0 : i;
            dibujar(p1, p2, o1, o2, false, false, esJ1, ">> " + atq.getNombre() + " se lanza con " + tipo + "...");
            pausa(50);
        }

        // Fase 2: Golpe / Fallo
        int o1 = esJ1 ? maxDesplazamiento : 0;
        int o2 = esJ1 ? 0 : maxDesplazamiento;
        if (dano > 0) {
            String crit = atq.fueCritico() ? " (¡GOLPE CRÍTICO!)" : "";
            dibujar(p1, p2, o1, o2, true, false, esJ1, "¡IMPACTO! " + atq.getNombre() + " causó " + dano + " de daño" + crit);
        } else {
            dibujar(p1, p2, o1, o2, false, false, esJ1, "¡FALLÓ! " + def.getNombre() + " esquivó el ataque ágilmente.");
        }
        pausa(850);

        // Fase 3: Retroceso a su guardia
        for (int i = maxDesplazamiento; i >= 0; i -= 2) {
            int r1 = esJ1 ? i : 0;
            int r2 = esJ1 ? 0 : i;
            dibujar(p1, p2, r1, r2, false, false, esJ1, ">> " + atq.getNombre() + " regresa a su guardia...");
            pausa(35);
        }
        pausa(200);
    }

    // Animación de curación con aura luminosa
    public void animarCuracion(Personaje p1, Personaje p2, boolean esJ1, int rec) {
        String nombre = (esJ1 ? p1 : p2).getNombre();
        for (int i = 0; i < 3; i++) {
            dibujar(p1, p2, 0, 0, false, true, esJ1, "✨ " + nombre + " canaliza energía y recupera +" + rec + " HP ✨");
            pausa(200);
            dibujar(p1, p2, 0, 0, false, false, esJ1, "✨ " + nombre + " canaliza energía y recupera +" + rec + " HP ✨");
            pausa(180);
        }
        pausa(200);
    }

    private String barra(Personaje p) {
        int llenos = (int) Math.round(((double) p.getPuntosDeVida() / p.getPuntosDeVidaMax()) * BARRA);
        llenos = Math.max(0, Math.min(BARRA, llenos));
        return "[" + "█".repeat(llenos) + "-".repeat(BARRA - llenos) + "]";
    }

    private String fila(String izq, String der) {
        return "|" + ajustar(izq, COL) + ajustar(der, COL) + "|\n";
    }

    private String ajustar(String s, int n) {
        if (s.length() >= n) return s.substring(0, n);
        return s + " ".repeat(n - s.length());
    }

    private String centrar(String s, int n) {
        if (s.length() >= n) return s.substring(0, n);
        int espacios = (n - s.length()) / 2;
        int resto = (n - s.length()) % 2;
        return " ".repeat(espacios) + s + " ".repeat(espacios + resto);
    }
}