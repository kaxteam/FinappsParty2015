package xml.restfuldroid.parser;

/**
 * Created by zenbook on 30/03/15.
 */
public interface CustomResponseParser {
    <T> T deserializer(byte[] data, Class<T> c);
}
