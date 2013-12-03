package kaffee.kompressor;

/**
 *
 * @author vszakonyi
 */
public class Kompressor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lexer l = new Lexer();
        String s = l.compressLine("echo ( \"Here we go.\" ) ;");
        int i = 1;
    }
}
