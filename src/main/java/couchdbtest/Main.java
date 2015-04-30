package couchdbtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.lightcouch.Attachment;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Document;
import org.lightcouch.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Main {

	public static void main(String[] args) {
		Document document = new Document();
		String hello = "hello";
		Attachment attachment = new Attachment(Base64.encodeBase64String(hello
				.getBytes()), "multipart/alternative");
		document.addAttachment("bla", attachment);
		CouchDbClient client = new CouchDbClient("workbench", false, "http",
				"127.0.0.1", 5984, null, null);
		List<JsonObject> tupleList = client.view("mostused/byType").key(1)
				.query(JsonObject.class);
		for (JsonObject object : tupleList) {
			System.out.println(object);
			if (!object.get("value").isJsonNull()) {
				client.remove(object.get("id").getAsString(),
						object.get("value").getAsString());
			}
		}

		// // Response save = client.save(document);
		// JsonObject find = client.find(JsonObject.class, save.getId());
		// System.out.println(find.toString());
		//
		// JsonObject object = new JsonObject();
		// Gson gson = new Gson();
		// Map<String, Attachment> attachments = new HashMap<String,
		// Attachment>();
		// attachments.put("bla", attachment);
		// JsonElement jsonTree = gson.toJsonTree(attachments);
		// object.add("_attachments", jsonTree);
		// client.save(object);
	}
}
