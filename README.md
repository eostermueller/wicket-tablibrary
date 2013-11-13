wicket-tablibrary
=================

A work-in-progress to enhance the Wicket "library" example.  The new example will show the BookDetails in a page with multiple tabs.

The original "library" example is here:
http://www.wicket-library.com/wicket-examples-6.0.x/library/wicket/bookmarkable/org.apache.wicket.examples.library.SignIn

Once you've forked this repo and done "mvn package", start the example using

```
localhost:8080/wicket-examples/tablibrary/
```

I'm a beginner with wicket and could use some help getting by this error.
The app starts fine, just like the wicket "libray".
But when you select one of the books in the initial list, you get the error below.

The "PAGE_ROOT" stuff is a dump from the WicketHierarchyPrinter.
Here is one point I don't understand:  
The error starts with "can't find title" and then finishes with "current =  '<span wicket:id="title">".
Perhaps that means I inadvertantly have two things named title next to each other in my hierarchy?

Thanks in advance for any help on this.
--Erik


```

PAGE_ROOT(org.apache.wicket.examples.tablibrary.BookDetails) ''
   mainNavigation(org.apache.wicket.examples.WicketExampleHeader) ''
      debug(org.apache.wicket.devutils.debugbar.DebugBar) ''
         logo(org.apache.wicket.markup.html.image.Image) ''
         content(org.apache.wicket.markup.html.WebMarkupContainer) ''
            contributors(org.apache.wicket.markup.html.list.ListView) '[]'
            removeImg(org.apache.wicket.markup.html.image.Image) ''
      exampleTitle(org.apache.wicket.markup.html.basic.Label) 'tablibrary'
      sources(org.apache.wicket.markup.html.link.BookmarkablePageLink) ''
   tabs(org.wicketskunkworks.pagetabs.brill.PageTabbedPanel) '0'
      tabs-container(org.apache.wicket.markup.html.WebMarkupContainer) ''
         tabs(org.apache.wicket.markup.html.list.Loop) '3'

id for TabPanel1 [3]
ERROR - MarkupContainer            - Unable to find component with id 'title' in [Page class = org.apache.wicket.examples.tablibrary.TabPanel1, id = 3, render count = 1]
Expected: ':title'.
Found with similar names: ''
ERROR - DefaultExceptionMapper     - Unexpected error occurred
Unable to find component with id 'title' in [Page class = org.apache.wicket.examples.tablibrary.TabPanel1, id = 3, render count = 1]
Expected: ':title'.
Found with similar names: ''
 MarkupStream: [markup = file:/Users/erikostermueller/Documents/src/jdist/wicket/tablibrary/wicket-examples/target/classes/org/apache/wicket/examples/tablibrary/TabPanel1.html

<html>
  Book Details
  <p></p>
  <table>
<tr>
  <td align="right">Title:</td>
  <td>
    <span wicket:id="title">
        Huckleberry Finn
        </span>
      </td>
</tr>
<tr>
  <td align="right">Author:</td>
  <td>
    <span wicket:id="author">
        Mark Twain
        </span>
  </td>
</tr>
<tr>
  <td align="right">Fiction:</td>
  <td>
    <span wicket:id="fiction">
    true
        </span>
  </td>
</tr>
<tr>
  <td align="right">Companion Book:</td>
  <td>
    <a wicket:id="companion">
    <span wicket:id="title">Cat in Hat</span>
        </a>
  </td>
</tr>
<tr>
  <td align="right">Styles:</td>
  <td>
    <span wicket:id="writingStyles">
      Style1, Style2
      </span>
  </td>
</tr>
<tr>
  <td align="right">Related Book:</td>
  <td>
    <a wicket:id="related">
    <span wicket:id="title">Cat in Hat</span>
        </a>
  </td>
</tr>
  </table>
 </html> , index = 1, current =  '<span wicket:id="title">' (line 0, column 0)]
at org.apache.wicket.markup.MarkupStream.throwMarkupException(MarkupStream.java:526)
at org.apache.wicket.MarkupContainer.renderNext(MarkupContainer.java:1435)
at org.apache.wicket.MarkupContainer.renderAll(MarkupContainer.java:1554)
at org.apache.wicket.Page.onRender(Page.java:876)
at org.apache.wicket.markup.html.WebPage.onRender(WebPage.java:142)
at org.apache.wicket.Component.internalRender(Component.java:2379)
at org.apache.wicket.Component.render(Component.java:2307)
at org.apache.wicket.Page.renderPage(Page.java:1010)
at org.apache.wicket.request.handler.render.WebPageRenderer.renderPage(WebPageRenderer.java:121)
at org.apache.wicket.request.handler.render.WebPageRenderer.respond(WebPageRenderer.java:219)
at org.apache.wicket.core.request.handler.RenderPageRequestHandler.respond(RenderPageRequestHandler.java:165)
at org.apache.wicket.request.cycle.RequestCycle$HandlerExecutor.respond(RequestCycle.java:861)
at org.apache.wicket.request.RequestHandlerStack.execute(RequestHandlerStack.java:64)
at org.apache.wicket.request.cycle.RequestCycle.execute(RequestCycle.java:261)
at org.apache.wicket.request.cycle.RequestCycle.processRequest(RequestCycle.java:218)
at org.apache.wicket.request.cycle.RequestCycle.processRequestAndDetach(RequestCycle.java:289)
at org.apache.wicket.protocol.http.WicketFilter.processRequestCycle(WicketFilter.java:259)
at org.apache.wicket.protocol.http.WicketFilter.processRequest(WicketFilter.java:201)
at org.apache.wicket.protocol.http.WicketFilter.doFilter(WicketFilter.java:282)
at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)
at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:399)
at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)
at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)
at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:766)
at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:450)
at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)
at org.mortbay.jetty.Server.handle(Server.java:326)
at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)
at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:928)
at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:549)
at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:212)
at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)
at org.mortbay.io.nio.SelectChannelEndPoint.run(SelectChannelEndPoint.java:410)
at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)
```


