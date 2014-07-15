import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class TinyHttpFileDownloader_Main {
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException, IOException, InterruptedException {
		if(args.length == 0){
			System.out.println(" ==== HOW TO USE ====");
			System.out.println("-p : Proxy Address --pport : Proxy Port [default : 8080]");
			System.out.println("-u : DOWNLOAD URL : http://xxx.xxx.xxx.xxx[:port][/...]");
			System.out.println("-c : Cookie Value Name --cvalue : Cookie Value Data");
			System.out.println("-o : Output File Path ");
		}
		
		String proxyAddr = "";
		String proxyPort = "8080";
		String downloadUrl = "";
		Map<String, String> cookies = new HashMap<String, String>();
		String outputPath = "";

		for(int i = 0 ; i < args.length ; i++){
			if(args[i].equals("-p")){
				proxyAddr = args[++i];
				if(i+2 < args.length && args[i+1].equals("--pport")){
					proxyPort = args[i+2];
					i+=2;
					continue;
				}
			}
			if(args[i].equals("-u")){
				downloadUrl = args[++i];
				continue;
			}
			if(args[i].equals("-c")){
				String name = args[++i];
				if(i+1 < args.length && args[i+1].equals("--cvalue")){
					String data = args[i+2];
					i+=2;
					cookies.put(name, data);
				}else{
					/* ERROR */
					System.out.println("Input cookie value in --cvalue option");
				}
				continue;
			}
			if(args[i].equals("-o")){
				outputPath = args[++i];
				continue;
			}
		}

		/* Setting Proxy */
		if(!proxyAddr.equals("")){
			System.setProperty("http.proxyHost", proxyAddr);
			System.setProperty("http.proxyPort", proxyPort);
		}
		
		Response resultImageResponse = Jsoup.connect(downloadUrl)
				.cookies(cookies)
				.ignoreContentType(true)
				.execute();

		// output here
		FileOutputStream out = (new FileOutputStream(new java.io.File(outputPath)));
		out.write(resultImageResponse.bodyAsBytes());           // resultImageResponse.body() is where the image's contents are.
		out.close();

		//IOUtils.copyLarge(new URL("http://ssultoday.com/layouts/xecenter/img/default_logo.gif").openConnection().getInputStream(), new BufferedOutputStream(new FileOutputStream("C:/data/ddd.gif")));
	}
}
