package de.l3s.boilerpipe.sax;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.document.Media;
import de.l3s.boilerpipe.document.Video;
import de.l3s.boilerpipe.extractors.CommonExtractors;

public class MediaExtractorTest {

	@Test
	public void shouldGetDocumentMedia() {
		URL url;
		List<Media> urls = null;
		try {
			final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
	
			final MediaExtractor ie = MediaExtractor.INSTANCE;
			
			urls = ie.process(this.getFileAsString("/hardwareluxx.html"), extractor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!(urls instanceof List)) {
			fail("no media found");
		} else {
	
			for(Media m : urls) {
				if (m instanceof Video) {
					try {
						URI video = new URI(((Video) m).getOriginUrl());
						URI videoEmbed = new URI(((Video) m).getEmbedUrl());
						
						assertTrue(video.isAbsolute());
						assertTrue(videoEmbed.isAbsolute());
						
					} catch (Exception e) {
						fail("no valid url");
					}
				} else if (m instanceof Image) {
					try {
						URI image = new URI(((Image) m).getSrc());
						assertTrue(image.isAbsolute());
					} catch (Exception e) {
						fail("no valid url");
					}
				}
			}
		}	
	}
	
	
	@Test
	public void shouldGetOnlyImageUrls() {
		URL url;
		List<Media> urls = null;
		try {
			final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
			final MediaExtractor ie = MediaExtractor.INSTANCE;
			urls = ie.process(this.getFileAsString("/blogspot.html"), extractor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (urls.size()==0) {
			fail("no media found");
		} else {
	
			for(Media m : urls) {
				if (m instanceof Image) {
					try {
						URI testuri = new URI(((Image) m).getSrc());
					} catch (URISyntaxException e) {
						fail("not correct uri format in image src");
					}
				}
			}
		}
	}
	
	
	public String getFileAsString(String file) {
		InputStream stream = getClass().getResourceAsStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
    	StringBuilder sb = new StringBuilder();
    	String line = "";
    	
    	try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	
		return sb.toString();
    }

}
