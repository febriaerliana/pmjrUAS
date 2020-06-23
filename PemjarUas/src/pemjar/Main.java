/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pemjar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class Main {
    

    public static JSONArray getRequest(String keyword) {
        JSONArray ja = null;
        try {

            String key[] = keyword.split(" ");
            String ky = String.join("", key);
            System.out.println(ky);

            String host = "www.omdbapi.com";

            int port = 80;


            Socket client = new Socket(host, port);

            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();

            String request = "GET /?s="+ky+"&apikey=77d6ecb8 HTTP/1.1\r\n"
                    + "host: " + host + "\r\n"
                    + "connection: close\r\n"
                    + "\r\n";

            outputStream.write(request.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.println(response.indexOf("{"));
            System.out.println(response.length());
            String resp = response.substring(response.indexOf("{"), response.length());

            JSONObject jo = new JSONObject(resp);
            ja = jo.getJSONArray("Search");

            in.close();

        } catch (Exception x) {
            x.printStackTrace();
        }
        return ja;
    }

    public static void main(String[] args) throws JSONException {

        while (true) {
            System.out.println("Mau cari film apa ?");
            Scanner s = new Scanner(System.in);

            JSONArray result = getRequest(s.nextLine());

            for (int i = 0; i < result.length(); i++) {
                System.out.println("=============== FILM KE "+(i+1)+" ===============");
                System.out.println("JUDUL : "+result.getJSONObject(i).getString("Title"));
                System.out.println("TAHUN : "+result.getJSONObject(i).getString("Year"));
                System.out.println("TYPE : "+result.getJSONObject(i).getString("Type"));
                System.out.println("=============================================");
            }

            System.out.println("Cari film lagi ? Y/N");
            String pilihanUser = s.nextLine();
            if (pilihanUser.equals("Y")) {
                continue;
            } else if (pilihanUser.equals("N")) {
                break;
            } else {
                System.out.println("Input Salah");
                break;
            }

        }
    }

}
