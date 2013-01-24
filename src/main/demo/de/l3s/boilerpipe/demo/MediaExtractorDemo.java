package de.l3s.boilerpipe.demo;

import java.net.URL;
import java.util.List;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Media;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.MediaExtractor;

/**
 * Demonstrates how to use Boilerpipe to get the media within the main content.
 * 
 * @author manuel.codiga@gmail.com
 */
public final class MediaExtractorDemo {
	
	public MediaExtractorDemo() {}
	
	public static void main(String[] args) throws Exception {
		URL url = new URL(
				"http://www.pbs.org/mediashift/2013/01/usc-annenberg-pushes-innovation-lab-experimental-school-1-year-masters018.html");
		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;

		final MediaExtractor ie = MediaExtractor.INSTANCE;
		
		List<Media> urls = ie.process(url, extractor);
		
		for(Media m : urls) {
			System.out.println("* "+m);
		}

	}
}
