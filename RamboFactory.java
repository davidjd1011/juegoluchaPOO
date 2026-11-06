public class RamboFactory extends PersonajeFactory {
    @Override
    public Personaje crearPersonaje(String nombre) {
        return new Rambo(nombre, new Metralleta());
    }
}
