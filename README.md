#boilerpipe 1.2.1


##Changes in this Version of Boilerpipe#
This is an extended version of [Boilerpipe 1.2.0 on Google Code](http://code.google.com/p/boilerpipe/).

###New features:

* Media extraction (Youtube videos, Vimeo videos and Images) within an article
* Extract an article with its HTML structure
 
### Example
* [MediaExtractorDemo](https://github.com/Netbreeze-GmbH/boilerpipe/blob/master/src/main/demo/de/l3s/boilerpipe/demo/MediaExtractorDemo.java)

### License
[Apache License 2.0] (http://www.apache.org/licenses/LICENSE-2.0)

### Changes
* mavenized

------------------
     <dependency>
      <groupId>de.l3s.boilerpipe</groupId>
      <artifactId>boilerpipe-core</artifactId>
      <version>1.2.1</version>
    </dependency>
------------------
#### Classes added:

* de.l3s.boilerpipe.document.Media
* de.l3s.boilerpipe.document.Video
* de.l3s.boilerpipe.document.VimeoVideo
* de.l3s.boilerpipe.document.YoutubeVideo
* de.l3s.boilerpipe.extractors.HtmlArticleExtractor - this Extractor extracts the article content with his basic HTML structure from the document
* de.l3s.boilerpipe.sax.MediaExtractor - this Extractor returns a list of Medias that are contained in the documents article

files/folders removed (because of the change to maven):

* /build.xml
* /eclipse-build
* /lib
