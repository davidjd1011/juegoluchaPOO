public class VanDammeFactory extends PersonajeFactory {
    @Override
    public Personaje crearPersonaje(String nombre) {
        return new VanDamme(nombre, new VendajeMuayThai());
    }
}
