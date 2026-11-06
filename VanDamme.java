public class VanDamme extends Personaje {
    public VanDamme(String nombre, Arma arma) {
        super(nombre, 120, arma);
    }

    @Override
    public String getClase() {
        return "Jean-Claude Van Damme";
    }

    @Override
    public String[] getDibujo() {
        return new String[] {
            " *#*                        ",
            " +%%*                       ",
            "    #@@#****           *%@@*",
            "      *#@@%%%#####%%%##%@%  ",
            "         *%@@@@%%#%%%%@@%%+ ",
            "            #@@@@@%%%%@@@@% ",
            "             +@@@@**+#%%@#  ",
            "              @@@           ",
            "           ***@@@           ",
            "              %@%           ",
            "              #@@           ",
            "              +@@           ",
            "               %@           ",
            "               #%%%#        "
        };
    }
}
