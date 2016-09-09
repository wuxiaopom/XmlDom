package com.yc.xml.utils;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.yc.xml.domain.News;

/**
 * @ClassName:     XmlDomNews.java
 * @Description:   Dom����Xml ���Ű���  --����תΪJson
 * @author          POM
 * @version         V1.0  
 * @Date           2016��9��9�� ����3:33:49 
 */
public class XmlDomNews {
	public  List<News> getList() {
		List<News> newsList = new ArrayList<News>();
		//io�����������浽����
		try {                                     
			/*File file=new File("news.xml");
				if(!file.exists()){
					URL url=new URL("http://api.avatardata.cn/GuoNeiNews/Query?key=b5884e12578141e888cf17fe62903bd2&page=1&rows=10&dtype=xml");
					URLConnection con=url.openConnection();
					con.connect();
					InputStream is=con.getInputStream();
					OutputStream os=new FileOutputStream(file,true);
					byte[] bt=new byte[1024];
					int len=-1;

					while( (len=is.read(bt))!=-1 ){
						os.write(bt, 0, len);
					}
					os.flush();
					os.close();
					is.close();
				}*/
			URL url=new URL("http://api.avatardata.cn/GuoNeiNews/Query?"
					+ "key=b5884e12578141e888cf17fe62903bd2&page=1&rows=10&dtype=xml");
			URLConnection con=url.openConnection();
			con.connect();
			InputStream is=con.getInputStream();


			//����һ���ĵ������������󣬽������Ķ���DOM��
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			//������ file �����ݽ���Ϊһ�� XML �ĵ������ҷ���һ���µ� DOM Document ����
			Document doc=builder.parse(is);

			NodeList nl = doc.getElementsByTagName("NewsObj");                   //��ȡ��NewsObj�ڵ�Ķ���
			int len = nl.getLength(); 
			for (int i = 0; i < len; i++) {
				News news = new News();
				int len2 = nl.item(i).getChildNodes().getLength();
				for(int j=0; j<len2;j++){
					Node node = nl.item(i).getChildNodes().item(j);               //  ȡ��newsObj�µ�ÿһ���ڵ�
					if(nl.item(i).getChildNodes().item(j).getNodeType() == 1){    //ȡ���ڵ������  1-ELEMENTԪ�ؽڵ� 2-ATTRIBUTE���Խڵ� 3-TEXT�ı��ڵ�
						if(nl.item(i).getChildNodes().item(j).getFirstChild() != null){
							String content = node.getFirstChild().getNodeValue();
							//System.out.println(nl.item(i).getChildNodes().item(j).getFirstChild().getNodeValue());
							if("ctime".equals(node.getNodeName())){
								news.setcTime(content);
							}else if("title".equals(node.getNodeName())){
								news.setTitle(content);
							}else if("description".equals(node.getNodeName())){
								news.setDescription(content);
							}else if("picUrl".equals(node.getNodeName())){
								news.setPicUrl(content);
							}else if("url".equals(node.getNodeName())){
								news.setUrl(content);
							}
						}
					}
				}
				newsList.add(news);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return newsList;
	}

	/**
	 *��������xmlת��Json��ʽ    �ַ���ƴ����ʽ
	 */
	
	
	public  String getJsonData(List<News> newsList) {
		String jsonData = "{\"result\":[";
		for (News news : newsList) {
			jsonData += "{\"ctime\":" + "\"" + news.getcTime() + "\",";
			jsonData += "\"title\":" + "\"" + news.getTitle() + "\",";
			jsonData += "\"description\":" + "\"" + news.getDescription() + "\",";
			jsonData += "\"picUrl\":" + "\"" + news.getPicUrl() + "\",";
			jsonData += "\"url\":" + "\"" + news.getUrl() + "\"},";
		}
		jsonData = jsonData.substring(0, jsonData.length() - 1); // ȥ������","
		jsonData += "],\"error_code\":" + "0" + ",\"reason\":" + "\"" + "success" + "\"}";
		return jsonData;
	}

	/**
	 *list����תJson��ʽ  �����ⲿ�� 
	 */
	public String getJsonDataByGson(List<News> newsList){
		Gson gson = new Gson();
		return gson.toJson(newsList);
	}
	
	public static void main(String[] args) {
		XmlDomNews xdm = new XmlDomNews();
		List<News> newList = xdm.getList();
		System.out.println( xdm.getJsonDataByGson(newList) );
	}
}




