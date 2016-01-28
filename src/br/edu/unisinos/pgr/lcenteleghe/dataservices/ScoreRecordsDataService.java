package br.edu.unisinos.pgr.lcenteleghe.dataservices;

import br.edu.unisinos.pgr.lcenteleghe.scores.ScoreRecords;

import com.thoughtworks.xstream.XStream;

/**
 * This class is used to load Game Stages from a XML file.
 */
public class ScoreRecordsDataService extends BaseDataService<ScoreRecords>{
	private static ScoreRecordsDataService instance;

	public ScoreRecordsDataService() {
		super("ScoreRecords");
	}
	
	public static ScoreRecordsDataService getInstance(){
		if(instance == null){
			instance = new ScoreRecordsDataService();
		}
		return instance;
	}

	@Override
	public void setAliases(XStream xStream) {
		xStream.aliasPackage("", "br.edu.unisinos.pgr.lcenteleghe.scores");
	}

}
