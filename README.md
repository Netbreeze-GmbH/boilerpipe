#boilerpipe 1.2.1

##Changes in this Version of Boilerpipe#
This is an extended version of [Boilerpipe 1.2.0](http://code.google.com/p/boilerpipe/).

###New features:

* Media extraction (Youtube videos, Vimeo videos and Images) within an article
* Extract an article with its HTML structure

### Changes
* mavenized

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
