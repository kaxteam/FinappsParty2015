package xml.restfuldroid.parser.simpleparser;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import xml.restfuldroid.core.WebService;
import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;
import xml.restfuldroid.parser.WebServiceParser;
import xml.restfuldroid.parser.customparser.deserializer.BitmapDeserializer;

/**
 * Created by zenbook on 31/03/15.
 */
public class JsonElementSimpleParser implements SimpleRequestParser<JsonObject>, SimpleResponseParser<JsonObject>, WebServiceParser {

    private Gson mGson;

    @Override
    public void initWebService(WebService service) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Bitmap.class, new BitmapDeserializer(service));
        mGson = gsonBuilder.create();
    }

    @Override
    public byte[] serializer(JsonObject root) {
        return mGson.toJson(root).getBytes();
    }

    @Override
    public JsonObject deserializer(byte[] data) {
        return mGson.fromJson(new String(data), JsonElement.class).getAsJsonObject();
    }

    @Override
    public String getContentType() {
        return "application/json";
    }


}
