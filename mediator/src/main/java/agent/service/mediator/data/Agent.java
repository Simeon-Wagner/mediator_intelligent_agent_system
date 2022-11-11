package agent.service.mediator.data;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Agent {
    String uri;


    public  boolean propose(Integer [] proposal, Integer [] contract){
        try {

            Gson gson = new Gson();
            ProposeObject prop = new ProposeObject(proposal,contract);
            String objectBody = gson.toJson(prop);

            URL url = new URL("http://"+uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            OutputStreamWriter outw = new OutputStreamWriter(out);
            outw.write(objectBody);
            outw.flush();
            outw.close();
            out.close();
            con.connect();
            System.out.println(con.getResponseCode());
            return createBooleanFromStream(con);
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }
    private  boolean createBooleanFromStream(HttpURLConnection connection) throws IOException {
        InputStream inputStream;

        inputStream = connection.getInputStream();

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return Boolean.valueOf(output.toString());
}


}
