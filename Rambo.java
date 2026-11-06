public class Rambo extends Personaje {
    public Rambo(String nombre, Arma arma) {
        super(nombre, 125, arma);
    }

    @Override
    public String getClase() {
        return "Rambo";
    }

    @Override
    public String[] getDibujo() {
        return new String[] {
            "                            ",
            "              %#=           ",
            "            %%%%%@%         ",
            "           %@##=+#@         ",
            "           %%+.--+@         ",
            "          @%%*==+#%@        ",
            "          %%@**#*#%         ",
            "          ##+*=::.::---     ",
            "       =-:#==:.:..:*..:-    ",
            "      *-+#+=. :...:##:.-=   ",
            "      =:-*:. .-:=%%%+..=+   ",
            "    +=-+%%%%%%%%%#**=.:-=   ",
            "   ++=%%#%%%#..%%==+#+--=   ",
            "   #--+@%%%* -*%%%==#+-+=   "
        };
    }
}
