package oss.airtel.component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


@Component
public class NAconnect {

	//private static SSLSocket socket = null;
	//private static HttpsURLConnection httpsConn=null;
	//public final String Files_Path= new ClassPathResource("authorizationFiles/").getFile().getAbsolutePath();
//	public final String Files_Path= new ClassPathResource("authorizationFiles/").getURI().getPath();
	
//	public NAconnect() throws IOException{
//		
//	}
	public String runXMLHttps(String xmldata) 
	{
		try
		{
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//			ks.load(new FileInputStream(new File(Files_Path+File.separator+"client.jks")), "N2510las".toCharArray());
			ks.load(new ClassPathResource("authorizationFiles/client.jks").getInputStream(), "N2510las".toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, "N2510las".toCharArray());
			KeyManager[] keyManager = kmf.getKeyManagers();

			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//			trustStore.load(new FileInputStream(Files_Path+File.separator+"jssecacerts_copper"), "changeit".toCharArray());
			trustStore.load(new ClassPathResource("authorizationFiles/jssecacerts_copper").getInputStream(), "changeit".toCharArray());
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
			
			SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);

			SSLContext ctx = SSLContext.getInstance("TLSv1");
			ctx.init(keyManager, new TrustManager[] { tm }, new SecureRandom());
			
			
			HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
			// Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) { return true; }
	        };
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

	        
	        String link = "https://10.232.161.137:10500/";
			URL url = new URL(link);
			HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
			httpsConn.setRequestMethod("POST");
			httpsConn.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			httpsConn.setDoOutput(true);
			
			DataOutputStream dos = new DataOutputStream(httpsConn.getOutputStream());
			dos.writeBytes(xmldata);
			dos.flush();
			dos.close();
			
//			String responseStatus = httpsConn.getResponseMessage();
//			System.out.println("responseStatus: "+responseStatus);
			
			StringBuffer sb = new StringBuffer();
			try(BufferedReader br = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()))) {
				String line;
				while((line=br.readLine())!=null) 
				{
					sb.append(line);
				}
				br.close();
			} catch (Exception e) {
				System.out.println("BufferedReader InputStream error: "+e.getMessage());
				httpsConn.disconnect();
				return "ErrorStatus: "+e.getMessage();
			}
			
			httpsConn.disconnect();
			Thread.sleep(500L);
			return sb.toString().trim();
		} 
		catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException | InterruptedException e) 
		{
			//System.out.println(e.getMessage());
			e.printStackTrace();
			return "ErrorStatus: "+e.getMessage();
		}
//		finally {
//			httpsConn.disconnect();
//		}
	}
	
	public String runXML(String xmldata) 
	{
		try 
		{
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//			ks.load(new FileInputStream(new File(Files_Path+File.separator+"client.jks")), "N2510las".toCharArray());
			ks.load(new ClassPathResource("authorizationFiles/client.jks").getInputStream(), "N2510las".toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, "N2510las".toCharArray());
			KeyManager[] keyManager = kmf.getKeyManagers();

			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//			trustStore.load(new FileInputStream(Files_Path+File.separator+"jssecacerts"), "changeit".toCharArray());
			trustStore.load(new ClassPathResource("authorizationFiles/jssecacerts_copper").getInputStream(), "changeit".toCharArray());
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
			
			SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);

			SSLContext ctx = SSLContext.getInstance("TLSv1");
			ctx.init(keyManager, new TrustManager[] { tm }, new SecureRandom());
			

			SSLSocketFactory factory = ctx.getSocketFactory();
			SSLSocket socket = (SSLSocket) factory.createSocket("10.232.161.137", 10500);
			socket.setKeepAlive(true);
			try {
				socket.startHandshake();
			} catch (SSLException e) {
				e.printStackTrace();
				socket.close();
				return null;
			}
			
			String path = "/";
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			wr.write("POST " + path + " HTTP/1.1\r\n");
			wr.write("Content-Type: text/xml; charset=UTF-8\r\n");
			wr.write("SOAPAction: \"\"\r\n");
			wr.write("Content-Length: " + xmldata.length() + "\r\n");
			wr.write("Host: 10.232.161.137:10500\r\n");
			wr.write("Connection: Keep-Alive\r\n");
			wr.write("\r\n");

			// Send data
			wr.write(xmldata);
			wr.flush();
//			InputStream is = socket.getInputStream();
//			byte[] buffer = new byte[16384];
//			int read=0;
//			
//			String output;
//			do{
//				output = new String(buffer, 0, read);
//				if (output.contains("</soap:Envelope>"))
//				break;
//			}while ((read = is.read(buffer)) != -1); 
//			System.out.println("Output of builtin:- "+output);
			
			StringBuffer sb = new StringBuffer();
			try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				String line;
				while((line=br.readLine())!=null) 
				{
					sb.append(line);
				}
				br.close();
			} catch (Exception e) {
				System.out.println("BufferedReader InputStream error: "+e.getMessage());
				socket.close();
				return "ErrorStatus: "+e.getMessage();
			}
			
			if(socket!=null) {
			socket.close();
			}
			Thread.sleep(500L);
			String output=sb.toString().trim();
			return output.substring(output.indexOf("<"));
		} 
		catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException | InterruptedException e) 
		{
			//System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
//		finally 
//		{
//			if(socket != null) 
//			{
//				try {
//					socket.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
	
	private static class SavingTrustManager implements X509TrustManager {
		private final X509TrustManager tm;
		@SuppressWarnings("unused")
		private X509Certificate[] chainA;

		SavingTrustManager(final X509TrustManager tm) {
			this.tm = tm;
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
			// throw new UnsupportedOperationException();
		}

		@Override
		public void checkClientTrusted(final X509Certificate[] chain,
				final String authType) throws CertificateException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void checkServerTrusted(final X509Certificate[] chain,
				final String authType) throws CertificateException {
			this.chainA = chain;
			this.tm.checkServerTrusted(chain, authType);
			
			
		}
	}
}
