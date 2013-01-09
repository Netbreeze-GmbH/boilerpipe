/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 *       
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.l3s.boilerpipe.sax;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;

/**
 * an Extractor for extracting an article from an document with its basic HTML structure.
 * 
 * @author manuel.codiga@gmail.com
 */
public class HtmlArticleExtractor {
	public static final HtmlArticleExtractor INSTANCE = new HtmlArticleExtractor();
	
	private static final Set<String> NOT_ALLOWED_HTML_TAGS = new HashSet<String>(Arrays.asList(
	        HTMLElementName.HEAD,
	        HTMLElementName.HTML,
	        HTMLElementName.SCRIPT,
	        HTMLElementName.STYLE,
	        HTMLElementName.FORM,
	        HTMLElementName.BODY,
	        HTMLElementName.DIV,
	        HTMLElementName.SPAN)
	);
	
	private HtmlArticleExtractor() {}
	
	/**
	 * Returns the singleton instance
	 * 
	 * @return
	 */
	public static HtmlArticleExtractor getInstance() {
		return INSTANCE;
	}

	public String process(final BoilerpipeExtractor extractor, final URL url)
			throws IOException, BoilerpipeProcessingException, SAXException, URISyntaxException {
		final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);
		return process(htmlDoc, url.toURI(), extractor);
	}
	
	
	/**
	 * returns the article from an document with its basic html structure. 
	 * 
	 * @param HTMLDocument
	 * @param URI the uri from the document for resolving the relative anchors in the document to absolute anchors
	 * @return String
	 */
	public String process(HTMLDocument htmlDoc, URI docUri, final BoilerpipeExtractor extractor) {

		final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance();
		hh.setOutputHighlightOnly(true);

		TextDocument doc;

		String text = "";
		try {
			doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
			extractor.process(doc);
			final InputSource is = htmlDoc.toInputSource();
			text = hh.process(doc, is);
		} catch (Exception ex) {
			return null;
		}


		return removeNotAllowedTags(text, docUri);
	}

	private String removeNotAllowedTags(String htmlFragment, URI docUri) {
	    Source source = new Source(htmlFragment);
	    OutputDocument outputDocument = new OutputDocument(source);
	    List<Element> elements = source.getAllElements();


	    for (Element element : elements) {
	    	Attributes attrs = element.getAttributes();
	    	Map<String, String> attrsUpdate = outputDocument.replace(attrs, true);
	    	if (!element.getName().contains("a")) {
				attrsUpdate.clear();
			} else {
	    		if (attrsUpdate.get("href")!=null) {
		    		String link = attrsUpdate.get("href");
		    		if (!link.contains("http")) {
			    		URI documentUri = docUri;

			    		URI anchorUri;
						try {
							anchorUri = new URI(link);
							URI result = documentUri.resolve(anchorUri);

							attrsUpdate.put("href",	result.toString());
						} catch (URISyntaxException e) {
							outputDocument.remove(element);
						}
		    		}
	    		}
	    	}

	    	if (NOT_ALLOWED_HTML_TAGS.contains(element.getName())) {
	    		Segment content = element.getContent();
	    		if (element.getName() == "script"
	    				|| element.getName() == "style"
	    				|| element.getName() == "form") {
	    			outputDocument.remove(content);
	    		}
	            outputDocument.remove(element.getStartTag());

	            if (!element.getStartTag().isSyntacticalEmptyElementTag()) {
	                outputDocument.remove(element.getEndTag());
	            }
	        }
	    }

	    String out = outputDocument.toString();
	    out = out.replaceAll("\\n", "");
	    out = out.replaceAll("\\t", "");

	    return out;
	}

}
