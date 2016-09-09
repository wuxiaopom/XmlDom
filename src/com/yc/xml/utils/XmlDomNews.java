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
 * @Description:   Dom解析Xml 新闻案例  --数据转为Json
 * @author          POM
 * @version         V1.0  
 * @Date           2016年9月9日 下午3:33:49 
 */
public class XmlDomNews {
	public  List<News> getList() {
		List<News> newsList = new ArrayList<News>();
		//io流操作，保存到本地
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


			//创建一个文档构建工厂对象，解析器的对象（DOM）
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			//将给定 file 的内容解析为一个 XML 文档，并且返回一个新的 DOM Document 对象。
			Document doc=builder.parse(is);

			NodeList nl = doc.getElementsByTagName("NewsObj");                   //获取到NewsObj节点的对象
			int len = nl.getLength(); 
			for (int i = 0; i < len; i++) {
				News news = new News();
				int len2 = nl.item(i).getChildNodes().getLength();
				for(int j=0; j<len2;j++){
					Node node = nl.item(i).getChildNodes().item(j);               //  取到newsObj下的每一个节点
					if(nl.item(i).getChildNodes().item(j).getNodeType() == 1){    //取到节点的类型  1-ELEMENT元素节点 2-ATTRIBUTE属性节点 3-TEXT文本节点
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
	 *将解析的xml转成Json格式    字符串拼接形式
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
		jsonData = jsonData.substring(0, jsonData.length() - 1); // 去掉最后的","
		jsonData += "],\"error_code\":" + "0" + ",\"reason\":" + "\"" + "success" + "\"}";
		return jsonData;
	}

	/**
	 *list数据转Json格式  借助外部包 
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




