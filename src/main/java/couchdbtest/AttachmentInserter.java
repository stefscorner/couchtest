package couchdbtest;

import org.apache.commons.codec.binary.Base64;
import org.lightcouch.Attachment;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Document;
import org.lightcouch.Params;
import org.lightcouch.Response;

import com.google.gson.JsonObject;

public class AttachmentInserter {

	public static void main(String[] args) {
		Document document = new Document();
		String hello = "hello";
		Attachment attachment = new Attachment(Base64.encodeBase64String(hello
				.getBytes()), "multipart/alternative");
		document.addAttachment("file", attachment);
		CouchDbClient client = new CouchDbClient("attachment", false, "http",
				"127.0.0.1", 5984, null, null);
		Response save = client.save(document);
		JsonObject find = client.find(JsonObject.class, save.getId(),
				new Params().attachments());
		// String asString = find.get("data").getAsString();
		System.out.println(find.toString());
		JsonObject attachments = find.get("_attachments").getAsJsonObject()
				.get("file").getAsJsonObject();
		System.out.println(attachments.toString());
		String asString = attachments.get("data").getAsString();
		byte[] decodeBase64 = Base64.decodeBase64(asString);
		System.out.println(new String(decodeBase64));
		
		
		JsonObject object = new JsonObject();
		// Gson gson = new Gson();
		// Map<String, Attachment> attachments = new HashMap<String,
		// Attachment>();
		// attachments.put("bla", attachment);
		// JsonElement jsonTree = gson.toJsonTree(attachments);
		// object.add("_attachments", jsonTree);
		// client.save(object);
	}

}
