package br.edu.unisinos.pgr.lcenteleghe.dataservices;

import java.io.File;
import java.io.FileWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public abstract class BaseDataService<T> {
	private XStream loader;
	private String sourceName;
	
	public BaseDataService(String sourceName) {
		super();
		this.loader =  new XStream(new StaxDriver());
		this.sourceName = sourceName;
		setAliases(loader);
	}

	@SuppressWarnings("unchecked")
	public T get(){
		return (T)loader.fromXML(new File(sourceName + ".xml"));
	}
	
	public void save(T toSave) {
		try{
			loader.toXML(toSave, new FileWriter(sourceName + ".xml"));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public abstract void setAliases(XStream xtStream);
}
