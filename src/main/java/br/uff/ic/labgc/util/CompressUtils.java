package br.uff.ic.labgc.util;

import br.uff.ic.labgc.exception.CompressionException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Esta classe implementa métodos para compactar e descompactar um array de
 * bytes que representam o conteúdo de um arquivo.
 *
 * @author Cristiano
 */
public class CompressUtils {

    /**
     * Compacta um array de bytes.
     *
     * @param input - o array de bytes a ser compactado.
     * @return um array de bytes compactado
     * @throws CompressionException
     */
    public static byte[] deflateBytes(byte[] input) throws CompressionException {
        byte[] output = null;
        try {
            Deflater df = new Deflater();
            df.setLevel(Deflater.DEFAULT_COMPRESSION);
            df.setInput(input);

            ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
            df.finish();
            byte[] buff = new byte[1024];
            while (!df.finished()) {
                int count = df.deflate(buff);
                baos.write(buff, 0, count);
            }
            baos.close();
            output = baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CompressUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new CompressionException("Ocorreu um erro ao compactar.", ex);
        }

        return output;
    }

    /**
     * Descompacta um array de bytes.
     *
     * @param input o array de bytes a ser descompactado.
     * @return um array de bytes descompactado
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws DataFormatException
     */
    public static byte[] inflateBytes(byte[] input) throws CompressionException {
        byte[] output = null;
        try {
            Inflater ifl = new Inflater();
            ifl.setInput(input);

            ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
            byte[] buff = new byte[1024];
            while (!ifl.finished()) {
                int count = ifl.inflate(buff);
                baos.write(buff, 0, count);
            }
            baos.close();
            output = baos.toByteArray();
        } catch (IOException ioex) {
            Logger.getLogger(CompressUtils.class.getName()).log(Level.SEVERE, null, ioex);
            throw new CompressionException("Ocorreu um erro ao descompactar.", ioex);
        } catch (DataFormatException dfex) {
            Logger.getLogger(CompressUtils.class.getName()).log(Level.SEVERE, null, dfex);
            throw new CompressionException("Ocorreu um erro ao descompactar.", dfex);
        }
        return output;
    }
}
