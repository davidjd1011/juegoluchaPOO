import java.util.Random;

public abstract class Personaje {
    protected static final Random RAND = new Random();
    private final String nombre;
    private final int puntosDeVidaMax;
    private int puntosDeVida;
    private final Arma arma;
    protected boolean critico = false;

    public Personaje(String nombre, int puntosDeVida, Arma arma) {
        this.nombre = nombre;
        this.puntosDeVidaMax = puntosDeVida;
        this.puntosDeVida = puntosDeVida;
        this.arma = arma;
    }

    public int atacar(Personaje op) {
        critico = false;
        int d = calcularDano();
        op.recibirDano(d);
        return d;
    }

    public int atacarPesado(Personaje op) {
        critico = false;
        if (RAND.nextInt(100) < 30) return 0; // 30% fallo
        critico = true;
        int d = (int) Math.round(calcularDano() * 1.6);
        op.recibirDano(d);
        return d;
    }

    public int curar() {
        critico = false;
        int c = RAND.nextInt(11) + 15;
        puntosDeVida = Math.min(puntosDeVidaMax, puntosDeVida + c);
        return c;
    }

    protected int calcularDano() {
        return RAND.nextInt(arma.getDanoMax() - arma.getDanoMin() + 1) + arma.getDanoMin();
    }

    public void recibirDano(int d) {
        puntosDeVida = Math.max(0, puntosDeVida - d);
    }

    public boolean estaVivo() { return puntosDeVida > 0; }
    public String getNombre() { return nombre; }
    public int getPuntosDeVida() { return puntosDeVida; }
    public int getPuntosDeVidaMax() { return puntosDeVidaMax; }
    public Arma getArma() { return arma; }
    public boolean fueCritico() { return critico; }

    public abstract String getClase();
    public abstract String[] getDibujo();
}
