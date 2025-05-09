package jsonparsenlnadp;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonParseNLNADP {

    public static void main(String[] args) {
        try (FileReader reader = new FileReader("orarendNLNADP.json")) { 
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject root = (JSONObject) jsonObject.get("nlnadp_orarend"); 
            JSONArray lessons = (JSONArray) root.get("ora"); 
            System.out.println("Órarend 2025 tavasz \n");

            for (int i = 0; i < lessons.size(); i++) { 
                JSONObject lesson = (JSONObject) lessons.get(i);
                JSONObject time = (JSONObject) lesson.get("idopont");

               
                System.out.println("Tárgy: " + lesson.get("tantargy"));
                System.out.println("Nap: " + time.get("nap"));
                System.out.println("Kezdés: " + time.get("kezdes"));
                System.out.println("Helyszín: " + lesson.get("helyszin"));
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
