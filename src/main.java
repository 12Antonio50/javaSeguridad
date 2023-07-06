import java.security.*;
import javax.crypto.*;
import java.io.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class main {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        Security.addProvider(new BouncyCastleProvider()); // Agrega el proveedor Bouncy Castle

        System.out.println("1.- Vamos a crear las llaves publica y privada de RSA");

        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA", "BC"); // Genera un par de claves RSA
        keygen.initialize(2048); // Tamaño de clave: 2048 bits

        KeyPair clavesRSA = keygen.genKeyPair(); // Genera el par de claves

        PublicKey clavePublica = clavesRSA.getPublic(); // Obtiene la clave pública
        PrivateKey clavePrivada = clavesRSA.getPrivate(); // Obtiene la clave privada

        System.out.println("Clave publica: " + clavePublica); // Imprime la clave pública
        System.out.println("Clave privada: " + clavePrivada); // Imprime la clave privada

        System.out.println("2.- Introduzca el texto que desea cifrar maximo 64 caracteres");

        byte[] bufferPlano = leerLinea(System.in); // Lee el texto plano desde la entrada estándar

        Cipher cifrador = Cipher.getInstance("RSA", "BC"); // Crea un objeto Cipher para cifrar/descifrar RSA

        cifrador.init(Cipher.ENCRYPT_MODE, clavePublica); // Inicializa el cifrador en modo de cifrado con la clave pública

        System.out.println("3.- Ciframos con la clave publica:");
        byte[] bufferCifrado = cifrador.doFinal(bufferPlano); // Cifra el texto plano y obtiene el texto cifrado
        System.out.println("Texto Cifrado:");
        mostrarBytes(bufferCifrado); // Muestra el texto cifrado

        cifrador.init(Cipher.DECRYPT_MODE, clavePrivada); // Inicializa el cifrador en modo de descifrado con la clave privada

        System.out.println("4.- Desciframos con la clave privada:");
        byte[] bufferPlano2 = cifrador.doFinal(bufferCifrado); // Descifra el texto cifrado y obtiene el texto plano
        String textoDescifrado = new String(bufferPlano2);
        System.out.println("Texto Descifrado: " + textoDescifrado);
        System.out.println();
    }

    public static byte[] leerLinea(InputStream in) throws IOException {
        byte[] buffer1 = new byte[1000];
        int i = 0;
        byte c;
        c = (byte) in.read();
        while ((c != '\n') && (i < 1000)) {
            buffer1[i] = c;
            c = (byte) in.read();
            i++;
        }

        byte[] buffer2 = new byte[i];
        for (int j = 0; j < i; j++) {
            buffer2[j] = buffer1[j];
        }
        return buffer2;
    }

    public static void mostrarBytes(byte[] buffer) {
        for (byte b : buffer) {
            System.out.print(String.format("%02X ", b));
        }
        System.out.println();
    }
}
