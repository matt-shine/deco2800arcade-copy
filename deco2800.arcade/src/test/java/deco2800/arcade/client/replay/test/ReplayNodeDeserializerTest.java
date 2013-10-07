package deco2800.arcade.client.replay.test;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import deco2800.arcade.client.replay.ReplayItem;
import deco2800.arcade.client.replay.ReplayNode;
import deco2800.arcade.client.replay.ReplayNodeDeserializer;

public class ReplayNodeDeserializerTest {

    @Test
    public void testDeserialization()
    {
        Gson serializer = new Gson();
        HashMap<String, ReplayItem> vars = new HashMap<String, ReplayItem>();
        vars.put("param1", new ReplayItem(123));
        vars.put("param2", new ReplayItem(5.234f));
        vars.put("param3", new ReplayItem("Hello World!"));
        ReplayNode rn = new ReplayNode("event_name", vars);
        String serialized = serializer.toJson(rn);

        Assert.assertEquals("{\"nodeTime\":0,\"type\":\"event_name\",\"items\":{\"param1\":{\"type\":1,\"data\":123},\"param2\":{\"type\":2,\"data\":5.234},\"param3\":{\"type\":3,\"data\":\"Hello World!\"}}}", serialized);
    
        Gson deserializer;
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(ReplayNode.class, new ReplayNodeDeserializer());
        deserializer = gsonBuilder.create();
        
        ReplayNode recreated = deserializer.fromJson(serialized, ReplayNode.class);
        
        Assert.assertEquals(rn.getItemForString("param1").intVal(), recreated.getItemForString("param1").intVal());
        Assert.assertEquals(rn.getItemForString("param2").floatVal(), recreated.getItemForString("param2").floatVal());
        Assert.assertEquals(rn.getItemForString("param3").stringVal(), recreated.getItemForString("param3").stringVal());
        
        
    }

}
