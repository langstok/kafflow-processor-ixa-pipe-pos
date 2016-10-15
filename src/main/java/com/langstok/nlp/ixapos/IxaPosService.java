package com.langstok.nlp.ixapos;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.google.common.io.Files;
import com.langstok.nlp.ixapos.configuration.IxaPosProperties;

import eus.ixa.ixa.pipe.pos.Annotate;
import eus.ixa.ixa.pipe.pos.CLI;
import ixa.kaflib.KAFDocument;


@Service
@EnableConfigurationProperties(value={IxaPosProperties.class})
public class IxaPosService {

	private final static Logger LOGGER = Logger.getLogger(IxaPosService.class);

	@Autowired
	IxaPosProperties posProperties;
	
	private Annotate annotator;
	
	private String model;

	private final String version = CLI.class.getPackage().getImplementationVersion();

	private final String commit = CLI.class.getPackage().getSpecificationVersion();

	public KAFDocument transform(KAFDocument document) throws Exception{
		LOGGER.info("IXAPOS start for publicId:"+ document.getPublic().publicId + " uri:" + document.getPublic().uri);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			document = annotate(document);
		} catch (IOException e) {
			LOGGER.error("IOException for KAF publicId: " + document.getPublic().publicId, e);
		} catch (JDOMException e) {
			LOGGER.error("JDOMException for KAF publicId: " + document.getPublic().publicId, e);
		}
		stopWatch.stop();
		LOGGER.info("IXAPOS done, "
				+ "duration: " + stopWatch.getTotalTimeMillis() + " ms, "
				+ "publicId:" + document.getPublic().publicId);
		return document;
	}

	public final KAFDocument annotate(KAFDocument kaf) throws Exception	{
		final KAFDocument.LinguisticProcessor newLp = kaf.addLinguisticProcessor(
				"terms", "ixa-pipe-pos-" + Files.getNameWithoutExtension(model),
				this.version + "-" + this.commit);
		newLp.setBeginTimestamp();
		annotator.annotatePOSToKAF(kaf);
		newLp.setEndTimestamp();
		return kaf;
	}

	private Properties setAnnotateProperties(String model, String lemmatizerModel, String language,
			String multiwords, String dictag){
		final Properties annotateProperties = new Properties();
		annotateProperties.setProperty("model", model);
		annotateProperties.setProperty("lemmatizerModel", lemmatizerModel);
		annotateProperties.setProperty("language", language);
		annotateProperties.setProperty("multiwords", multiwords);
		annotateProperties.setProperty("dictag", dictag);
		return annotateProperties;
	}
	
	@PostConstruct
	private void init() throws Exception{
		String multiwords = Boolean.toString(posProperties.getMultiwords());
		String dictag = Boolean.toString(posProperties.getDictag());

		String lang = posProperties.getLanguage();
		if(!posProperties.getModels().containsKey(lang)){
			throw new Exception("language model not configured for language: "+lang);
		}
		this.model = posProperties.getModels().get(lang);
		
		if(!posProperties.getLemmatizermodels().containsKey(lang)){
			throw new Exception("lemmatizermodel model not configured for language: "+lang);
		}
		final String lemmatizerModel = posProperties.getLemmatizermodels().get(lang);

		Properties properties = setAnnotateProperties(model, lemmatizerModel, lang, multiwords, dictag);
		this.annotator = new Annotate(properties);
	}
	

}
