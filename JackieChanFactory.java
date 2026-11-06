public class JackieChanFactory extends PersonajeFactory {
    @Override
    public Personaje crearPersonaje(String nombre) {
        return new JackieChan(nombre, new Nunchaku());
    }
}
