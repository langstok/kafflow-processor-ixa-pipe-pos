package com.langstok.nlp.ixapos.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import eus.ixa.ixa.pipe.pos.CLI;

@ConfigurationProperties
public class IxaPosProperties {
	
	/**
	 * provide language keys as list
	 */
	private List<String> languages = Arrays.asList(new String[]{"en","nl"});
	
	
	/**
	 * It is required to provide a models to perform POS tagging, order by languages
	 */
	private List<String> models = Arrays.asList(new String[]{
			"/maven/morph-models-1.5.0/en/en-pos-maxent-100-c5-baseline-autodict01-conll09.bin",
			"/maven/morph-models-1.5.0/nl/nl-pos-maxent-100-c5-autodict01-alpino.bin"
	});

	/**
	 * It is required to provide a lemmatizer model, order by languages
	 */
	private List<String> lemmatizermodels = Arrays.asList(new String[]{
			"/maven/morph-models-1.5.0/en/en-lemma-perceptron-conll09.bin",
			"/maven/morph-models-1.5.0/nl/nl-lemma-perceptron-alpino.bin"
	});
	
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
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	public List<String> getModels() {
		return models;
	}
	public void setModels(List<String> models) {
		this.models = models;
	}
	public List<String> getLemmatizermodels() {
		return lemmatizermodels;
	}
	public void setLemmatizermodels(List<String> lemmatizermodels) {
		this.lemmatizermodels = lemmatizermodels;
	}
	
}
