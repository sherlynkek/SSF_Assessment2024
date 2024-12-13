package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	NoticeRepository noticeRepo;
	
	RestTemplate template = new RestTemplate();
	
	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type

	public List<Notice> postToNoticeServer() {
		RequestEntity<Void> req = RequestEntity
								.get("https://publishing-production-d35a.up.railway.app/")
								.accept(MediaType.APPLICATION_JSON).build();

		ResponseEntity<String> res = template.exchange(req, String.class);
		
		String notice = res.getBody();
		
		JsonReader reader = Json.createReader(new StringReader(notice));
		JsonObject jObject = reader.readObject();
		JsonArray data = jObject.getJsonArray("Data");
		List<Notice> noticeList = new ArrayList<>();

		for(int i = 0; i < data.size(); i++) {
			JsonObject notices = data.getJsonObject(i);
			String title = notices.getString("title");
			String poster = notices.getString("poster");
			Long postDateLong = notices.getJsonNumber("postDate").longValueExact();
			Date postDate = new Date(postDateLong * 1000);
			JsonArray categoryData = notices.getJsonArray("categories");
			String text = notices.getString("text");

			StringBuilder sb = new StringBuilder();

			for(int j = 0; j < categoryData.size(); j++) {
				JsonObject cat = categoryData.getJsonObject(j);
				String category = cat.getString("categories_data");
				sb.append(category + ",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			String categories = sb.toString();

			Notice nb = new Notice(title + poster + postDate + categories + text);
			noticeList.add(nb);
		}

		return noticeList;
	}

	public void addToRedis(Notice nb) {
		JsonObject jObject = Json.createObjectBuilder()
		.add("title", nb.getTitle())
		.add("poster", nb.getPoster())
		.add("postDate", nb.getPostDate().getTime()/1000)
		.add("categories", nb.getCategories())
		.add("text", nb.getText())
		.build();
	}

}
