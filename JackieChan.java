public class JackieChan extends Personaje {
    public JackieChan(String nombre, Arma arma) {
        super(nombre, 120, arma);
    }

    @Override
    public String getClase() {
        return "Jackie Chan";
    }

    @Override
    public String[] getDibujo() {
        return new String[] {
            " #*        #%@@@@@%         ",
            " #*+       #%##***#@        ",
            " #*++#@@#    ##*********+*  ",
            "   #@@@@@@% *+#*+***##*++++ ",
            "     #@@@@@@@#####**##*++=+ ",
            "       #%@@@@@@@@@@@%#**+=  ",
            "      *+*# #@@@@@@@@@@@@@%# ",
            "    #**      #@@@@@@@@@@@@# ",
            "   #**             #@@@@@@@*",
            "                     %@@@@@#",
            "                      @@@@@#",
            "                   #@@@@@@% ",
            "               #@@@@%##     ",
            "             ###            "
        };
    }
}
