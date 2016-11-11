package com.langstok.nlp.ixapos.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import eus.ixa.ixa.pipe.pos.CLI;

@ConfigurationProperties
public class IxaPosProperties {
		
	/**
	 * It is required to provide a model to perform POS tagging
	 */
	private Map<String, String> models;
	
	/**
	 * It is required to provide a lemmatizer model
	 */
	private Map<String, String> lemmatizermodels;
	
	/**
	 * Choose beam size for decoding, it defaults to 3
	 */
	private String beamSize = CLI.DEFAULT_BEAM_SIZE;
	
	/**
	 * Use to detect and process multiwords
	 */
	private Boolean multiwords = false;
	
	/**
	 * Post process POS tagger output with a monosemic dictionary
	 */
	private Boolean dictag = false;
	
	private String language = "en";
	
	public String getBeamSize() {
		return beamSize;
	}
	public void setBeamSize(String beamSize) {
		this.beamSize = beamSize;
	}
	public Boolean getMultiwords() {
		return multiwords;
	}
	public void setMultiwords(Boolean multiwords) {
		this.multiwords = multiwords;
	}
	public Boolean getDictag() {
		return dictag;
	}
	public void setDictag(Boolean dictag) {
		this.dictag = dictag;
	}
	public Map<String, String> getModels() {
		return models;
	}
	public void setModels(Map<String, String> models) {
		this.models = models;
	}
	public Map<String, String> getLemmatizermodels() {
		return lemmatizermodels;
	}
	public void setLemmatizermodels(Map<String, String> lemmatizermodels) {
		this.lemmatizermodels = lemmatizermodels;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
