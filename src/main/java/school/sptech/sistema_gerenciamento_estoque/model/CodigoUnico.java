package school.sptech.sistema_gerenciamento_estoque.model;
import java.security.SecureRandom;

public class CodigoUnico {
    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int VERIFICATION_CODE_SIZE = 8;

    public static String generateVerificationCode() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[(int) Math.ceil(VERIFICATION_CODE_SIZE * 5 /8.0)];
        secureRandom.nextBytes(bytes);

        StringBuilder sb = new StringBuilder(VERIFICATION_CODE_SIZE);

        int buffer = 0;
        int bitsLeft = 0;

        for (byte b : bytes){
            buffer = (buffer << 8) | (b & 0xff);
            bitsLeft += 8;

            while (bitsLeft >= 5 && sb.length() < VERIFICATION_CODE_SIZE){
                int index = (buffer >> (bitsLeft - 5)) & 31;
                bitsLeft -= 5;
                sb.append(ALPHABET.charAt(index));
            }
        }
        return sb.toString();
    }

}
