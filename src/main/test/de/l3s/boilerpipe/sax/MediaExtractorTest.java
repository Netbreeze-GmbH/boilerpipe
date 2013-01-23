package de.l3s.boilerpipe.sax;

import static org.junit.Assert.fail;

import java.net.URI;
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
			url = new URL(
					"http://www.pbs.org/mediashift/2013/01/usc-annenberg-pushes-innovation-lab-experimental-school-1-year-masters018.html");
			final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
	
			final MediaExtractor ie = MediaExtractor.INSTANCE;
			
			urls = ie.process(url, extractor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (urls.size()<0) {
			fail("no media found");
		}
		
		for(Media m : urls) {
			if (m instanceof Video) {
				try {
					URI video = new URI(((Video) m).getOriginUrl());
				} catch (Exception e) {
					fail("no valid url");
				}
			} else if (m instanceof Image) {
				try {
					URI image = new URI(((Image) m).getSrc());
				} catch (Exception e) {
					fail("no valid url");
				}
			}
		}
		
	}
	

}
